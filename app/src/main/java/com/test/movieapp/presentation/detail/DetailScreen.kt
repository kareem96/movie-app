package com.test.movieapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.movieapp.data.remote.api.ApiConstants
import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.model.Review
import com.test.movieapp.presentation.components.MoviePosterImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    onThemeToggle: () -> Unit,
    isDarkTheme: Boolean,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyReviewItems = viewModel.reviewsPaging.collectAsLazyPagingItems()

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    val backdropHeightPx = with(LocalDensity.current) { 280.dp.toPx() }

    val appBarAlpha by remember {
        derivedStateOf {
            val offset = listState.layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == 0 }?.offset ?: -backdropHeightPx.toInt()
            (-offset / backdropHeightPx).coerceIn(0f, 1f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.error != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = uiState.error ?: "Failed to load movie details",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(onClick = { viewModel.loadMovieDetail(movieId) }) {
                        Text("Retry")
                    }
                }
            }
            uiState.movieDetail != null -> {
                val detail = uiState.movieDetail!!
                val appendReviewState = lazyReviewItems.loadState.append

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding() + 16.dp
                    )
                ) {
                    // 1. Backdrop Image
                    item {
                        BackdropSection(
                            backdropPath = detail.backdropPath,
                            title = detail.title,
                            height = 280.dp
                        )
                    }

                    // 2. Info Section
                    item {
                        InfoSection(detail)
                    }

                    // 3. Overview Section
                    item {
                        OverviewSection(detail.overview)
                    }

                    // 4. Trailer Section
                    item {
                        TrailerSection(uiState.trailer?.key)
                    }

                    // 5. Reviews Header
                    item {
                        Text(
                            text = "Reviews",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    // Reviews Empty State checking
                    if (lazyReviewItems.loadState.refresh is LoadState.NotLoading && lazyReviewItems.itemCount == 0) {
                        item {
                            Text(
                                text = "No reviews yet",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Reviews Paging List
                    items(
                        count = lazyReviewItems.itemCount,
                        key = lazyReviewItems.itemKey { it.id },
                        contentType = lazyReviewItems.itemContentType { "review" }
                    ) { index ->
                        lazyReviewItems[index]?.let { review ->
                            ReviewItem(
                                review = review,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    // Review pagination loading states
                    if (appendReviewState is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else if (appendReviewState is LoadState.Error) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = appendReviewState.error.localizedMessage ?: "Failed to load more reviews",
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = { lazyReviewItems.retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Overlay AppBar — selalu tampil di atas
        val title = uiState.movieDetail?.title ?: ""
        TopAppBar(
            title = {
                if (appBarAlpha > 0.5f) {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = Color.Black.copy(alpha = (1f - appBarAlpha) * 0.4f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = if (appBarAlpha > 0.5f)
                            MaterialTheme.colorScheme.onSurface
                        else Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onThemeToggle,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = Color.Black.copy(alpha = (1f - appBarAlpha) * 0.4f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode
                                      else Icons.Default.DarkMode,
                        contentDescription = "Toggle theme",
                        tint = if (appBarAlpha > 0.5f)
                            MaterialTheme.colorScheme.onSurface
                        else Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = appBarAlpha)
            ),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun BackdropSection(backdropPath: String?, title: String, height: Dp) {
    val backdropUrl = backdropPath?.let { "${ApiConstants.IMAGE_BASE_URL_W780}$it" }
    if (backdropUrl != null) {
        AsyncImage(
            model = backdropUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Backdrop Image",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun InfoSection(detail: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        MoviePosterImage(
            path = detail.posterPath,
            contentDescription = detail.title,
            baseUrl = ApiConstants.IMAGE_BASE_URL_W92,
            modifier = Modifier
                .width(120.dp)
                .height(180.dp)
                .clip(MaterialTheme.shapes.medium)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Info Column
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = detail.title,
                style = MaterialTheme.typography.headlineSmall
            )
            if (!detail.tagline.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\"${detail.tagline}\"",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Release Date: ${detail.releaseDate ?: "-"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Runtime: ${detail.runtime?.let { "$it min" } ?: "-"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format("%.1f", detail.voteAverage),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                detail.genres.forEach { genre ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(genre.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun OverviewSection(overview: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = overview.ifBlank { "No overview available." },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun TrailerSection(videoKey: String?) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Trailer",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (videoKey != null) {
            val thumbnailUrl = "https://img.youtube.com/vi/$videoKey/maxresdefault.jpg"

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        val uri = android.net.Uri.parse(
                            "https://www.youtube.com/watch?v=$videoKey"
                        )
                        context.startActivity(
                            android.content.Intent(android.content.Intent.ACTION_VIEW, uri)
                        )
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Trailer thumbnail",
                    contentScale = ContentScale.Crop,
                    placeholder = ColorPainter(Color(0xFF1A1A1A)),
                    error = ColorPainter(Color(0xFF1A1A1A)),
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp)
                        .background(Color.Black.copy(alpha = 0.7f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play trailer",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tap to watch on YouTube",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "▶",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No trailer available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.author,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                val displayDate = review.createdAt.substringBefore("T")
                Text(
                    text = displayDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
