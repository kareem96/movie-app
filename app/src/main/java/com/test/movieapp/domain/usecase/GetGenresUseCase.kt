package com.test.movieapp.domain.usecase

import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Genre>> = repository.getGenres()
}
