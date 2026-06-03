package com.test.movieapp.presentation.detail

import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.model.Video

data class DetailUiState(
    val movieDetail: MovieDetail? = null,
    val trailer: Video? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
