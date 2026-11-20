package com.example.cinemabooking.ui.screens



import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.repository.ShowtimeRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MovieDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val movie = Movie(
        id = 4,
        title = "Steppe Riders",
        genre = "Adventure",
        durationMinutes = 136,
        ageRating = "12+",
        description = "Test description"
    )
    private val showtimes = ShowtimeRepository.getShowtimesForMovie(4)

    @Test
    fun chooseSeats_isDisabled_whenNoShowtimeSelected() {
        composeRule.setContent {
            MovieDetailsScreen(movie, showtimes, null, {}, {}, {})
        }

        composeRule.onNodeWithText("Choose Seats").assertIsNotEnabled()
    }

    @Test
    fun chooseSeats_isEnabled_whenShowtimeSelected() {
        composeRule.setContent {
            MovieDetailsScreen(movie, showtimes, 402, {}, {}, {})
        }

        composeRule.onNodeWithText("Choose Seats").assertIsEnabled()
    }

    @Test
    fun clickingShowtime_callsCallbackWithCorrectId() {
        var clickedId: Int? = null
        composeRule.setContent {
            MovieDetailsScreen(movie, showtimes, null, { clickedId = it }, {}, {})
        }

        composeRule.onNodeWithText("18:00").performClick()

        assertEquals(402, clickedId)
    }
}