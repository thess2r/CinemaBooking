package com.example.cinemabooking

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cinemabooking.ui.screens.BookingUnavailableScreen
import com.example.cinemabooking.ui.theme.CinemaBookingTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BookingUnavailableScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun missingBookingOffersRouteBackToMovies() {
        var returned = false
        composeRule.setContent {
            CinemaBookingTheme {
                BookingUnavailableScreen("Booking selection missing.") { returned = true }
            }
        }

        composeRule.onNodeWithText("Booking selection missing.").assertExists()
        composeRule.onNodeWithText("Back to Movies").performClick()
        assertTrue(returned)
    }
}
