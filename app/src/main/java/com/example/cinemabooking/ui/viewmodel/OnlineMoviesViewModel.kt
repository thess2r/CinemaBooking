
package com.example.cinemabooking.ui.viewmodel
import com.example.cinemabooking.data.repository.OnlineMoviesDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemabooking.data.remote.model.OnlineMovie
import com.example.cinemabooking.data.repository.OnlineMovieRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface OnlineMoviesUiState {
    data object Loading : OnlineMoviesUiState

    data class Success(
        val movies: List<OnlineMovie>
    ) : OnlineMoviesUiState

    data object Empty : OnlineMoviesUiState

    data class Error(
        val message: String
    ) : OnlineMoviesUiState
}

class OnlineMoviesViewModel(
    private val repository: OnlineMoviesDataSource =
        OnlineMovieRepository()
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<OnlineMoviesUiState>(
            OnlineMoviesUiState.Loading
        )

    val uiState: StateFlow<OnlineMoviesUiState> =
        _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = OnlineMoviesUiState.Loading

            try {
                val movies = repository.getMovies()

                _uiState.value = if (movies.isEmpty()) {
                    OnlineMoviesUiState.Empty
                } else {
                    OnlineMoviesUiState.Success(movies)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = OnlineMoviesUiState.Error(
                    "Failed to load movies. Check your internet connection."
                )
            }
        }
    }

    fun retry() {
        loadMovies()
    }
}
