package com.example.cinemabooking

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.cinemabooking.data.remote.model.OnlineMovie
import com.example.cinemabooking.data.repository.OnlineMoviesDataSource
import com.example.cinemabooking.ui.screens.DiscoverScreen
import com.example.cinemabooking.ui.theme.CinemaBookingTheme
import com.example.cinemabooking.ui.viewmodel.OnlineMoviesViewModel
import org.junit.Rule
import org.junit.Test

class DiscoverScreenTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun searchFiltersLoadedMoviesAndShowsNoResults() {
        val movies = listOf(
            OnlineMovie("1", "Kiki's Delivery Service", "A young witch", null, "103"),
            OnlineMovie("2", "Castle in the Sky", "A sky adventure", null, "124")
        )
        val viewModel = OnlineMoviesViewModel(object : OnlineMoviesDataSource {
            override suspend fun getMovies(): List<OnlineMovie> = movies
        })

        composeRule.setContent {
            CinemaBookingTheme { DiscoverScreen(moviesViewModel = viewModel) }
        }
        composeRule.onNodeWithText("Kiki's Delivery Service").assertExists()
        composeRule.onNodeWithText("Castle in the Sky").assertExists()

        composeRule.onNodeWithTag("discover-search").performTextInput("kiki")
        composeRule.onNodeWithText("Kiki's Delivery Service").assertExists()
        composeRule.onNodeWithText("Castle in the Sky").assertDoesNotExist()

        composeRule.onNodeWithTag("discover-search").performTextClearance()
        composeRule.onNodeWithTag("discover-search").performTextInput("unknown")
        composeRule.onNodeWithText("No movies found").assertExists()
    }
}
