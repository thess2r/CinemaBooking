package com.example.cinemabooking.data.model

data class Booking(
    val id: Int,
    val movieId: Int,
    val showtimeId: Int,
    val seatIds: List<String>,
    val totalKzt: Int
)
