package com.bms.clone.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val movie: Movie,
    val runtime: Int?,
    val budget: Long,
    val cast: List<Cast>,
    val crew: List<String>,
    val reviews: List<Review> = emptyList(),
    val tagline: String?
)
