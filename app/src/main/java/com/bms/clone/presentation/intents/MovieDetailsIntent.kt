package com.bms.clone.presentation.intents

sealed class MovieDetailsIntent {
    data class LoadMovieDetails(val movieId: Int): MovieDetailsIntent()
    data class LoadReviews(val movieId: Int): MovieDetailsIntent()
}
