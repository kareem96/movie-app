package com.test.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.movieapp.domain.model.Review
import com.test.movieapp.domain.usecase.GetMovieDetailUseCase
import com.test.movieapp.domain.usecase.GetMovieReviewsUseCase
import com.test.movieapp.domain.usecase.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _movieId = MutableStateFlow(0)
    val reviewsPaging: StateFlow<PagingData<Review>> = _movieId
        .flatMapLatest { movieId ->
            if (movieId == 0) flowOf(PagingData.empty())
            else getMovieReviewsUseCase(movieId)
        }
        .cachedIn(viewModelScope)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        )

    fun loadMovieDetail(movieId: Int) {
        _movieId.value = movieId
        viewModelScope.launch {
            _uiState.value = DetailUiState(isLoading = true)
            try {
                coroutineScope {
                    val detailResult = async { getMovieDetailUseCase(movieId) }
                    val trailerResult = async { getMovieTrailerUseCase(movieId) }
                    
                    val detail = detailResult.await()
                    val trailer = trailerResult.await()
                    
                    _uiState.value = DetailUiState(
                        movieDetail = detail.getOrNull(),
                        trailer = trailer.getOrNull(),
                        isLoading = false,
                        error = if (detail.isFailure) detail.exceptionOrNull()?.message else null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState(
                    isLoading = false,
                    error = e.localizedMessage ?: "An error occurred"
                )
            }
        }
    }
}
