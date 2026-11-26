package com.example.cinemabooking.data.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SeatInputValidatorTest {
    @Test
    fun parse_trimsAndUppercasesSeats() {
        assertEquals(listOf("A1", "C4"), SeatInputValidator.parse(" a1 ,  c4 "))
    }

    @Test
    fun parse_acceptsSingleSeat() {
        assertEquals(listOf("C4"), SeatInputValidator.parse("C4"))
    }

    @Test
    fun parse_rejectsBlankInput() {
        assertNull(SeatInputValidator.parse(""))
        assertNull(SeatInputValidator.parse("   "))
    }

    @Test
    fun parse_rejectsEmptyItems() {
        assertNull(SeatInputValidator.parse("A1,,B2"))
        assertNull(SeatInputValidator.parse("A1,"))
    }

    @Test
    fun parse_rejectsUnknownSeats() {
        assertNull(SeatInputValidator.parse("D1"))
        assertNull(SeatInputValidator.parse("A5"))
        assertNull(SeatInputValidator.parse("A0"))
        assertNull(SeatInputValidator.parse("seat1"))
    }

    @Test
    fun parse_rejectsDuplicatesEvenWithDifferentCase() {
        assertNull(SeatInputValidator.parse("A1, a1"))
    }

    @Test
    fun isValidSelection_acceptsEveryHallSeatOnce() {
        assertTrue(SeatInputValidator.isValidSelection(listOf("A1", "B2", "C4")))
    }

    @Test
    fun isValidSelection_rejectsEmptyList() {
        assertFalse(SeatInputValidator.isValidSelection(emptyList()))
    }

    @Test
    fun isValidSelection_rejectsDuplicatesAndUnknownSeats() {
        assertFalse(SeatInputValidator.isValidSelection(listOf("A1", "A1")))
        assertFalse(SeatInputValidator.isValidSelection(listOf("A1", "Z9")))
    }

    @Test
    fun isValidSelection_isCaseSensitiveBecauseParseNormalizesFirst() {
        assertFalse(SeatInputValidator.isValidSelection(listOf("a1")))
    }
}
