package com.example.cinemabooking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.repository.BookingRepository
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.SeatRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

fun calculateTotal(priceKzt: Int, selectedSeatIds: Set<String>): Int =
    priceKzt * selectedSeatIds.size

fun canConfirmBooking(
    movieId: Int?,
    showtimeId: Int?,
    seatIds: Set<String>
): Boolean =
    movieId != null &&
            showtimeId != null &&
            seatIds.isNotEmpty() &&
            SeatRepository.allSeats.containsAll(seatIds) &&
            MovieRepository.getMovieById(movieId) != null &&
            ShowtimeRepository.getShowtimeById(showtimeId)?.movieId == movieId

class CinemaViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CinemaUiState())
    val uiState: StateFlow<CinemaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            bookingRepository.observeAllBookings().collect { bookings ->
                _uiState.update { current ->
                    current.copy(bookings = bookings)
                }
            }
        }
    }

    fun selectMovie(movieId: Int) {
        if (MovieRepository.getMovieById(movieId) == null) return

        _uiState.update { current ->
            if (current.selectedMovieId == movieId) current
            else current.copy(
                selectedMovieId = movieId,
                selectedShowtimeId = null,
                selectedSeats = emptySet(),
                confirmedBooking = null
            )
        }
    }

    fun selectShowtime(showtimeId: Int) {
        val showtime = ShowtimeRepository.getShowtimeById(showtimeId) ?: return

        _uiState.update { current ->
            if (showtime.movieId != current.selectedMovieId) current
            else current.copy(
                selectedShowtimeId = showtimeId,
                selectedSeats = emptySet(),
                confirmedBooking = null
            )
        }
    }

    fun occupiedSeats(showtimeId: Int): Set<String> =
        SeatRepository.initialOccupiedSeats(showtimeId) +
                uiState.value.bookings
                    .filter { it.showtimeId == showtimeId }
                    .flatMap { it.seatIds }

    fun toggleSeat(seatId: String) {
        _uiState.update { current ->
            val showtimeId = current.selectedShowtimeId
                ?: return@update current

            if (
                seatId !in SeatRepository.allSeats ||
                seatId in occupiedSeats(showtimeId)
            ) {
                current
            } else {
                current.copy(
                    selectedSeats = if (seatId in current.selectedSeats) {
                        current.selectedSeats - seatId
                    } else {
                        current.selectedSeats + seatId
                    }
                )
            }
        }
    }

    fun confirmBooking(onResult: (Booking?) -> Unit) {
        val current = uiState.value
        val movieId = current.selectedMovieId
        val showtimeId = current.selectedShowtimeId

        if (!canConfirmBooking(movieId, showtimeId, current.selectedSeats)) {
            onResult(null)
            return
        }

        if (current.selectedSeats.any { it in occupiedSeats(showtimeId!!) }) {
            onResult(null)
            return
        }

        val showtime = ShowtimeRepository.getShowtimeById(showtimeId!!)
        if (showtime == null) {
            onResult(null)
            return
        }

        val booking = Booking(
            id = 0,
            movieId = movieId!!,
            showtimeId = showtimeId,
            seatIds = current.selectedSeats.sorted(),
            totalKzt = calculateTotal(showtime.priceKzt, current.selectedSeats)
        )

        viewModelScope.launch {
            try {
                val savedBooking = bookingRepository.addBooking(booking)

                _uiState.update {
                    it.copy(
                        confirmedBooking = savedBooking,
                        selectedSeats = emptySet()
                    )
                }

                onResult(savedBooking)
            } catch (exception: Exception) {
                onResult(null)
            }
        }
    }

    fun clearSelection() {
        _uiState.update {
            it.copy(
                selectedMovieId = null,
                selectedShowtimeId = null,
                selectedSeats = emptySet(),
                confirmedBooking = null
            )
        }
    }
}
