package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.ui.components.MovieCard
import com.example.cinemabooking.ui.theme.CinemaMuted

@Composable
fun MoviesScreen(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    var query by remember { mutableStateOf("") }
    val visibleMovies = movies.filter { it.title.contains(query.trim(), ignoreCase = true) }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("CINEMA KIT", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Text("Now Showing", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Find your next movie night", color = CinemaMuted)
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search by title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp)
        )
        if (visibleMovies.isEmpty()) {
            Text("No movies found", modifier = Modifier.padding(top = 24.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(visibleMovies, key = { it.id }) { movie ->
                    MovieCard(movie = movie, onDetailsClick = onMovieClick)
                }
            }
        }
    }
}
