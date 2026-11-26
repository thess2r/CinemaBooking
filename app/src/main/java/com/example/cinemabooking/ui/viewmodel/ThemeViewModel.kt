package com.example.cinemabooking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cinemabooking.data.model.BookingSortOrder
import com.example.cinemabooking.data.repository.ThemePreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val repository: ThemePreferencesRepository) : ViewModel() {
    val darkTheme: StateFlow<Boolean> = repository.darkTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    val bookingSortOrder: StateFlow<BookingSortOrder> = repository.bookingSortOrder.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BookingSortOrder.Default
    )

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch { repository.setDarkTheme(enabled) }
    }

    fun setBookingSortOrder(order: BookingSortOrder) {
        viewModelScope.launch { repository.setBookingSortOrder(order) }
    }
}

class ThemeViewModelFactory(
    private val repository: ThemePreferencesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
