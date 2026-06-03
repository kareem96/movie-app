package com.test.movieapp.presentation.genre

import com.test.movieapp.domain.model.Genre

sealed class GenreUiState {
    object Loading : GenreUiState()
    data class Success(val genres: List<Genre>) : GenreUiState()
    data class Error(val message: String) : GenreUiState()
}
