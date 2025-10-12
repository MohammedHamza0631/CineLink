package com.bms.clone.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("release_date") val releaseDate: String?,
    val overview: String?,

    val runtime: Int?,
    val budget: Long,
    val genres: List<GenreDto>,
    val tagline: String?
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)
