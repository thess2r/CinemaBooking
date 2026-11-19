package com.example.cinemabooking.data.repository

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class MovieRepositoryTest {
    @Test
    fun getMovies_returnsFiveMovies() {
        val repository = MovieRepository()
        val movies = repository.getMovies()

        assertEquals(5, movies.size)
    }

    @Test
    fun getMovieById_existingId_returnsCorrectMovie() {
        val repository = MovieRepository()

        val movie = repository.getMovieById(4)

        assertEquals("Dune: Part Two", movie?.title)
    }

    @Test
    fun getMovieById_unknownId_returnsNull() {
        val repository = MovieRepository()

        val movie = repository.getMovieById(999)

        assertNull(movie)
    }

    @Test
    fun searchMovies_ignoresCase() {
        val repository = MovieRepository()

        val movies = repository.searchMovies("INTER")

        assertEquals(1, movies.size)
        assertEquals("Interstellar", movies[0].title)
    }

    @Test
    fun searchMovies_unknownTitle_returnsEmptyList() {
        val repository = MovieRepository()

        val movies = repository.searchMovies("xyz123")

        assertTrue(movies.isEmpty())
    }

    @Test
    fun searchMovies_emptyQuery_returnsAllMovies() {
        val repository = MovieRepository()

        val movies = repository.searchMovies("")

        assertEquals(5, movies.size)
    }




}