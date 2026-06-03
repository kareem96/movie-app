package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class VideoDto(
    @SerialName("id") val id: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String,
    @SerialName("site") val site: String,
    @SerialName("type") val type: String
)
