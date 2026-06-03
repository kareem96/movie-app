package com.test.movieapp.domain.usecase

import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetMovieDetailUseCaseTest {

    private val repository = mockk<MovieRepository>()
    private val useCase = GetMovieDetailUseCase(repository)

    @Test
    fun `invoke returns movie detail on success`() = runTest {
        val detail = MovieDetail(
            id = 1, title = "Test", overview = "Overview",
            posterPath = null, backdropPath = null,
            voteAverage = 8.0, releaseDate = "2024-01-01",
            genres = emptyList(), runtime = 120, tagline = null
        )
        coEvery { repository.getMovieDetail(1) } returns Result.success(detail)

        val result = useCase(1)

        assertTrue(result.isSuccess)
        assertEquals("Test", result.getOrNull()?.title)
    }

    @Test
    fun `invoke returns failure on error`() = runTest {
        coEvery { repository.getMovieDetail(1) } returns
            Result.failure(Exception("Not found"))

        val result = useCase(1)

        assertTrue(result.isFailure)
    }
}
