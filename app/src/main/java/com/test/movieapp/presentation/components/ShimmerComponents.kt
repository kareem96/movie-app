package com.test.movieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun GenreCardShimmer(modifier: Modifier = Modifier) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .shimmer(shimmerInstance)
            .background(Color(0xFFE0E0E0), MaterialTheme.shapes.medium)
    )
}

@Composable
fun MovieItemShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .shimmer(shimmerInstance)
                .background(Color(0xFFE0E0E0), MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .shimmer(shimmerInstance)
                    .background(Color(0xFFE0E0E0), MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .shimmer(shimmerInstance)
                    .background(Color(0xFFE0E0E0), MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(16.dp)
                    .shimmer(shimmerInstance)
                    .background(Color(0xFFE0E0E0), MaterialTheme.shapes.small)
            )
        }
    }
}

@Composable
fun DetailShimmer() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
    Column(modifier = Modifier.fillMaxSize()) {
        // Backdrop placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .shimmer(shimmerInstance)
                .background(Color(0xFFE0E0E0))
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .shimmer(shimmerInstance)
                    .background(Color(0xFFE0E0E0), MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .shimmer(shimmerInstance)
                            .background(Color(0xFFE0E0E0), MaterialTheme.shapes.small)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
