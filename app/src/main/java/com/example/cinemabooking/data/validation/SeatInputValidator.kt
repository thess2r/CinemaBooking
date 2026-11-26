package com.example.cinemabooking.data.validation

import com.example.cinemabooking.data.repository.SeatRepository

/**
 * Pure validation rules for seat identifiers. Keeping them outside Room and the
 * repository lets My Bookings and Confirm share one definition of a valid seat
 * list and makes the rules easy to unit test without a database.
 */
object SeatInputValidator {
    const val INVALID_INPUT_MESSAGE = "Enter unique seats from A1 to C4, separated by commas"

    /** True for a non-empty list of unique seats that exist in the hall. */
    fun isValidSelection(seatIds: List<String>): Boolean =
        seatIds.isNotEmpty() &&
            seatIds.all { it in SeatRepository.allSeats } &&
            seatIds.size == seatIds.toSet().size

    /**
     * Turns free text such as " a1, c4 " into ["A1", "C4"].
     * Returns null when the text is blank, has an empty item, an unknown seat or a duplicate.
     */
    fun parse(input: String): List<String>? {
        val seats = input.split(",").map { it.trim().uppercase() }
        return if (isValidSelection(seats)) seats else null
    }
}
