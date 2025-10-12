package com.bms.clone.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    val id: String,
    val author: String,
    val content: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("author_details") val authorDetails: AuthorDetailsDto
)

@Serializable
data class AuthorDetailsDto(
    val name: String?,
    val username: String
)
