package com.example.cinemabooking.data.model

data class Showtime(
    val id: Int,
    val movieId: Int,
    val time: String,
    val hall: Int,
    val priceKzt: Int
)
