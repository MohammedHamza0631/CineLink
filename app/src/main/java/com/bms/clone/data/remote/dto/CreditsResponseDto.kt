package com.bms.clone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponseDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)

@Serializable
data class CastDto(
    val name: String,
    val character: String,
    val profile_path: String?
)

@Serializable
data class CrewDto(
    val name: String,
    val job: String
)
