package com.example.cinemabooking

import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.booking.ticketShareText
import org.junit.Assert.assertTrue
import org.junit.Test

class TicketShareTextTest {
    @Test fun sharedTicketContainsBookingDetailsAndDemoWarning() {
        val text = ticketShareText(
            Booking(1001, 4, 402, listOf("C4", "C3"), 5000),
            requireNotNull(MovieRepository.getMovieById(4)),
            requireNotNull(ShowtimeRepository.getShowtimeById(402))
        )

        listOf("#1001", "Steppe Riders", "18:00", "Hall 2", "C4, C3", "5 000 ₸", "Demo only")
            .forEach { assertTrue("Missing $it", it in text) }
    }
}
