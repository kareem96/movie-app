package com.test.movieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.movieapp.data.remote.api.ApiConstants

@Composable
fun MoviePosterImage(
    path: String?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    baseUrl: String = ApiConstants.IMAGE_BASE_URL_W185
) {
    val imageUrl = path?.let { "$baseUrl$it" }

    if (imageUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Color(0xFFE0E0E0)),
            error = painterResource(id = android.R.drawable.ic_menu_gallery),
            modifier = modifier
        )
    } else {
        Box(
            modifier = modifier.background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "No image",
                tint = Color(0xFF9E9E9E),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
