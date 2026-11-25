package com.example.cinemabooking.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity (
    @PrimaryKey
    val id: Int,
    val movieId: Int,
    val showtimeId: Int,
    val seatIds: String,
    val totalKzt: Int
)