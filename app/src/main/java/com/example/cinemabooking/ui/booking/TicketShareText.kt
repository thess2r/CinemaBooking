package com.example.cinemabooking.ui.booking

import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime

fun ticketShareText(booking: Booking, movie: Movie, showtime: Showtime): String = buildString {
    appendLine("Cinema Kit demo ticket #${booking.id}")
    appendLine("Movie: ${movie.title}")
    appendLine("Time: ${showtime.time} · Hall ${showtime.hall}")
    appendLine("Seats: ${booking.seatIds.joinToString(", ")}")
    appendLine("Total: ${formatPriceKzt(booking.totalKzt)}")
    append("Demo only — not valid for admission")
}
