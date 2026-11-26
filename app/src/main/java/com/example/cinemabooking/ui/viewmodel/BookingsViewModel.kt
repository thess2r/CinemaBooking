package com.example.cinemabooking.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemabooking.data.repository.BookingRepository
import com.example.cinemabooking.data.model.Booking
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookingsViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    val bookings: StateFlow<List<Booking>> =
        bookingRepository.observeAllBookings().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBooking(booking: Booking) {
        viewModelScope.launch {
            bookingRepository.addBooking(booking)
        }
    }

    fun updateBooking(booking: Booking) {
        viewModelScope.launch {
            bookingRepository.updateBooking(booking)
        }
    }

    fun deleteBooking(booking: Booking) {
        viewModelScope.launch {
            bookingRepository.deleteBooking(booking)
        }
    }

    fun deleteBookingById(bookingId: Int) {
        viewModelScope.launch {
            bookingRepository.deleteBookingById(bookingId)
        }
    }
}