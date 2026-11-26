
package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.cinemabooking.ui.viewmodel.OnlineMoviesUiState
import com.example.cinemabooking.ui.viewmodel.OnlineMoviesViewModel

@Composable
fun DiscoverScreen(
    moviesViewModel: OnlineMoviesViewModel = viewModel()
) {
    val uiState by moviesViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Discover Movies")

        Spacer(modifier = Modifier.height(24.dp))

        when (uiState) {

            is OnlineMoviesUiState.Loading -> {
                CircularProgressIndicator()
            }

            is OnlineMoviesUiState.Success -> {
                Text(text = "Movies loaded successfully")
            }

            is OnlineMoviesUiState.Error -> {
                Text(text = "Failed to load movies")
            }

            is OnlineMoviesUiState.Empty -> {
                Text(text = "No movies available")
            }
        }
    }
}
