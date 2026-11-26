package com.example.cinemabooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemabooking.data.repository.ThemePreferencesRepository
import com.example.cinemabooking.navigation.AppNavigation
import com.example.cinemabooking.ui.theme.CinemaBookingTheme
import com.example.cinemabooking.ui.viewmodel.ThemeViewModel
import com.example.cinemabooking.ui.viewmodel.ThemeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeRepository = remember { ThemePreferencesRepository(applicationContext) }
            val themeViewModel: ThemeViewModel = viewModel(factory = ThemeViewModelFactory(themeRepository))
            val darkTheme by themeViewModel.darkTheme.collectAsState()
            CinemaBookingTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(themeViewModel = themeViewModel)
                }
            }
        }
    }
}
