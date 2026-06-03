package com.test.movieapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)
