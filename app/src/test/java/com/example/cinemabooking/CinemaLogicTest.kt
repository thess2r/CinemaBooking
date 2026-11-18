package com.example.cinemabooking

import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.viewmodel.CinemaViewModel
import com.example.cinemabooking.ui.viewmodel.calculateTotal
import com.example.cinemabooking.ui.viewmodel.canConfirmBooking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CinemaLogicTest {
    @Test fun movieSearchFindsSteppeRidersIgnoringCase() {
        assertEquals("Steppe Riders", MovieRepository.getMovieById(4)?.title)
        assertEquals(listOf(4), MovieRepository.search("sTePpE").map { it.id })
    }

    @Test fun steppeRidersHasThreeShowtimesAndDemoSession() {
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
        assertTrue(canConfirmBooking(4, 402, setOf("C4")))
    }

    @Test fun occupiedSeatCannotBeBookedAndConfirmedSeatBecomesOccupied() {
        val model = CinemaViewModel()
        model.selectMovie(4)
        model.selectShowtime(402)
        model.toggleSeat("A2")
        assertTrue(model.uiState.value.selectedSeats.isEmpty())
        model.toggleSeat("C4")
        assertEquals(setOf("C4"), model.uiState.value.selectedSeats)
        assertEquals(2500, model.confirmBooking()?.totalKzt)
        assertTrue("C4" in model.occupiedSeats(402))
        assertEquals(null, model.confirmBooking())
    }

    @Test fun changingMovieClearsShowtimeAndSeats() {
        val model = CinemaViewModel()
        model.selectMovie(4)
        model.selectShowtime(402)
        model.toggleSeat("C4")
        model.selectMovie(1)
        assertEquals(null, model.uiState.value.selectedShowtimeId)
        assertTrue(model.uiState.value.selectedSeats.isEmpty())
    }
}
