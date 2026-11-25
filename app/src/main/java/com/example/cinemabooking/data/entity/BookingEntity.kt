package com.example.cinemabooking.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val showtimeId: Int,
    val seatIds: String,
    val totalKzt: Int
)