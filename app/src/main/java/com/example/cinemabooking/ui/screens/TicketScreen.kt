package com.example.cinemabooking.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.Booking
import com.example.cinemabooking.data.model.Movie
import com.example.cinemabooking.data.model.Showtime
import com.example.cinemabooking.ui.booking.formatPriceKzt
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
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Booking confirmed", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Your demo ticket is ready.")
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CinemaSurface)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("CINEMA KIT", color = CinemaAccent, fontWeight = FontWeight.Bold)
                Text("Ticket #${booking.id}", style = MaterialTheme.typography.titleLarge)
                DetailRow("Movie", movie.title)
                DetailRow("Date", "Today")
                DetailRow("Time", showtime.time)
                DetailRow("Hall", showtime.hall.toString())
                DetailRow("Seats", booking.seatIds.joinToString(", "))
                DetailRow("Total", formatPriceKzt(booking.totalKzt))
            }
        }
        OutlinedButton(
            onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("Cinema Kit ticket number", booking.id.toString()))
                Toast.makeText(context, "Ticket number copied", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Copy ticket number") }
        Text("Demo ticket — not valid for admission", color = CinemaAccent, fontWeight = FontWeight.Bold)
        Button(onClick = onBackToMovies, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Movies")
        }
    }
}
