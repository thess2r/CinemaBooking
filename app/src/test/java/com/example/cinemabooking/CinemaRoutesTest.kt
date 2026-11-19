package com.example.cinemabooking

import com.example.cinemabooking.navigation.CinemaRoutes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CinemaRoutesTest {
    @Test fun detailsRouteCarriesMovieId() {
        assertEquals("details/4", CinemaRoutes.details(4))
    }

    @Test fun seatsRouteCarriesBothIdsInOrder() {
        assertEquals("seats/4/402", CinemaRoutes.seats(4, 402))
    }

    @Test fun invalidIdsCannotBuildRoutes() {
        assertThrows(IllegalArgumentException::class.java) { CinemaRoutes.details(0) }
        assertThrows(IllegalArgumentException::class.java) { CinemaRoutes.seats(4, -1) }
    }
}
