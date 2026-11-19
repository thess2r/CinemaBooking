
package com.example.cinemabooking.ui.screens
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.ui.components.MovieCard

@Composable
fun MoviesScreen(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredMovies = movies.filter { movie ->
        movie.title.contains(
            searchQuery.trim(),
            ignoreCase = true
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cinema Kit",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E1A17)
        )

        Text(
            text = "Now Showing",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF1E1A17)
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        if (filteredMovies.isEmpty()) {
            Text(
                text = "No movies found",
                color = Color(0xFF756F68)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filteredMovies,
                    key = { movie -> movie.id }
                ) { movie ->
                    MovieCard(
                        movie = movie,
                        onDetailsClick = onMovieClick
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    val movies = MovieRepository().getMovies()

    MoviesScreen(
        movies = movies,
        onMovieClick = {}
    )
}
