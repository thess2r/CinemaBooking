package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.components.PosterPlaceholder
import com.example.cinemabooking.ui.components.ShowtimeButton
import com.example.cinemabooking.ui.theme.CinemaMuted

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    showtimes: List<Showtime>,
    selectedShowtimeId: Int?,
    onSelectShowtime: (Int) -> Unit,
    onChooseSeats: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Back to Movies") }
        PosterPlaceholder(movie, Modifier.fillMaxWidth().height(210.dp))
        Text(movie.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("${movie.genre}  •  ${movie.durationMinutes} min  •  ${movie.ageRating}", color = CinemaMuted)
        Text(movie.description)
        Text("Today's showtimes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            showtimes.forEach { showtime ->
                ShowtimeButton(
                    showtime = showtime,
                    selected = selectedShowtimeId == showtime.id,
                    onClick = { onSelectShowtime(showtime.id) }
                )
            }
        }
        Text("Hall 2  •  2 500 ₸ per seat", color = CinemaMuted)
        Button(
            onClick = onChooseSeats,
            enabled = selectedShowtimeId != null,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Choose Seats") }
    }
}
