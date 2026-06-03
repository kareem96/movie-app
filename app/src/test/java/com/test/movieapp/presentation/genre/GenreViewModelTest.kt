package com.test.movieapp.presentation.genre

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.usecase.GetGenresUseCase
import com.test.movieapp.data.remote.NetworkMonitor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GenreViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val getGenresUseCase = mockk<GetGenresUseCase>()
    private val networkMonitor = mockk<NetworkMonitor>()
    private lateinit var viewModel: GenreViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { networkMonitor.isOnline } returns flowOf(true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        coEvery { getGenresUseCase() } coAnswers { delay(100); Result.success(emptyList()) }
        viewModel = GenreViewModel(getGenresUseCase, networkMonitor)
        assertTrue(viewModel.uiState.value is GenreUiState.Loading)
    }

    @Test
    fun `loadGenres emits Success when use case succeeds`() = runTest {
        val genres = listOf(Genre(1, "Action"), Genre(2, "Drama"))
        coEvery { getGenresUseCase() } returns Result.success(genres)

        viewModel = GenreViewModel(getGenresUseCase, networkMonitor)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is GenreUiState.Success)
            assertEquals(genres, (state as GenreUiState.Success).genres)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadGenres emits Error when use case fails`() = runTest {
        coEvery { getGenresUseCase() } returns Result.failure(Exception("Failed"))

        viewModel = GenreViewModel(getGenresUseCase, networkMonitor)

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is GenreUiState.Error)
            assertEquals("Failed", (state as GenreUiState.Error).message)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
