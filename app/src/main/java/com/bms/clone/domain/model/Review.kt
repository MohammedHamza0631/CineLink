package com.bms.clone.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String
)
