package com.test.movieapp.presentation.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.test.movieapp.domain.usecase.DiscoverMoviesUseCase
import com.test.movieapp.domain.usecase.SearchMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val discoverMoviesUseCase = mockk<DiscoverMoviesUseCase>()
    private val searchMoviesUseCase = mockk<SearchMoviesUseCase>()
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { discoverMoviesUseCase(any()) } returns flowOf(PagingData.empty())
        every { searchMoviesUseCase(any()) } returns flowOf(PagingData.empty())
        viewModel = MoviesViewModel(discoverMoviesUseCase, searchMoviesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial search state is inactive`() {
        assertFalse(viewModel.isSearchActive.value)
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun `setSearchActive true activates search mode`() {
        viewModel.setSearchActive(true)
        assertTrue(viewModel.isSearchActive.value)
    }

    @Test
    fun `setSearchActive false resets query`() {
        viewModel.setSearchQuery("action")
        viewModel.setSearchActive(false)

        assertFalse(viewModel.isSearchActive.value)
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun `setSearchQuery updates searchQuery state`() {
        viewModel.setSearchActive(true)
        viewModel.setSearchQuery("matrix")
        assertEquals("matrix", viewModel.searchQuery.value)
    }

    @Test
    fun `setGenreId updates genreId`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.movies.collect {}
        }

        viewModel.setGenreId("28")

        // verify discover is called with correct genreId (and null initially)
        verify { discoverMoviesUseCase(null) }
        verify { discoverMoviesUseCase("28") }

        collectJob.cancel()
    }
}
