package com.example.cinemabooking.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getDatabase(context: Context): BookingDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookingDatabase::class.java,
                    "cinema_booking_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}