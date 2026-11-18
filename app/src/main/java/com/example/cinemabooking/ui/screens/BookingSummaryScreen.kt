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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.components.DetailRow
import com.example.cinemabooking.ui.theme.CinemaSurface
import com.example.cinemabooking.ui.viewmodel.calculateTotal

@Composable
fun BookingSummaryScreen(
    movie: Movie,
    showtime: Showtime,
    selectedSeats: Set<String>,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
        TextButton(onClick = onBack) { Text("← Back to Seats") }
        Text("Booking Summary", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Check your selection before confirming.")
        Card(colors = CardDefaults.cardColors(containerColor = CinemaSurface)) {
            Column(Modifier.padding(20.dp)) {
                DetailRow("Movie", movie.title)
                DetailRow("Date", "Today")
                DetailRow("Time", showtime.time)
                DetailRow("Hall", showtime.hall.toString())
                DetailRow("Seats", selectedSeats.sorted().joinToString(", "))
                DetailRow("Total", "${calculateTotal(showtime.priceKzt, selectedSeats)} ₸")
            }
        }
        Button(onClick = onConfirm, enabled = selectedSeats.isNotEmpty(), modifier = Modifier.fillMaxWidth()) {
            Text("Confirm Booking")
        }
        Text("This is a demo booking. No payment is collected.")
    }
}
