package com.test.movieapp.data.mapper

import com.test.movieapp.data.remote.dto.GenreDto
import com.test.movieapp.data.remote.dto.MovieDto
import com.test.movieapp.data.remote.dto.MovieDetailDto
import com.test.movieapp.data.remote.dto.ReviewDto
import com.test.movieapp.data.remote.dto.VideoDto
import com.test.movieapp.domain.model.Genre
import com.test.movieapp.domain.model.Movie
import com.test.movieapp.domain.model.MovieDetail
import com.test.movieapp.domain.model.Review
import com.test.movieapp.domain.model.Video

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        genres = genres.map { it.toDomain() },
        runtime = runtime,
        tagline = tagline
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        id = id,
        author = author,
        content = content,
        createdAt = createdAt
    )
}

fun VideoDto.toDomain(): Video {
    return Video(
        id = id,
        key = key,
        name = name,
        site = site,
        type = type
    )
}
