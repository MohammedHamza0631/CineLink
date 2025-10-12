package com.bms.clone.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("release_date") val releaseDate: String?,
    val overview: String?,
    @SerialName("genre_ids") val genreIds: List<Int>
)
