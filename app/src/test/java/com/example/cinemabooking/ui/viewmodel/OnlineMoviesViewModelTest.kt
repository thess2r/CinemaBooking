
package com.example.cinemabooking.ui.viewmodel

import com.example.cinemabooking.data.remote.model.OnlineMovie
import com.example.cinemabooking.data.repository.OnlineMoviesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnlineMoviesViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun sampleMovie() = OnlineMovie(
        id = "1",
        title = "Test Movie",
        description = "Test description",
        image = null,
        runningTime = "120"
    )

    private class FakeRepository(
        var movies: List<OnlineMovie> = emptyList(),
        var shouldFail: Boolean = false
    ) : OnlineMoviesDataSource {

        var callCount = 0

        override suspend fun getMovies(): List<OnlineMovie> {
            callCount++

            if (shouldFail) {
                throw java.io.IOException("Network error")
            }

            return movies
        }
    }

    @Test
    fun loadMovies_success() = runTest(dispatcher) {
        val fake = FakeRepository(
            movies = listOf(sampleMovie())
        )

        val viewModel = OnlineMoviesViewModel(fake)
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is OnlineMoviesUiState.Success)
        assertEquals(
            1,
            (state as OnlineMoviesUiState.Success).movies.size
        )
    }

    @Test
    fun loadMovies_empty() = runTest(dispatcher) {
        val fake = FakeRepository()

        val viewModel = OnlineMoviesViewModel(fake)
        advanceUntilIdle()

        assertEquals(
            OnlineMoviesUiState.Empty,
            viewModel.uiState.value
        )
    }

    @Test
    fun loadMovies_error() = runTest(dispatcher) {
        val fake = FakeRepository(
            shouldFail = true
        )

        val viewModel = OnlineMoviesViewModel(fake)
        advanceUntilIdle()

        assertTrue(
            viewModel.uiState.value is OnlineMoviesUiState.Error
        )
    }

    @Test
    fun retry_loadsMoviesAgain() = runTest(dispatcher) {
        val fake = FakeRepository(
            shouldFail = true
        )

        val viewModel = OnlineMoviesViewModel(fake)
        advanceUntilIdle()

        assertEquals(1, fake.callCount)

        fake.shouldFail = false
        fake.movies = listOf(sampleMovie())

        viewModel.retry()
        advanceUntilIdle()

        assertEquals(2, fake.callCount)
        assertTrue(
            viewModel.uiState.value is OnlineMoviesUiState.Success
        )
    }
}
