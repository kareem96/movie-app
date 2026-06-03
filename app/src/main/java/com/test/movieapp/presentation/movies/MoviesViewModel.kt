package com.test.movieapp.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.movieapp.domain.model.Movie
import com.test.movieapp.domain.usecase.DiscoverMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val discoverMoviesUseCase: DiscoverMoviesUseCase
) : ViewModel() {

    private val _genreId = MutableStateFlow<String?>(null)

    val movies: StateFlow<PagingData<Movie>> = _genreId
        .flatMapLatest { genreId -> discoverMoviesUseCase(genreId) }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun setGenreId(genreId: String) {
        _genreId.value = genreId
    }
}
