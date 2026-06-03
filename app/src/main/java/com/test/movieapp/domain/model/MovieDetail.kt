package com.test.movieapp.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: String?,
    val genres: List<Genre>,
    val runtime: Int?,
    val tagline: String?
)
