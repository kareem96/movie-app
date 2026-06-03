package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ReviewDto(
    @SerialName("id") val id: String,
    @SerialName("author") val author: String,
    @SerialName("content") val content: String,
    @SerialName("created_at") val createdAt: String
)
