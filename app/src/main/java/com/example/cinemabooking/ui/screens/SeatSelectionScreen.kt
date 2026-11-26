package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.data.repository.SeatRepository
import com.example.cinemabooking.ui.components.DetailRow
import com.example.cinemabooking.ui.components.SeatButton
import com.example.cinemabooking.ui.theme.CinemaMuted
import com.example.cinemabooking.ui.viewmodel.calculateTotal

@Composable
fun SeatSelectionScreen(
    movie: Movie,
    showtime: Showtime,
    occupiedSeats: Set<String>,
    selectedSeats: Set<String>,
    onSeatClick: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Back to Details") }
        Text("Select Seats", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("${movie.title}  •  Today ${showtime.time}  •  Hall ${showtime.hall}", color = CinemaMuted)
        HorizontalDivider()
        Text("SCREEN", modifier = Modifier.fillMaxWidth(), color = CinemaMuted)
        ('A'..'C').forEach { row ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                (1..4).forEach { number ->
                    val seatId = "$row$number"
                    SeatButton(
                        seatId = seatId,
                        occupied = seatId in occupiedSeats,
                        selected = seatId in selectedSeats,
                        onClick = { onSeatClick(seatId) }
                    )
                }
            }
        }
        Text("Available: outline    Selected: orange    Occupied: disabled", color = CinemaMuted)
        HorizontalDivider()
        DetailRow("Seats", if (selectedSeats.isEmpty()) "None" else selectedSeats.sorted().joinToString(", "))
        DetailRow("Total", "${calculateTotal(showtime.priceKzt, selectedSeats)} ₸")
        Button(
            onClick = onContinue,
            enabled = selectedSeats.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Continue") }
    }
}
