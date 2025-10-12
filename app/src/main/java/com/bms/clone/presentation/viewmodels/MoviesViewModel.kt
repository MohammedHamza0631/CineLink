package com.bms.clone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.usecases.movie.GetNowPlayingMoviesUseCase
import com.bms.clone.presentation.intents.MoviesIntent
import com.bms.clone.presentation.states.MoviesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel (
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    fun onIntent(intent: MoviesIntent) {
        when(intent) {
            is MoviesIntent.LoadNowPlayingMovies -> loadNowPlayingMovies()
        }
    }

    private fun loadNowPlayingMovies() {
        viewModelScope.launch {
            _state.update { 
                it.copy(
                    isLoading = true,
                    error = null
                ) 
            }

            try {
                val nowPlayingMovies = getNowPlayingMoviesUseCase()
                _state.update {
                    it.copy(
                        nowPlayingMovies = nowPlayingMovies,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = e
                    ) 
                }
            }
        }
    }
}