package com.example.cinemabooking.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cinemabooking.data.model.BookingSortOrder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThemePreferencesRepositoryTest {
    @Test
    fun darkThemeRemainsSavedForNewRepositoryInstance() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val firstRepository = ThemePreferencesRepository(context)
        try {
            firstRepository.setDarkTheme(true)
            val reopenedRepository = ThemePreferencesRepository(context)
            assertTrue(reopenedRepository.darkTheme.first())
        } finally {
            firstRepository.setDarkTheme(false)
        }
        assertFalse(ThemePreferencesRepository(context).darkTheme.first())
    }

    @Test
    fun bookingSortOrderRemainsSavedForNewRepositoryInstance() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val firstRepository = ThemePreferencesRepository(context)
        try {
            firstRepository.setBookingSortOrder(BookingSortOrder.PRICE_HIGH_TO_LOW)
            val reopenedRepository = ThemePreferencesRepository(context)
            assertEquals(BookingSortOrder.PRICE_HIGH_TO_LOW, reopenedRepository.bookingSortOrder.first())
        } finally {
            firstRepository.setBookingSortOrder(BookingSortOrder.Default)
        }
        assertEquals(BookingSortOrder.Default, ThemePreferencesRepository(context).bookingSortOrder.first())
    }
}
