package com.example.cinemabooking.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cinemabooking.data.entity.BookingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("SELECT * FROM bookings ORDER BY id DESC")
    fun observeAllBookings(): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE showtimeId = :showtimeId")
    fun observeBookingsByShowtime(showtimeId: Int): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE id = :bookingId")
    suspend fun getBookingById(bookingId: Int): BookingEntity?

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Delete
    suspend fun deleteBooking(booking: BookingEntity)

    @Query("DELETE FROM bookings WHERE id = :bookingId")
    suspend fun deleteBookingById(bookingId: Int)
}