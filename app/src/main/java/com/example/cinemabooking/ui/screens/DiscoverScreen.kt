
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import com.example.cinemabooking.R
import com.example.cinemabooking.data.remote.model.OnlineMovie
import com.example.cinemabooking.ui.viewmodel.OnlineMoviesUiState
import com.example.cinemabooking.ui.viewmodel.OnlineMoviesViewModel

@Composable
fun DiscoverScreen(
    moviesViewModel: OnlineMoviesViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val uiState by moviesViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Back to Movies") }
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Unable to load movies",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = state.message)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { moviesViewModel.retry() }
                    ) {
                        Text(text = "Retry")
                    }
                }
            }

            is OnlineMoviesUiState.Empty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No movies available",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { moviesViewModel.retry() }
                    ) {
                        Text(text = "Try Again")
                    }
                }
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
                error = painterResource(R.drawable.ic_launcher_foreground),
                fallback = painterResource(R.drawable.ic_launcher_foreground),
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
