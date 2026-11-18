package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.model.Showtime

object ShowtimeRepository {
    val showtimes: List<Showtime> = MovieRepository.movies.flatMap { movie ->
        listOf("15:00", "18:00", "21:00").mapIndexed { index, time ->
            Showtime(movie.id * 100 + index + 1, movie.id, time, 2, 2500)
        }
    }

    fun getShowtimesForMovie(movieId: Int): List<Showtime> =
        showtimes.filter { it.movieId == movieId }

    fun getShowtimeById(id: Int): Showtime? = showtimes.find { it.id == id }
}
