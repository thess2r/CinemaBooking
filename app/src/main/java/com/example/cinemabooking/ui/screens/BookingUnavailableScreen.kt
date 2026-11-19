package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookingUnavailableScreen(message: String, onBackToMovies: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Booking unavailable", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(message)
        Button(onClick = onBackToMovies, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Movies")
        }
    }
}
