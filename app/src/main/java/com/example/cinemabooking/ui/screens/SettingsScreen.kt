package com.example.cinemabooking.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.cinemabooking.data.model.BookingSortOrder

@Composable
fun SettingsScreen(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    bookingSortOrder: BookingSortOrder = BookingSortOrder.Default,
    onBookingSortOrderChange: (BookingSortOrder) -> Unit = {},
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextButton(onClick = onBack) { Text("← Back to Movies") }
        Text("Settings", style = MaterialTheme.typography.headlineMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark theme")
            Switch(checked = darkTheme, onCheckedChange = onDarkThemeChange)
        }
        Text("My Bookings order", style = MaterialTheme.typography.titleMedium)
        Column(modifier = Modifier.selectableGroup()) {
            BookingSortOrder.entries.forEach { order ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = order == bookingSortOrder,
                            onClick = { onBookingSortOrderChange(order) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp)
                        .testTag("sort-${order.name}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = order == bookingSortOrder, onClick = null)
                    Text(order.label, modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
    }
}
