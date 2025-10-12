package com.bms.clone.presentation.states

import com.bms.clone.domain.model.Movie

data class MoviesState (
    val nowPlayingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
