package com.example.cinemabooking.ui.viewmodel

import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.repository.MovieRepository

data class CinemaUiState(
    val movies: List<Movie> = MovieRepository.movies,
    val selectedMovieId: Int? = null,
    val selectedShowtimeId: Int? = null,
    val selectedSeats: Set<String> = emptySet(),
    val bookings: List<Booking> = emptyList(),
    val confirmedBooking: Booking? = null
)
