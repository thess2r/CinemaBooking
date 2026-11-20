package com.example.cinemabooking

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class BookingJourneyTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test fun movieToTicketAndBackUsesTheSameBooking() {
        bookDuneC4()
        composeRule.onNodeWithText("Ticket #1001").assertExists()
        composeRule.onNodeWithText("Dune: Part Two").assertExists()
        composeRule.onNodeWithText("C4").assertExists()
        composeRule.onNodeWithText("2 500 ₸").assertExists()
        composeRule.onNodeWithText("Back to Movies").performClick()
        composeRule.onNodeWithText("Now Showing").assertExists()
    }

    @Test fun confirmedSeatIsDisabledWhenTheSessionIsReopened() {
        bookDuneC4()
        composeRule.onNodeWithText("Back to Movies").performClick()
        composeRule.onNodeWithText("Search by title").performTextClearance()
        composeRule.onNodeWithText("Search by title").performTextInput("Dune")
        composeRule.onNodeWithText("View Details").performClick()
        composeRule.onNodeWithText("18:00").performClick()
        composeRule.onNodeWithText("Choose Seats").performClick()
        composeRule.onNodeWithText("C4").assertIsNotEnabled()
    }

    private fun bookDuneC4() {
        composeRule.onNodeWithText("Search by title").performTextInput("Dune")
        composeRule.onNodeWithText("View Details").performClick()
        composeRule.onNodeWithText("18:00").performClick()
        composeRule.onNodeWithText("Choose Seats").performClick()
        composeRule.onNodeWithText("C4").performClick()
        composeRule.onNodeWithText("Continue").performClick()
        composeRule.onNodeWithText("Confirm Booking").performClick()
        composeRule.onNodeWithText("Book seats").performClick()
    }
}
