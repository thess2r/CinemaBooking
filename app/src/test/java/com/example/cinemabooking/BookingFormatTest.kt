package com.example.cinemabooking

import com.example.cinemabooking.ui.booking.formatPriceKzt
import org.junit.Assert.assertEquals
import org.junit.Test

class BookingFormatTest {
    @Test fun pricesUseReadableKztGrouping() {
        assertEquals("0 ₸", formatPriceKzt(0))
        assertEquals("2 500 ₸", formatPriceKzt(2500))
        assertEquals("125 000 ₸", formatPriceKzt(125000))
    }
}
