package com.example.cinemabooking

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.screens.TicketScreen
import com.example.cinemabooking.ui.theme.CinemaBookingTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TicketScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun copyingTicketNumberPlacesItsIdOnClipboard() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        composeRule.setContent {
            CinemaBookingTheme {
                TicketScreen(
                    booking = Booking(1001, 4, 402, listOf("C4"), 2500),
                    movie = requireNotNull(MovieRepository.getMovieById(4)),
                    showtime = requireNotNull(ShowtimeRepository.getShowtimeById(402)),
                    onBackToMovies = {}
                )
            }
        }

        composeRule.onNodeWithText("Copy ticket number").performClick()
        assertEquals("1001", clipboard.primaryClip?.getItemAt(0)?.coerceToText(context)?.toString())
    }
}
