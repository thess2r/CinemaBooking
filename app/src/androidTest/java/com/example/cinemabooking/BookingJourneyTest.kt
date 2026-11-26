package com.example.cinemabooking

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import kotlinx.coroutines.runBlocking
import com.example.cinemabooking.data.database.BookingDatabase

class BookingJourneyTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun clearDatabase() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        BookingDatabase.getDatabase(context)
            .bookingDao()
            .deleteAllBookings()
    }

    @Test fun movieToTicketAndBackUsesTheSameBooking() {
        bookDuneC4()
        composeRule.onNodeWithText(
            "Booking confirmed"
        ).assertExists()

        composeRule.onNodeWithText(
            "Copy ticket number"
        ).assertExists()
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

    @Test fun savedBookingCanBeEditedAndDeletedFromMyBookings() {
        bookDuneC4()
        composeRule.onNodeWithText("Back to Movies").performClick()
        composeRule.onNodeWithText("My Bookings").performClick()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("C4").fetchSemanticsNodes().isNotEmpty()
        }
        composeRule.onNodeWithText("C4").assertExists()

        composeRule.onNodeWithText("Edit seats").performClick()
        composeRule.onNodeWithTag("seat-edit").performTextClearance()
        composeRule.onNodeWithTag("seat-edit").performTextInput("A2")
        composeRule.onNodeWithText("Save seats").performClick()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("A selected seat is unavailable").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("seat-edit").performTextClearance()
        composeRule.onNodeWithTag("seat-edit").performTextInput("A1")
        composeRule.onNodeWithText("Save seats").performClick()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("A1").fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithText("Delete").performClick()
        composeRule.onNodeWithText("Delete booking").performClick()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("No bookings yet").fetchSemanticsNodes().isNotEmpty()
        }
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
