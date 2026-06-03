package com.test.movieapp.domain.usecase

import androidx.paging.PagingData
import com.test.movieapp.domain.model.Movie
import com.test.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: String?): Flow<PagingData<Movie>> =
        repository.discoverMovies(genreId)
}
