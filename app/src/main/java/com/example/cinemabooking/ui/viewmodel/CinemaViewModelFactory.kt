package com.example.cinemabooking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemabooking.data.repository.BookingRepository

class CinemaViewModelFactory(
    private val bookingRepository: BookingRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CinemaViewModel::class.java)) {
            return CinemaViewModel(bookingRepository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}