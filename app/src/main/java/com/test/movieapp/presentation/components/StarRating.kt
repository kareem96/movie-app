package com.test.movieapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    voteAverage: Double,
    modifier: Modifier = Modifier,
    showNumber: Boolean = true
) {
    val starCount = 5
    val normalizedRating = (voteAverage / 2).coerceIn(0.0, 5.0)
    val fullStars = normalizedRating.toInt()
    val hasHalfStar = (normalizedRating - fullStars) >= 0.5

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(starCount) { index ->
            val icon = when {
                index < fullStars -> Icons.Default.Star
                index == fullStars && hasHalfStar -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Default.StarOutline
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
        }
        if (showNumber) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = String.format("%.1f", voteAverage),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
