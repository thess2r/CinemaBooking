
package com.example.cinemabooking.ui.screens

import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.cinemabooking.data.remote.model.OnlineMovie
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
            .padding(16.dp)
    ) {
        Text(text = "Discover Movies")

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is OnlineMoviesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is OnlineMoviesUiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.movies,
                        key = { movie -> movie.id }
                    ) { movie ->
                        OnlineMovieItem(movie)
                    }
                }
            }

            is OnlineMoviesUiState.Error -> {
                Text(text = state.message)
            }

            is OnlineMoviesUiState.Empty -> {
                Text(text = "No movies available")
            }
        }
    }
}


@Composable
fun OnlineMovieItem(movie: OnlineMovie) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            AsyncImage(
                model = movie.image,
                contentDescription = "Poster of ${movie.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Text(text = movie.title)

            Text(
                text = "Duration: ${movie.runningTime} min"
            )

            Text(
                text = movie.description,
                maxLines = 3
            )
        }
    }
}
