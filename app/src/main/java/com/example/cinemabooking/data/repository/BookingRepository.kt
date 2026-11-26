package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.dao.BookingDao
import com.example.cinemabooking.data.entity.BookingEntity
import com.example.cinemabooking.data.model.Booking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookingRepository(
    private val bookingDao: BookingDao
) {
    fun observeAllBookings(): Flow<List<Booking>> {
        return bookingDao.observeAllBookings().map { entities ->
            entities.map { it.toBooking() }
        }
    }

    fun observeBookingsByShowtime(showtimeId: Int): Flow<List<Booking>> {
        return bookingDao.observeBookingsByShowtime(showtimeId).map { entities ->
            entities.map { it.toBooking() }
        }
    }

    suspend fun getBookingById(bookingId: Int): Booking? {
        return bookingDao.getBookingById(bookingId)?.toBooking()
    }

    suspend fun addBooking(booking: Booking): Booking {
        val showtime = ShowtimeRepository.getShowtimeById(booking.showtimeId)
            ?: throw IllegalArgumentException("Session not found")
        require(showtime.movieId == booking.movieId) { "Movie and session do not match" }
        require(
            booking.seatIds.isNotEmpty() &&
                booking.seatIds.all { it in SeatRepository.allSeats } &&
                booking.seatIds.size == booking.seatIds.toSet().size
        ) { "Choose unique seats from A1 to C4" }
        require(booking.seatIds.none { it in SeatRepository.initialOccupiedSeats(booking.showtimeId) }) {
            "A selected seat is unavailable"
        }
        val validBooking = booking.copy(
            id = 0,
            seatIds = booking.seatIds.sorted(),
            totalKzt = showtime.priceKzt * booking.seatIds.size
        )
        val generatedId = bookingDao.insertBookingIfSeatsAvailable(
            validBooking.toEntity()
        )
        return validBooking.copy(id = generatedId.toInt())
    }

    suspend fun updateBooking(booking: Booking) {
        bookingDao.updateBooking(booking.toEntity())
    }

    suspend fun updateBookingSeats(bookingId: Int, input: String): Booking {
        val booking = getBookingById(bookingId)
            ?: throw IllegalArgumentException("Booking not found")
        val seats = input.split(",").map { it.trim().uppercase() }
        if (seats.isEmpty() || seats.any { it.isBlank() || it !in SeatRepository.allSeats } ||
            seats.size != seats.toSet().size
        ) {
            throw IllegalArgumentException("Enter unique seats from A1 to C4, separated by commas")
        }
        if (seats.any { it in SeatRepository.initialOccupiedSeats(booking.showtimeId) }) {
            throw IllegalArgumentException("A selected seat is unavailable")
        }
        val showtime = ShowtimeRepository.getShowtimeById(booking.showtimeId)
            ?: throw IllegalArgumentException("Session not found")
        val updated = booking.copy(
            seatIds = seats.sorted(),
            totalKzt = showtime.priceKzt * seats.size
        )
        bookingDao.updateBookingIfSeatsAvailable(updated.toEntity())
        return updated
    }

    suspend fun deleteBooking(booking: Booking) {
        bookingDao.deleteBooking(booking.toEntity())
    }

    suspend fun deleteBookingById(bookingId: Int) {
        bookingDao.deleteBookingById(bookingId)
    }

    private fun Booking.toEntity(): BookingEntity {
        return BookingEntity(
            id = id,
            movieId = movieId,
            showtimeId = showtimeId,
            seatIds = seatIds.joinToString(","),
            totalKzt = totalKzt
        )
    }

    private fun BookingEntity.toBooking(): Booking {
        return Booking(
            id = id,
            movieId = movieId,
            showtimeId = showtimeId,
            seatIds = seatIds.split(",").filter { it.isNotBlank() },
            totalKzt = totalKzt
        )
    }
}
