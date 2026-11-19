package com.example.cinemabooking.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CinemaViewModelTest {

    @Test
    fun occupiedSeatCannotBeSelected() {
        val viewModel = CinemaViewModel()

        viewModel.selectMovie(4)
        viewModel.selectShowtime(402)

        viewModel.toggleSeat("A2")

        assertTrue(
            viewModel.uiState.value.selectedSeats.isEmpty()
        )
    }

    @Test
    fun availableSeatCanBeSelected() {
        val viewModel = CinemaViewModel()

        viewModel.selectMovie(4)
        viewModel.selectShowtime(402)

        viewModel.toggleSeat("C4")

        assertEquals(
            setOf("C4"),
            viewModel.uiState.value.selectedSeats
        )
    }

    @Test
    fun selectedSeatCanBeDeselected() {
        val viewModel = CinemaViewModel()

        viewModel.selectMovie(4)
        viewModel.selectShowtime(402)

        viewModel.toggleSeat("C4")
        viewModel.toggleSeat("C4")

        assertTrue(
            viewModel.uiState.value.selectedSeats.isEmpty()
        )
    }

    @Test
    fun changingShowtimeClearsSelection() {
        val viewModel = CinemaViewModel()

        viewModel.selectMovie(4)
        viewModel.selectShowtime(402)
        viewModel.toggleSeat("C4")

        viewModel.selectShowtime(403)

        assertTrue(
            viewModel.uiState.value.selectedSeats.isEmpty()
        )
    }

    @Test
    fun bookingCannotBeConfirmedWithoutSeats() {
        val viewModel = CinemaViewModel()

        viewModel.selectMovie(4)
        viewModel.selectShowtime(402)

        assertNull(viewModel.confirmBooking())
    }
}