package com.bms.clone.presentation.states

import com.bms.clone.domain.model.Movie

data class HomeState (
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
