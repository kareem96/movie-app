package com.test.movieapp.domain.repository

import androidx.paging.PagingData
import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.model.Movie
import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.model.Review
import com.test.movieapp.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getGenres(): Result<List<Genre>>
    fun discoverMovies(genreId: String?): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
    suspend fun getMovieVideos(movieId: Int): Result<List<Video>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}
