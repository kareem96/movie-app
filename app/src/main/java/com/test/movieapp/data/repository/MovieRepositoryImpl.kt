package com.test.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.movieapp.data.mapper.toDomain
import com.test.movieapp.data.remote.api.MovieApiService
import com.test.movieapp.data.remote.api.MoviePagingSource
import com.test.movieapp.data.remote.api.ReviewPagingSource
import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.model.Movie
import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.model.Review
import com.test.movieapp.domain.model.Video
import com.test.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            val response = apiService.getGenres()
            Result.success(response.genres.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun discoverMovies(genreId: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(apiService, genreId) }
        ).flow
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val response = apiService.getMovieDetails(movieId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ReviewPagingSource(apiService, movieId) }
        ).flow
    }

    override suspend fun getMovieVideos(movieId: Int): Result<List<Video>> {
        return try {
            val response = apiService.getMovieVideos(movieId)
            Result.success(response.results.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
