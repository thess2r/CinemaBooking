
package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.model.Movie

class MovieRepository {

    private val movies = listOf(
        Movie(
            id = 1,
            title = "The Last Harbour",
            genre = "Drama",
            durationMinutes = 128,
            ageRating = "12+",
            description = "A dramatic story set in a coastal town."
        ),
        Movie(
            id = 2,
            title = "Night Signal",
            genre = "Thriller",
            durationMinutes = 112,
            ageRating = "16+",
            description = "A mysterious signal leads to an unexpected discovery."
        ),
        Movie(
            id = 3,
            title = "Saffron Summer",
            genre = "Romance",
            durationMinutes = 104,
            ageRating = "12+",
            description = "A summer story about friendship and relationships."
        ),
        Movie(
            id = 4,
            title = "Steppe Riders",
            genre = "Adventure",
            durationMinutes = 136,
            ageRating = "12+",
            description = "An adventure across the vast steppes."
        ),
        Movie(
            id = 5,
            title = "Quiet Orbit",
            genre = "Sci-Fi",
            durationMinutes = 121,
            ageRating = "16+",
            description = "An exploration mission uncovers a mystery in space."
        )
    )

    fun getMovies(): List<Movie> {
        return movies
    }

    fun getMovieById(id: Int): Movie? {
        return movies.find { movie ->
            movie.id == id
        }
    }

    fun searchMovies(query: String): List<Movie> {
        val searchText = query.trim()

        return movies.filter { movie ->
            movie.title.contains(searchText, ignoreCase = true)
        }
    }
}
