package com.example.cinemabooking.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.cinemabooking.data.database.BookingDatabase
import com.example.cinemabooking.data.repository.BookingRepository
import com.example.cinemabooking.ui.viewmodel.CinemaViewModelFactory
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.navigation.CinemaRoutes.DETAILS
import com.example.cinemabooking.navigation.CinemaRoutes.MOVIES
import com.example.cinemabooking.navigation.CinemaRoutes.SEATS
import com.example.cinemabooking.navigation.CinemaRoutes.SUMMARY
import com.example.cinemabooking.navigation.CinemaRoutes.TICKET
import com.example.cinemabooking.navigation.CinemaRoutes.SETTINGS
import com.example.cinemabooking.navigation.CinemaRoutes.MY_BOOKINGS
import com.example.cinemabooking.ui.screens.BookingSummaryScreen
import com.example.cinemabooking.ui.screens.BookingUnavailableScreen
import com.example.cinemabooking.ui.screens.MovieDetailsScreen
import com.example.cinemabooking.ui.screens.MoviesScreen
import com.example.cinemabooking.ui.screens.SeatSelectionScreen
import com.example.cinemabooking.ui.screens.TicketScreen
import com.example.cinemabooking.ui.screens.SettingsScreen
import com.example.cinemabooking.ui.screens.MyBookingsScreen
import com.example.cinemabooking.ui.viewmodel.CinemaViewModel
import com.example.cinemabooking.ui.viewmodel.BookingsViewModel
import com.example.cinemabooking.ui.viewmodel.BookingsViewModelFactory
import com.example.cinemabooking.ui.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val context = LocalContext.current

    val bookingRepository = remember(context) {
        BookingRepository(
            BookingDatabase.getDatabase(context).bookingDao()
        )
    }

    val cinemaViewModel: CinemaViewModel = viewModel(
        factory = CinemaViewModelFactory(bookingRepository)
    )
    val bookingsViewModel: BookingsViewModel = viewModel(
        factory = BookingsViewModelFactory(bookingRepository)
    )

    val navController = rememberNavController()
    val state by cinemaViewModel.uiState.collectAsState()
    val recoverToMovies: () -> Unit = {
        cinemaViewModel.clearSelection()
        navController.popBackStack(MOVIES, false)
    }

    NavHost(
        navController = navController,
        startDestination = MOVIES,
        modifier = Modifier.systemBarsPadding()
    ) {
        composable(MOVIES) {
            MoviesScreen(
                movies = state.movies,
                onMovieClick = { movieId ->
                    cinemaViewModel.selectMovie(movieId)
                    navController.navigate(CinemaRoutes.details(movieId))
                },
                onSettingsClick = { navController.navigate(SETTINGS) },
                onMyBookingsClick = { navController.navigate(MY_BOOKINGS) }
            )
        }
        composable(MY_BOOKINGS) {
            val bookings by bookingsViewModel.bookings.collectAsState()
            MyBookingsScreen(
                bookings = bookings,
                onUpdateSeats = bookingsViewModel::updateSeats,
                onDelete = bookingsViewModel::deleteBooking,
                onBack = { navController.navigateUp() }
            )
        }
        composable(SETTINGS) {
            val darkTheme by themeViewModel.darkTheme.collectAsState()
            SettingsScreen(
                darkTheme = darkTheme,
                onDarkThemeChange = themeViewModel::setDarkTheme,
                onBack = { navController.navigateUp() }
            )
        }
        composable(DETAILS, arguments = listOf(navArgument("movieId") { type = NavType.IntType })) { entry ->
            val movieId = entry.arguments?.getInt("movieId") ?: -1
            val movie = MovieRepository.getMovieById(movieId)
            if (movie == null || state.selectedMovieId != movieId) {
                BookingUnavailableScreen("Movie selection is missing or invalid.", recoverToMovies)
            } else {
                MovieDetailsScreen(
                    movie = movie,
                    showtimes = ShowtimeRepository.getShowtimesForMovie(movieId),
                    selectedShowtimeId = state.selectedShowtimeId,
                    onSelectShowtime = cinemaViewModel::selectShowtime,
                    onChooseSeats = {
                        state.selectedShowtimeId?.let { showtimeId ->
                            navController.navigate(CinemaRoutes.seats(movieId, showtimeId))
                        }
                    },
                    onBack = { navController.navigateUp() }
                )
            }
        }
        composable(
            SEATS,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType },
                navArgument("showtimeId") { type = NavType.IntType }
            )
        ) { entry ->
            val movieId = entry.arguments?.getInt("movieId") ?: -1
            val showtimeId = entry.arguments?.getInt("showtimeId") ?: -1
            val movie = MovieRepository.getMovieById(movieId)
            val showtime = ShowtimeRepository.getShowtimeById(showtimeId)
            if (movie == null || showtime == null || showtime.movieId != movieId ||
                state.selectedMovieId != movieId || state.selectedShowtimeId != showtimeId
            ) {
                BookingUnavailableScreen("Session selection is missing or invalid.", recoverToMovies)
            } else {
                SeatSelectionScreen(
                    movie = movie,
                    showtime = showtime,
                    occupiedSeats = cinemaViewModel.occupiedSeats(showtimeId),
                    selectedSeats = state.selectedSeats,
                    onSeatClick = cinemaViewModel::toggleSeat,
                    onContinue = {
                        if (state.selectedSeats.isNotEmpty()) navController.navigate(SUMMARY)
                    },
                    onBack = { navController.navigateUp() }
                )
            }
        }
        composable(SUMMARY) {
            val movie = state.selectedMovieId?.let(MovieRepository::getMovieById)
            val showtime = state.selectedShowtimeId?.let(ShowtimeRepository::getShowtimeById)
            if (movie == null || showtime == null || showtime.movieId != movie.id || state.selectedSeats.isEmpty()) {
                BookingUnavailableScreen("Booking selection missing.", recoverToMovies)
            } else {
                BookingSummaryScreen(
                    movie = movie,
                    showtime = showtime,
                    selectedSeats = state.selectedSeats,
                    onConfirm = { done ->
                        cinemaViewModel.confirmBooking { booking ->
                            done(booking != null)
                            if (booking != null) {
                                navController.navigate(TICKET) {
                                    popUpTo(MOVIES) {
                                        inclusive = false
                                    }
                                }
                            }
                        }
                    },
                    onBack = { navController.navigateUp() }
                )
            }
        }
        composable(TICKET) {
            val booking = state.confirmedBooking
            val movie = booking?.let { MovieRepository.getMovieById(it.movieId) }
            val showtime = booking?.let { ShowtimeRepository.getShowtimeById(it.showtimeId) }
            if (booking == null || movie == null || showtime == null || showtime.movieId != movie.id) {
                BookingUnavailableScreen("Ticket not found.", recoverToMovies)
            } else {
                val returnToMovies: () -> Unit = {
                    cinemaViewModel.clearSelection()
                    navController.popBackStack(MOVIES, false)
                }
                BackHandler(onBack = returnToMovies)
                TicketScreen(
                    booking = booking,
                    movie = movie,
                    showtime = showtime,
                    onBackToMovies = returnToMovies
                )
            }
        }
    }
}
