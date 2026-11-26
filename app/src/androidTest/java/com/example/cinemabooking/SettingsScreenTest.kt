package com.example.cinemabooking

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import com.example.cinemabooking.data.model.BookingSortOrder
import com.example.cinemabooking.data.repository.ThemePreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {
    @get:Rule val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bookingOrderSelectionReachesDataStoreAndSettings() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val preferences = ThemePreferencesRepository(context)
        runBlocking { preferences.setBookingSortOrder(BookingSortOrder.Default) }
        try {
            composeRule.onNodeWithText("Settings").performClick()
            composeRule.onNodeWithTag("sort-PRICE_HIGH_TO_LOW").performClick()
            composeRule.waitUntil(5_000) {
                runBlocking {
                    preferences.bookingSortOrder.first() == BookingSortOrder.PRICE_HIGH_TO_LOW
                }
            }
            composeRule.onNodeWithText("← Back to Movies").performClick()
            composeRule.onNodeWithText("Settings").performClick()

            val selected = composeRule.onNodeWithTag("sort-PRICE_HIGH_TO_LOW")
                .fetchSemanticsNode().config[SemanticsProperties.Selected]
            assertTrue(selected)
        } finally {
            runBlocking { preferences.setBookingSortOrder(BookingSortOrder.Default) }
        }
    }
}
