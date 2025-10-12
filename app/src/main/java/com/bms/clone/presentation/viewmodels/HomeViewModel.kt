package com.bms.clone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.usecases.movie.GetPopularMoviesUseCase
import com.bms.clone.presentation.intents.HomeIntent
import com.bms.clone.presentation.states.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.LoadMovies -> loadMovies()
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val movies = getPopularMoviesUseCase()
                _state.update {
                    it.copy(
                        movies = movies,
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