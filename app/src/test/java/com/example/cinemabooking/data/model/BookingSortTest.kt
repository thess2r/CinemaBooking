package com.example.cinemabooking.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BookingSortTest {
    private fun booking(id: Int, total: Int) = Booking(
        id = id,
        movieId = 4,
        showtimeId = 402,
        seatIds = listOf("C4"),
        totalKzt = total
    )

    private val sample = listOf(
        booking(id = 2, total = 5000),
        booking(id = 1, total = 2500),
        booking(id = 3, total = 5000),
        booking(id = 4, total = 7500)
    )

    @Test
    fun newestFirst_ordersByIdDescending() {
        assertEquals(
            listOf(4, 3, 2, 1),
            sortBookings(sample, BookingSortOrder.NEWEST_FIRST).map { it.id }
        )
    }

    @Test
    fun oldestFirst_ordersByIdAscending() {
        assertEquals(
            listOf(1, 2, 3, 4),
            sortBookings(sample, BookingSortOrder.OLDEST_FIRST).map { it.id }
        )
    }

    @Test
    fun priceHighToLow_ordersByTotalThenNewest() {
        assertEquals(
            listOf(4, 3, 2, 1),
            sortBookings(sample, BookingSortOrder.PRICE_HIGH_TO_LOW).map { it.id }
        )
    }

    @Test
    fun sorting_doesNotChangeTheOriginalList() {
        val before = sample.map { it.id }
        sortBookings(sample, BookingSortOrder.OLDEST_FIRST)
        assertEquals(before, sample.map { it.id })
    }

    @Test
    fun emptyList_staysEmptyForEveryOrder() {
        BookingSortOrder.entries.forEach { order ->
            assertTrue(sortBookings(emptyList(), order).isEmpty())
        }
    }

    @Test
    fun fromStorage_returnsSavedOrder() {
        BookingSortOrder.entries.forEach { order ->
            assertEquals(order, BookingSortOrder.fromStorage(order.name))
        }
    }

    @Test
    fun fromStorage_fallsBackToDefaultForMissingOrUnknownValue() {
        assertEquals(BookingSortOrder.Default, BookingSortOrder.fromStorage(null))
        assertEquals(BookingSortOrder.Default, BookingSortOrder.fromStorage(""))
        assertEquals(BookingSortOrder.Default, BookingSortOrder.fromStorage("PRICE_LOW_TO_HIGH"))
    }
}
