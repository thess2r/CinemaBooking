package com.example.cinemabooking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val Accent = Color(0xFFC6512D)
private val TextColor = Color(0xFF1E1A17)
private val BorderColor = Color(0xFFE3DDD6)

@Composable
fun ShowtimeButton(
    time: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    if (isSelected) {
        Button(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Accent,
                contentColor = Color.White
            )
        ) {
            Text(time)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(48.dp),
            shape = shape,
            border = BorderStroke(1.dp, BorderColor),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextColor)
        ) {
            Text(time)
        }
    }
}