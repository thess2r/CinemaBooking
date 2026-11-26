package com.example.cinemabooking.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.cinemabooking.data.model.BookingSortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeDataStore by preferencesDataStore(name = "theme_preferences")

class ThemePreferencesRepository(context: Context) {
    private val appContext = context.applicationContext
    private val darkThemeKey = booleanPreferencesKey("dark_theme")
    private val bookingSortOrderKey = stringPreferencesKey("booking_sort_order")

    val darkTheme: Flow<Boolean> = appContext.themeDataStore.data.map { preferences ->
        preferences[darkThemeKey] ?: false
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        appContext.themeDataStore.edit { preferences ->
            preferences[darkThemeKey] = enabled
        }
    }

    val bookingSortOrder: Flow<BookingSortOrder> = appContext.themeDataStore.data.map { preferences ->
        BookingSortOrder.fromStorage(preferences[bookingSortOrderKey])
    }

    suspend fun setBookingSortOrder(order: BookingSortOrder) {
        appContext.themeDataStore.edit { preferences ->
            preferences[bookingSortOrderKey] = order.name
        }
    }
}
