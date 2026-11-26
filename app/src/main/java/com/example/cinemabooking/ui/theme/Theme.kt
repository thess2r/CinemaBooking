package com.example.cinemabooking.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
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

private val CinemaDarkColorScheme = darkColorScheme(
    primary = CinemaDarkAccent,
    onPrimary = CinemaDarkBackground,
    secondary = CinemaDarkMuted,
    background = CinemaDarkBackground,
    onBackground = CinemaDarkText,
    surface = CinemaDarkSurface,
    onSurface = CinemaDarkText,
    outline = CinemaDarkBorder
)

@Composable
fun CinemaBookingTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) CinemaDarkColorScheme else CinemaColorScheme,
        typography = Typography,
        content = content
    )
}
