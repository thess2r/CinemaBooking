package com.example.cinemabooking.data.model

data class Showtime(
    val id: Int,
    val movieId: Int,
    val time: String,      // "18:00"
    val hall: Int,         // 2
    val priceKzt: Int      // 2500
)