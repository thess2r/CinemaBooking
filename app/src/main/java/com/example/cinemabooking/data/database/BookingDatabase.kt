package com.example.cinemabooking.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemabooking.data.dao.BookingDao
import com.example.cinemabooking.data.entity.BookingEntity

@Database(
    entities = [BookingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookingDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao
}