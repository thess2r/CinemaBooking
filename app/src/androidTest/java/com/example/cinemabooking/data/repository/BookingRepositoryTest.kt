package com.example.cinemabooking.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cinemabooking.data.database.BookingDatabase
import com.example.cinemabooking.data.model.Booking
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookingRepositoryTest {

    private lateinit var database: BookingDatabase
    private lateinit var repository: BookingRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            BookingDatabase::class.java
        ).build()

        repository = BookingRepository(database.bookingDao())
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun addBooking_savesBookingToDatabase() = runBlocking {
        val booking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1", "A2"),
            totalKzt = 3000
        )

        val savedBooking = repository.addBooking(booking)
        val bookings = repository.observeAllBookings().first()

        assertNotEquals(0, savedBooking.id)
        assertNotNull(repository.getBookingById(savedBooking.id))
        assertEquals(1, bookings.size)
        assertEquals(listOf("A1", "A2"), bookings.first().seatIds)
    }

    @Test
    fun updateBooking_changesStoredBooking() = runBlocking {
        val booking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1"),
            totalKzt = 1500
        )

        val savedBooking = repository.addBooking(booking)

        repository.updateBooking(savedBooking.copy(totalKzt = 2000))

        val result = repository.getBookingById(savedBooking.id)

        assertEquals(2000, result?.totalKzt)
    }

    @Test
    fun deleteBooking_removesStoredBooking() = runBlocking {
        val booking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1"),
            totalKzt = 1500
        )

        val savedBooking = repository.addBooking(booking)

        repository.deleteBookingById(savedBooking.id)

        val result = repository.getBookingById(savedBooking.id)

        assertEquals(null, result)
        assertEquals(0, repository.observeAllBookings().first().size)
    }
}