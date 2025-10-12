package com.bms.clone.presentation.intents

sealed class HomeIntent {
    data object LoadMovies: HomeIntent()
}
