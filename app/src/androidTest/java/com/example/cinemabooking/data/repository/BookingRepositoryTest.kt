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

    @Test
    fun observeBookingsByShowtime_returnsOnlyMatchingBookings() = runBlocking {
        val firstBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1"),
            totalKzt = 1500
        )

        val secondBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 403,
            seatIds = listOf("B1"),
            totalKzt = 1500
        )

        repository.addBooking(firstBooking)
        repository.addBooking(secondBooking)

        val result = repository.observeBookingsByShowtime(402).first()

        assertEquals(1, result.size)
        assertEquals(402, result.first().showtimeId)
        assertEquals(listOf("A1"), result.first().seatIds)
    }

    @Test
    fun bookingPersistsAfterDatabaseReopens() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val databaseName = "booking_persistence_test"

        context.deleteDatabase(databaseName)

        var persistentDatabase = Room.databaseBuilder(
            context,
            BookingDatabase::class.java,
            databaseName
        ).build()

        try {
            val persistentRepository =
                BookingRepository(persistentDatabase.bookingDao())

            val booking = Booking(
                id = 0,
                movieId = 1,
                showtimeId = 402,
                seatIds = listOf("A1", "B1"),
                totalKzt = 3000
            )

            val savedBooking = persistentRepository.addBooking(booking)

            persistentDatabase.close()

            persistentDatabase = Room.databaseBuilder(
                context,
                BookingDatabase::class.java,
                databaseName
            ).build()

            val reopenedRepository =
                BookingRepository(persistentDatabase.bookingDao())

            val result = reopenedRepository.getBookingById(savedBooking.id)

            assertNotNull(result)
            assertEquals(savedBooking.id, result?.id)
            assertEquals(listOf("A1", "B1"), result?.seatIds)
            assertEquals(3000, result?.totalKzt)
        } finally {
            persistentDatabase.close()
            context.deleteDatabase(databaseName)
        }
    }

    @Test
    fun addBooking_rejectsAlreadyBookedSeatsForSameShowtime() = runBlocking {
        val firstBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1", "A2"),
            totalKzt = 3000
        )

        repository.addBooking(firstBooking)

        val duplicateBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A2", "B1"),
            totalKzt = 3000
        )

        try {
            repository.addBooking(duplicateBooking)
            throw AssertionError("Duplicate seat booking should have been rejected")
        } catch (exception: IllegalStateException) {
            assertNotNull(exception.message)
        }

        val bookings = repository.observeAllBookings().first()
        assertEquals(1, bookings.size)
    }

    @Test
    fun addBooking_allowsSameSeatForDifferentShowtimes() = runBlocking {
        val firstBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("A1"),
            totalKzt = 1500
        )

        val secondBooking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 403,
            seatIds = listOf("A1"),
            totalKzt = 1500
        )

        repository.addBooking(firstBooking)
        repository.addBooking(secondBooking)

        val bookings = repository.observeAllBookings().first()

        assertEquals(2, bookings.size)
    }

    @Test
    fun observeAllBookings_emitsUpdatedDataAfterInsert() = runBlocking {
        val booking = Booking(
            id = 0,
            movieId = 1,
            showtimeId = 402,
            seatIds = listOf("C2"),
            totalKzt = 1500
        )

        repository.addBooking(booking)

        val result = repository.observeAllBookings().first()

        assertEquals(1, result.size)
        assertEquals(listOf("C2"), result.first().seatIds)
    }

    @Test
    fun updateBookingSeats_validatesSeatsAndRecalculatesTotal() = runBlocking {
        val booking = repository.addBooking(Booking(0, 4, 402, listOf("A1"), 2500))

        val updated = repository.updateBookingSeats(booking.id, "C4, B1")

        assertEquals(listOf("B1", "C4"), updated.seatIds)
        assertEquals(5000, updated.totalKzt)
        assertEquals(updated, repository.getBookingById(booking.id))
    }

    @Test
    fun updateBookingSeats_rejectsOccupiedAndInvalidSeats() = runBlocking {
        val first = repository.addBooking(Booking(0, 4, 402, listOf("A1"), 2500))
        val second = repository.addBooking(Booking(0, 4, 402, listOf("B1"), 2500))

        for (input in listOf("A1", "A2", "Z9", "C4, C4")) {
            try {
                repository.updateBookingSeats(second.id, input)
                throw AssertionError("Should reject $input")
            } catch (_: IllegalArgumentException) {
                // Invalid or initially occupied seat.
            } catch (_: IllegalStateException) {
                // Seat belongs to another booking.
            }
        }
        assertEquals(listOf("A1"), repository.getBookingById(first.id)?.seatIds)
        assertEquals(listOf("B1"), repository.getBookingById(second.id)?.seatIds)
    }
}
