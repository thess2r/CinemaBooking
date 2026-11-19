package com.example.cinemabooking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.repository.MovieRepository

@Composable
fun MovieCard(
    movie: Movie,
    onDetailsClick: (Int) -> Unit
) {
    Card(
        onClick = { onDetailsClick(movie.id) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = movie.title)
            Text(text = movie.genre)
            Text(text = "${movie.durationMinutes} min")
            Text(text = movie.ageRating)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val repository = MovieRepository()
    val movie = repository.getMovieById(4)

    if (movie != null) {
        MovieCard(
            movie = movie,
            onDetailsClick = {}
        )
    }
}
