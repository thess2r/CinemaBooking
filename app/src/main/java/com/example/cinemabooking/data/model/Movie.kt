package com.example.cinemabooking.data.model

data class Movie(
    val id: Int,
    val title: String,
    val genre: String,
    val durationMinutes: Int,
    val ageRating: String,
    val description: String,
    val posterResId: Int? = null
)
