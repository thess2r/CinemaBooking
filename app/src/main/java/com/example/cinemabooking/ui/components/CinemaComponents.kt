package com.example.cinemabooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.theme.CinemaAccent
import com.example.cinemabooking.ui.theme.CinemaMuted
import com.example.cinemabooking.ui.theme.CinemaSurface

@Composable
fun PosterPlaceholder(movie: Movie, modifier: Modifier = Modifier) {
    val colors = listOf(
        Color(0xFF27333A), Color(0xFF718283), Color(0xFF514C61),
        Color(0xFF8D654C), Color(0xFF916F65)
    )
    Box(
        modifier = modifier.background(colors[(movie.id - 1) % colors.size], RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = movie.title.uppercase(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun MovieCardPlaceholder(movie: Movie, onDetailsClick: (Int) -> Unit) {    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CinemaSurface)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PosterPlaceholder(movie, Modifier.fillMaxWidth().height(130.dp))
            Text(movie.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("${movie.genre}  •  ${movie.durationMinutes} min  •  ${movie.ageRating}", color = CinemaMuted)
            Button(onClick = { onDetailsClick(movie.id) }, modifier = Modifier.fillMaxWidth()) {
                Text("Details")
            }
        }
    }
}

@Composable
fun ShowtimeButton(showtime: Showtime, selected: Boolean, onClick: () -> Unit) {
    if (selected) Button(onClick = onClick) { Text(showtime.time) }
    else OutlinedButton(onClick = onClick) { Text(showtime.time) }
}

@Composable
fun SeatButton(seatId: String, occupied: Boolean, selected: Boolean, onClick: () -> Unit) {
    if (selected) {
        Button(onClick = onClick, modifier = Modifier.padding(3.dp)) { Text(seatId) }
    } else {
        OutlinedButton(
            onClick = onClick,
            enabled = !occupied,
            modifier = Modifier.padding(3.dp)
        ) { Text(seatId) }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = CinemaMuted)
        Text(value, fontWeight = FontWeight.SemiBold, color = CinemaAccent)
    }
}
