package com.bms.clone.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewDto>
)
