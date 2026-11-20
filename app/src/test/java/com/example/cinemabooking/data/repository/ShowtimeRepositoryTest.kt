package com.example.cinemabooking.data.repository

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ShowtimeRepositoryTest {

    @Test
    fun getShowtimesForMovie_steppeRiders_returnsThreeShowtimes() {
        val result = ShowtimeRepository.getShowtimesForMovie(4)

        assertEquals(3, result.size)
        assertEquals(listOf("15:00", "18:00", "21:00"), result.map { it.time })
    }

    @Test
    fun getShowtimesForMovie_returnsOnlyThatMovie() {
        val result = ShowtimeRepository.getShowtimesForMovie(4)

        assertTrue(result.all { it.movieId == 4 })
    }

    @Test
    fun getShowtimeById_402_isSteppeRiders1800InHall2() {
        val showtime = ShowtimeRepository.getShowtimeById(402)

        assertNotNull(showtime)
        assertEquals(4, showtime!!.movieId)
        assertEquals("18:00", showtime.time)
        assertEquals(2, showtime.hall)
    }
}