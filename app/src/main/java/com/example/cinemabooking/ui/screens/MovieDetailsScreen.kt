package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemabooking.R
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.components.ShowtimeButton

private val Background = Color(0xFFF1EDE8)
private val TextColor = Color(0xFF1E1A17)
private val Accent = Color(0xFFC6512D)
private val Muted = Color(0xFF756F68)
private val Border = Color(0xFFE3DDD6)

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
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("← Back", color = Accent, fontSize = 16.sp)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val posterResId = movie.posterResId
            if (posterResId != null) {
                Image(
                    painter = painterResource(id = posterResId),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Accent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = movie.title,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.title,
                color = TextColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${movie.genre} · ${movie.durationMinutes} min · ${movie.ageRating}",
                color = Muted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.description,
                color = TextColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Showtimes",
                color = TextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                showtimes.forEach { showtime ->
                    ShowtimeButton(
                        time = showtime.time,
                        isSelected = showtime.id == selectedShowtimeId,
                        onClick = { onSelectShowtime(showtime.id) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            val selectedShowtime = showtimes.find { it.id == selectedShowtimeId }
            if (selectedShowtime != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Today · Hall ${selectedShowtime.hall} · ${selectedShowtime.priceKzt} ₸ per seat",
                    color = Muted,
                    fontSize = 14.sp
                )
            }
        }

        Button(
            onClick = onChooseSeats,
            enabled = selectedShowtimeId != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent,
                contentColor = Color.White,
                disabledContainerColor = Border,
                disabledContentColor = Muted
            )
        ) {
            Text("Choose Seats")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsScreenPreview() {
    var selectedId by remember { mutableStateOf<Int?>(null) }
    MovieDetailsScreen(
        movie = Movie(
            id = 4,
            title = "Steppe Riders",
            genre = "Adventure",
            durationMinutes = 136,
            ageRating = "12+",
            description = "A group of riders crosses the steppe in search of a lost herd.",
            posterResId = R.drawable.dune
        ),
        showtimes = ShowtimeRepository.getShowtimesForMovie(4),
        selectedShowtimeId = selectedId,
        onSelectShowtime = { selectedId = it },
        onChooseSeats = {},
        onBack = {}
    )
}