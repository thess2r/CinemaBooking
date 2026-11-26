package com.example.cinemabooking

import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.viewmodel.calculateTotal
import com.example.cinemabooking.ui.viewmodel.canConfirmBooking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CinemaLogicTest {
    @Test
    fun movieSearchFindsDuneIgnoringCase() {
        assertEquals("Dune: Part Two", MovieRepository.getMovieById(4)?.title)
        assertEquals(listOf(4), MovieRepository.search("dUnE").map { it.id })
    }

    @Test fun duneHasThreeShowtimesAndDemoSession() {
        val showtimes = ShowtimeRepository.getShowtimesForMovie(4)
        assertEquals(3, showtimes.size)
        assertTrue(showtimes.all { it.movieId == 4 })
        assertEquals("18:00", ShowtimeRepository.getShowtimeById(402)?.time)
    }

    @Test fun selectedSeatsDeterminePrice() {
        assertEquals(0, calculateTotal(2500, emptySet()))
        assertEquals(2500, calculateTotal(2500, setOf("C4")))
        assertEquals(5000, calculateTotal(2500, setOf("C4", "A3")))
    }

    @Test fun bookingRequiresValidMovieSessionAndSeat() {
        assertFalse(canConfirmBooking(4, 402, emptySet()))
        assertFalse(canConfirmBooking(99, 402, setOf("C4")))
        assertFalse(canConfirmBooking(4, 102, setOf("C4")))
        assertFalse(canConfirmBooking(4, 402, setOf("Z99")))
        assertTrue(canConfirmBooking(4, 402, setOf("C4")))
    }

}
