package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.booking.formatPriceKzt
import com.example.cinemabooking.ui.components.DetailRow
import com.example.cinemabooking.ui.viewmodel.calculateTotal

@Composable
fun BookingSummaryScreen(
    movie: Movie,
    showtime: Showtime,
    selectedSeats: Set<String>,
    onConfirm: ((Boolean) -> Unit) -> Unit,
    onBack: () -> Unit
) {
    var confirmationOpen by remember { mutableStateOf(false) }
    var saving by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Back to Seats") }
        Text("Booking Summary", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Check your selection before confirming.")
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.padding(20.dp)) {
                DetailRow("Movie", movie.title)
                DetailRow("Date", "Today")
                DetailRow("Time", showtime.time)
                DetailRow("Hall", showtime.hall.toString())
                DetailRow("Seats", selectedSeats.sorted().joinToString(", "))
                DetailRow("Tickets", selectedSeats.size.toString())
                DetailRow("Price per seat", formatPriceKzt(showtime.priceKzt))
                DetailRow("Total", formatPriceKzt(calculateTotal(showtime.priceKzt, selectedSeats)))
            }
        }
        Button(onClick = { confirmationOpen = true }, enabled = selectedSeats.isNotEmpty() && !saving, modifier = Modifier.fillMaxWidth()) {
            Text(if (saving) "Saving booking..." else "Confirm Booking")
        }
        if (saveError) Text("Could not save booking. Check the seats and try again.", color = MaterialTheme.colorScheme.error)
        Text("This is a demo booking. No payment is collected.")
    }
    if (confirmationOpen) {
        val seatLabel = if (selectedSeats.size == 1) "seat" else "seats"
        AlertDialog(
            onDismissRequest = { confirmationOpen = false },
            title = { Text("Confirm demo booking?") },
            text = {
                Text("Book ${selectedSeats.size} $seatLabel for ${formatPriceKzt(calculateTotal(showtime.priceKzt, selectedSeats))}? No payment is collected.")
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmationOpen = false
                    saving = true
                    saveError = false
                    onConfirm { success ->
                        saving = false
                        saveError = !success
                    }
                }) { Text("Book seats") }
            },
            dismissButton = {
                TextButton(onClick = { confirmationOpen = false }) { Text("Cancel") }
            }
        )
    }
}
