package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GetGenreResponse(
    @SerialName("genres") val genres: List<GenreDto>
)
