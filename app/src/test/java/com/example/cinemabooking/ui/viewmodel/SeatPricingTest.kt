package com.example.cinemabooking.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test

class SeatPricingTest {

    @Test
    fun emptySelectionCostsZero() {
        assertEquals(
            0,
            calculateTotal(2500, emptySet())
        )
    }

    @Test
    fun oneSeatCosts2500() {
        assertEquals(
            2500,
            calculateTotal(2500, setOf("C4"))
        )
    }

    @Test
    fun twoSeatsCost5000() {
        assertEquals(
            5000,
            calculateTotal(2500, setOf("C4", "A1"))
        )
    }
}