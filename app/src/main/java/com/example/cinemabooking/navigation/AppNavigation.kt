package com.example.cinemabooking.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.screens.BookingSummaryScreen
import com.example.cinemabooking.ui.screens.MovieDetailsScreen
import com.example.cinemabooking.ui.screens.MoviesScreen
import com.example.cinemabooking.ui.screens.SeatSelectionScreen
import com.example.cinemabooking.ui.screens.TicketScreen
import com.example.cinemabooking.ui.viewmodel.CinemaViewModel

private const val MOVIES = "movies"
private const val DETAILS = "details/{movieId}"
private const val SEATS = "seats/{movieId}/{showtimeId}"
private const val SUMMARY = "summary"
private const val TICKET = "ticket"

@Composable
fun AppNavigation(cinemaViewModel: CinemaViewModel = viewModel()) {
    val navController = rememberNavController()
    val state by cinemaViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MOVIES,
        modifier = Modifier.systemBarsPadding()
    ) {
        composable(MOVIES) {
            MoviesScreen(movies = state.movies) { movieId ->
                cinemaViewModel.selectMovie(movieId)
                navController.navigate("details/$movieId")
            }
        }
        composable(DETAILS, arguments = listOf(navArgument("movieId") { type = NavType.IntType })) { entry ->
            val movieId = entry.arguments?.getInt("movieId") ?: -1
            val movie = MovieRepository.getMovieById(movieId)
            if (movie == null) Text("Movie not found")
            else MovieDetailsScreen(
                movie = movie,
                showtimes = ShowtimeRepository.getShowtimesForMovie(movieId),
                selectedShowtimeId = state.selectedShowtimeId,
                onSelectShowtime = cinemaViewModel::selectShowtime,
                onChooseSeats = {
                    state.selectedShowtimeId?.let { showtimeId ->
                        navController.navigate("seats/$movieId/$showtimeId")
                    }
                },
                onBack = { navController.navigateUp() }
            )
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
            if (movie == null || showtime == null || showtime.movieId != movieId) Text("Session not found")
            else SeatSelectionScreen(
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
        composable(SUMMARY) {
            val movie = state.selectedMovieId?.let(MovieRepository::getMovieById)
            val showtime = state.selectedShowtimeId?.let(ShowtimeRepository::getShowtimeById)
            if (movie == null || showtime == null) Text("Booking selection missing")
            else BookingSummaryScreen(
                movie = movie,
                showtime = showtime,
                selectedSeats = state.selectedSeats,
                onConfirm = {
                    if (cinemaViewModel.confirmBooking() != null) {
                        navController.navigate(TICKET) { popUpTo(MOVIES) { inclusive = false } }
                    }
                },
                onBack = { navController.navigateUp() }
            )
        }
        composable(TICKET) {
            val booking = state.confirmedBooking
            val movie = booking?.let { MovieRepository.getMovieById(it.movieId) }
            val showtime = booking?.let { ShowtimeRepository.getShowtimeById(it.showtimeId) }
            if (booking == null || movie == null || showtime == null) Text("Ticket not found")
            else {
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
