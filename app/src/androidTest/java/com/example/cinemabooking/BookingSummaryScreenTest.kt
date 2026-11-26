package com.example.cinemabooking

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.screens.BookingSummaryScreen
import com.example.cinemabooking.ui.theme.CinemaBookingTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BookingSummaryScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun selectedSeatAndTotalAreShownBeforeConfirm() {
        var confirmed = false
        composeRule.setContent {
            CinemaBookingTheme {
                BookingSummaryScreen(
                    movie = requireNotNull(MovieRepository.getMovieById(4)),
                    showtime = requireNotNull(ShowtimeRepository.getShowtimeById(402)),
                    selectedSeats = setOf("C4"),
                    onConfirm = { confirmed = true },
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithText("Steppe Riders").assertExists()
        composeRule.onNodeWithText("C4").assertExists()
        composeRule.onAllNodesWithText("2 500 ₸").assertCountEquals(2)
        composeRule.onNodeWithText("Confirm Booking").performClick()
        composeRule.onNodeWithText("Confirm demo booking?").assertExists()
        composeRule.onNodeWithText("Book 1 seat for 2 500 ₸? No payment is collected.").assertExists()
        assertFalse(confirmed)
        composeRule.onNodeWithText("Book seats").performClick()
        assertTrue(confirmed)
    }

    @Test fun bookingCannotBeConfirmedWithoutSeats() {
        composeRule.setContent {
            CinemaBookingTheme {
                BookingSummaryScreen(
                    movie = requireNotNull(MovieRepository.getMovieById(4)),
                    showtime = requireNotNull(ShowtimeRepository.getShowtimeById(402)),
                    selectedSeats = emptySet(),
                    onConfirm = {},
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithText("Confirm Booking").assertIsNotEnabled()
    }

    @Test fun cancellingConfirmationDoesNotBookSeats() {
        var confirmed = false
        composeRule.setContent {
            CinemaBookingTheme {
                BookingSummaryScreen(
                    movie = requireNotNull(MovieRepository.getMovieById(4)),
                    showtime = requireNotNull(ShowtimeRepository.getShowtimeById(402)),
                    selectedSeats = setOf("C4"),
                    onConfirm = { confirmed = true },
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithText("Confirm Booking").performClick()
        composeRule.onNodeWithText("Cancel").performClick()
        assertFalse(confirmed)
    }

    @Test fun confirmationShowsCorrectTotalForTwoSeats() {
        composeRule.setContent {
            CinemaBookingTheme {
                BookingSummaryScreen(
                    movie = requireNotNull(MovieRepository.getMovieById(4)),
                    showtime = requireNotNull(ShowtimeRepository.getShowtimeById(402)),
                    selectedSeats = setOf("C4", "A3"),
                    onConfirm = {},
                    onBack = {}
                )
            }
        }

        composeRule.onNodeWithText("Confirm Booking").performClick()
        composeRule.onNodeWithText("Book 2 seats for 5 000 ₸? No payment is collected.").assertExists()
    }
}
