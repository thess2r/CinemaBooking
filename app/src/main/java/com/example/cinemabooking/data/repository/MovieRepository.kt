package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.model.Movie

object MovieRepository {
    val movies = listOf(
        Movie(1, "Night Signal", "Thriller", 112, "16+", "A mysterious radio transmission draws a journalist into the city's hidden past."),
        Movie(2, "After the Rain", "Drama", 98, "12+", "Two old friends reconnect during a long summer in Almaty."),
        Movie(3, "Orbit 9", "Sci-Fi", 124, "12+", "A young engineer races to bring a stranded crew home."),
        Movie(4, "Steppe Riders", "Adventure", 106, "12+", "A journey across the steppe becomes a story of courage and friendship."),
        Movie(5, "Little Lantern", "Family", 89, "6+", "A curious child discovers a secret festival of lights.")
    )

    fun getMovieById(id: Int): Movie? = movies.find { it.id == id }

    fun search(query: String): List<Movie> = movies.filter {
        it.title.contains(query.trim(), ignoreCase = true)
    }
}
