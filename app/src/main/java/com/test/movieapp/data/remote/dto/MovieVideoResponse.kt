package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieVideoResponse(
    @SerialName("results") val results: List<VideoDto>
)
