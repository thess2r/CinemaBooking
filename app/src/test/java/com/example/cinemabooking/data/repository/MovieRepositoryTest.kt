package com.example.cinemabooking.data.repository

import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieRepositoryTest {
    @Test
    fun getMovies_returnsFiveMovies() {
        val repository = MovieRepository()
        val movies = repository.getMovies()

        assertEquals(5, movies.size)
    }
}