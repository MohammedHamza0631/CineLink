package com.bms.clone.presentation.states

import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Review

data class MovieDetailState (
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isLoadingReviews: Boolean = false,
    val reviewsError: Throwable? = null,
    val pendingReviews: List<Review> = emptyList()
)
