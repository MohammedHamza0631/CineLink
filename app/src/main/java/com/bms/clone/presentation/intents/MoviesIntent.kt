package com.bms.clone.presentation.intents

sealed class MoviesIntent {
    object LoadNowPlayingMovies: MoviesIntent()
}
