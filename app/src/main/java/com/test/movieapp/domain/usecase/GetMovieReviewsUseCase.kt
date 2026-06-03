package com.test.movieapp.domain.usecase

import androidx.paging.PagingData
import com.test.movieapp.domain.model.Review
import com.test.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<PagingData<Review>> =
        repository.getMovieReviews(movieId)
}
