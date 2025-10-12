package com.bms.clone.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val name: String,
    val character: String,
    val profilePath: String?
)