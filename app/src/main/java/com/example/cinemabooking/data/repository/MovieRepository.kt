package com.example.cinemabooking.data.repository

import com.example.cinemabooking.data.model.Movie

class MovieRepository {

    private val movies = listOf(
        Movie(
            id = 1,
            title = "Interstellar",
            genre = "Sci-Fi / Drama",
            durationMinutes = 169,
            ageRating = "PG-13",
            description = "A team of astronauts travels through space to find a new home for humanity."
        ),
        Movie(
            id = 2,
            title = "Inception",
            genre = "Sci-Fi / Thriller",
            durationMinutes = 148,
            ageRating = "PG-13",
            description = "A skilled thief enters people's dreams to steal and plant ideas."
        ),
        Movie(
            id = 3,
            title = "Spider-Man: No Way Home",
            genre = "Action / Adventure",
            durationMinutes = 148,
            ageRating = "PG-13",
            description = "Peter Parker faces unexpected challenges after his secret identity becomes public."
        ),
        Movie(
            id = 4,
            title = "Dune: Part Two",
            genre = "Sci-Fi / Adventure",
            durationMinutes = 166,
            ageRating = "PG-13",
            description = "Paul Atreides joins the Fremen and fights for the future of Arrakis."
        ),
        Movie(
            id = 5,
            title = "Inside Out 2",
            genre = "Animation / Comedy",
            durationMinutes = 96,
            ageRating = "PG",
            description = "Riley faces new emotions as she enters her teenage years."
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

}
