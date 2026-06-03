package com.test.movieapp.domain.usecase

import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetGenresUseCaseTest {

    private val repository = mockk<MovieRepository>()
    private val useCase = GetGenresUseCase(repository)

    @Test
    fun `invoke returns success when repository succeeds`() = runTest {
        val genres = listOf(Genre(1, "Action"), Genre(2, "Comedy"))
        coEvery { repository.getGenres() } returns Result.success(genres)

        val result = useCase()

        assertTrue(result.isSuccess)
        assertEquals(genres, result.getOrNull())
    }

    @Test
    fun `invoke returns failure when repository throws`() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.getGenres() } returns Result.failure(exception)

        val result = useCase()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}
