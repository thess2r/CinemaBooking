
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.repository.MovieRepository

private val CardBackground = Color(0xFFFAF8F5)
private val TextColor = Color(0xFF1E1A17)
private val AccentColor = Color(0xFFC6512D)
private val MutedColor = Color(0xFF756F68)

@Composable
fun MovieCard(
    movie: Movie,
    onDetailsClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        color = Color(0xFFE3DDD6),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CINEMA KIT",
                    fontSize = 16.sp,
                    color = MutedColor
                )
            }

            Text(
                text = movie.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Text(
                text = movie.genre,
                fontSize = 14.sp,
                color = MutedColor
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "${movie.durationMinutes} min",
                    fontSize = 14.sp,
                    color = MutedColor
                )

                Text(
                    text = movie.ageRating,
                    fontSize = 14.sp,
                    color = MutedColor
                )
            }

            Button(
                onClick = { onDetailsClick(movie.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "View Details")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    val movie = MovieRepository().getMovieById(4)

    if (movie != null) {
        MovieCard(
            movie = movie,
            onDetailsClick = {}
        )
    }
}
