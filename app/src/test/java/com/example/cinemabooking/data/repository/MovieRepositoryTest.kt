package com.example.cinemabooking.data.repository

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
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


}