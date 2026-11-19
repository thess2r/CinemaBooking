package com.example.cinemabooking.data.repository

object SeatRepository {
    val allSeats = ('A'..'C').flatMap { row -> (1..4).map { number -> "$row$number" } }

    fun initialOccupiedSeats(showtimeId: Int): Set<String> =
        if (showtimeId == 402) setOf("A2", "B3", "C1") else emptySet()
}
