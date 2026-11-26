package com.example.cinemabooking.navigation

/** Route patterns and builders live together so arguments cannot be swapped accidentally. */
object CinemaRoutes {
    const val MOVIES = "movies"
    const val DETAILS = "details/{movieId}"
    const val SEATS = "seats/{movieId}/{showtimeId}"
    const val SUMMARY = "summary"
    const val TICKET = "ticket"
    const val SETTINGS = "settings"
    const val MY_BOOKINGS = "my_bookings"
    const val DISCOVER = "discover"

    fun details(movieId: Int): String {
        require(movieId > 0) { "movieId must be positive" }
        return "details/$movieId"
    }

    fun seats(movieId: Int, showtimeId: Int): String {
        require(movieId > 0 && showtimeId > 0) { "Route IDs must be positive" }
        return "seats/$movieId/$showtimeId"
    }
}
