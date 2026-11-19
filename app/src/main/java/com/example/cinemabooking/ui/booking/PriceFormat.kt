package com.example.cinemabooking.ui.booking

import java.text.NumberFormat
import java.util.Locale

/** Keeps the demo price readable and consistent on Summary and Ticket. */
fun formatPriceKzt(amount: Int): String =
    NumberFormat.getIntegerInstance(Locale.US).format(amount).replace(',', ' ') + " ₸"
