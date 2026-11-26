package com.example.cinemabooking.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CinemaColorScheme = lightColorScheme(
    primary = CinemaAccent,
    onPrimary = CinemaSurface,
    secondary = CinemaMuted,
    background = CinemaBackground,
    onBackground = CinemaText,
    surface = CinemaSurface,
    onSurface = CinemaText,
    outline = CinemaBorder
)

@Composable
fun CinemaBookingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CinemaColorScheme,
        typography = Typography,
        content = content
    )
}
