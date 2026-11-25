package com.example.cinemabooking.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
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

    @Query("SELECT * FROM bookings WHERE showtimeId = :showtimeId")
    suspend fun getBookingsByShowtime(showtimeId: Int): List<BookingEntity>

    @Transaction
    suspend fun insertBookingIfSeatsAvailable(
        booking: BookingEntity
    ): Long {
        val requestedSeats = booking.seatIds
            .split(",")
            .filter { it.isNotBlank() }
            .toSet()

        val occupiedSeats = getBookingsByShowtime(booking.showtimeId)
            .flatMap { it.seatIds.split(",") }
            .toSet()

        val conflictingSeats = requestedSeats.intersect(occupiedSeats)

        if (conflictingSeats.isNotEmpty()) {
            throw IllegalStateException(
                "These seats are already booked: ${conflictingSeats.joinToString()}"
            )
        }

        return insertBooking(booking)
    }
}