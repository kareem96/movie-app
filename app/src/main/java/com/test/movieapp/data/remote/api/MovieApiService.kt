package com.test.movieapp.data.remote.api

import com.test.movieapp.data.remote.dto.DiscoverMovieResponse
import com.test.movieapp.data.remote.dto.GetGenreResponse
import com.test.movieapp.data.remote.dto.MovieDetailDto
import com.test.movieapp.data.remote.dto.MovieReviewResponse
import com.test.movieapp.data.remote.dto.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("genre/movie/list")
    suspend fun getGenres(): GetGenreResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: String?,
        @Query("page") page: Int
    ): DiscoverMovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): MovieDetailDto

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int
    ): MovieReviewResponse

    @GET("movie/{movieId}/videos")
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int
    ): MovieVideoResponse
}
