package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.model.Showtime

object ShowtimeRepository {

    private val times = listOf("15:00", "18:00", "21:00")
    private val movieIds = listOf(1, 2, 3, 4, 5)

    // id сеанса = movieId * 100 + номер (401 = 15:00, 402 = 18:00, 403 = 21:00)
    private val showtimes: List<Showtime> = movieIds.flatMap { movieId ->
        times.mapIndexed { index, time ->
            Showtime(
                id = movieId * 100 + (index + 1),
                movieId = movieId,
                time = time,
                hall = if (movieId % 2 == 0) 2 else 1,
                priceKzt = 2500
            )
        }
    }

    fun getAllShowtimes(): List<Showtime> = showtimes

    fun getShowtimesForMovie(movieId: Int): List<Showtime> =
        showtimes.filter { it.movieId == movieId }

    fun getShowtimeById(id: Int): Showtime? =
        showtimes.find { it.id == id }
}