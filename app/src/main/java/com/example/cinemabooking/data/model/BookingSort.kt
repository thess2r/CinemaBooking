package com.example.cinemabooking.data.model

/** How the My Bookings list is ordered. The choice is stored in DataStore by its [name]. */
enum class BookingSortOrder(val label: String) {
    NEWEST_FIRST("Newest first"),
    OLDEST_FIRST("Oldest first"),
    PRICE_HIGH_TO_LOW("Highest price first");

    companion object {
        val Default = NEWEST_FIRST

        /** Reads a stored value; anything missing or unknown falls back to [Default]. */
        fun fromStorage(value: String?): BookingSortOrder =
            entries.firstOrNull { it.name == value } ?: Default
    }
}

/**
 * Orders bookings for display. Room ids grow with every insert, so the id is used as
 * the creation order. Equal prices keep the newest booking first.
 */
fun sortBookings(bookings: List<Booking>, order: BookingSortOrder): List<Booking> =
    when (order) {
        BookingSortOrder.NEWEST_FIRST -> bookings.sortedByDescending { it.id }
        BookingSortOrder.OLDEST_FIRST -> bookings.sortedBy { it.id }
        BookingSortOrder.PRICE_HIGH_TO_LOW ->
            bookings.sortedWith(
                compareByDescending<Booking> { it.totalKzt }.thenByDescending { it.id }
            )
    }
