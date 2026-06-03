package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieReviewResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<ReviewDto>,
    @SerialName("total_pages") val totalPages: Int
)
