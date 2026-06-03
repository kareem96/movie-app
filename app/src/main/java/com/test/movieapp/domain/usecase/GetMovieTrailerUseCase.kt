package com.test.movieapp.domain.usecase

import com.test.movieapp.domain.model.Video
import com.test.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Video?> {
        return repository.getMovieVideos(movieId).map { videos ->
            videos.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
        }
    }
}
