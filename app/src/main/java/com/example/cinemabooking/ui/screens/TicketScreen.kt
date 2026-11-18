package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.components.DetailRow
import com.example.cinemabooking.ui.theme.CinemaAccent
import com.example.cinemabooking.ui.theme.CinemaSurface

@Composable
fun TicketScreen(
    booking: Booking,
    movie: Movie,
    showtime: Showtime,
    onBackToMovies: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("Booking confirmed", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Your demo ticket is ready.")
        Card(colors = CardDefaults.cardColors(containerColor = CinemaSurface)) {
            Column(Modifier.padding(20.dp)) {
                Text("CINEMA KIT", color = CinemaAccent, fontWeight = FontWeight.Bold)
                Text("Ticket #${booking.id}", style = MaterialTheme.typography.titleLarge)
                DetailRow("Movie", movie.title)
                DetailRow("Date", "Today")
                DetailRow("Time", showtime.time)
                DetailRow("Hall", showtime.hall.toString())
                DetailRow("Seats", booking.seatIds.joinToString(", "))
                DetailRow("Total", "${booking.totalKzt} ₸")
            }
        }
        Text("Demo ticket — not valid for admission", color = CinemaAccent, fontWeight = FontWeight.Bold)
        Button(onClick = onBackToMovies, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Movies")
        }
    }
}
