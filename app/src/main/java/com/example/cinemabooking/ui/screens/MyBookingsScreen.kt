package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.repository.MovieRepository
import com.example.cinemabooking.data.repository.ShowtimeRepository
import com.example.cinemabooking.ui.booking.formatPriceKzt
import com.example.cinemabooking.ui.components.DetailRow

@Composable
fun MyBookingsScreen(
    bookings: List<Booking>,
    onUpdateSeats: (Int, String, (String?) -> Unit) -> Unit,
    onDelete: (Booking) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        TextButton(onClick = onBack) { Text("← Back to Movies") }
        Text("My Bookings", style = MaterialTheme.typography.headlineMedium)
        if (bookings.isEmpty()) {
            Text("No bookings yet", modifier = Modifier.padding(top = 24.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(bookings, key = { it.id }) { booking ->
                    BookingCard(booking, onUpdateSeats, onDelete)
                }
            }
        }
    }
}

@Composable
private fun BookingCard(
    booking: Booking,
    onUpdateSeats: (Int, String, (String?) -> Unit) -> Unit,
    onDelete: (Booking) -> Unit
) {
    var editing by remember(booking.id) { mutableStateOf(false) }
    var seatInput by remember(booking.id, booking.seatIds) {
        mutableStateOf(booking.seatIds.joinToString(", "))
    }
    var error by remember(booking.id) { mutableStateOf<String?>(null) }
    var saving by remember(booking.id) { mutableStateOf(false) }
    var confirmDelete by remember(booking.id) { mutableStateOf(false) }
    val movieTitle = MovieRepository.getMovieById(booking.movieId)?.title ?: "Movie #${booking.movieId}"
    val showtime = ShowtimeRepository.getShowtimeById(booking.showtimeId)

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Booking #${booking.id}", style = MaterialTheme.typography.titleMedium)
            DetailRow("Movie", movieTitle)
            DetailRow("Time", showtime?.time ?: "Session #${booking.showtimeId}")
            DetailRow("Seats", booking.seatIds.joinToString(", "))
            DetailRow("Total", formatPriceKzt(booking.totalKzt))
            if (editing) {
                OutlinedTextField(
                    value = seatInput,
                    onValueChange = { seatInput = it; error = null },
                    label = { Text("Seats, for example A1, C4") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        enabled = !saving,
                        onClick = {
                            saving = true
                            onUpdateSeats(booking.id, seatInput) { message ->
                                saving = false
                                error = message
                                if (message == null) editing = false
                            }
                        }
                    ) { Text(if (saving) "Saving..." else "Save seats") }
                    TextButton(onClick = { editing = false; error = null }) { Text("Cancel") }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = { editing = true }) { Text("Edit seats") }
                    TextButton(onClick = { confirmDelete = true }) { Text("Delete") }
                }
            }
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete this booking?") },
            text = { Text("The saved booking will be removed and its seats will become available.") },
            confirmButton = {
                TextButton(onClick = { confirmDelete = false; onDelete(booking) }) { Text("Delete booking") }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text("Cancel") }
            }
        )
    }
}
