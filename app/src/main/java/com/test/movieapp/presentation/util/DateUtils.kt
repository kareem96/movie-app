package com.test.movieapp.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatReleaseDate(dateString: String?): String {
    if (dateString.isNullOrBlank()) return "-"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: dateString
    } catch (e: Exception) {
        dateString
    }
}
