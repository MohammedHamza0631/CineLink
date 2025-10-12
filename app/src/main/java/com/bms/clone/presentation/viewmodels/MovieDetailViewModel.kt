package com.bms.clone.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.usecases.movie.GetMovieDetailsUseCase
import com.bms.clone.domain.usecases.movie.GetMovieReviewsUseCase
import com.bms.clone.presentation.intents.MovieDetailsIntent
import com.bms.clone.presentation.states.MovieDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    fun onIntent(intent: MovieDetailsIntent) {
        when(intent) {
            is MovieDetailsIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId)
            is MovieDetailsIntent.LoadReviews -> loadReviews(intent.movieId)
        }
    }
    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            Log.d("MovieDetailViewModel", "Loading movie details for ID: $movieId")
            _state.update {
                it.copy(isLoading = true, error = null)
            }
            try {
                val movieDetails = getMovieDetailsUseCase(movieId)
                Log.d("MovieDetailViewModel", "Movie details loaded: ${movieDetails.movie.title}, initial reviews: ${movieDetails.reviews.size}")
                _state.update { currentState ->
                    val finalMovieDetails = if (currentState.pendingReviews.isNotEmpty()) {
                        Log.d("MovieDetailViewModel", "Merging ${currentState.pendingReviews.size} pending reviews")
                        movieDetails.copy(reviews = currentState.pendingReviews)
                    } else {
                        movieDetails
                    }
                    currentState.copy(
                        movieDetails = finalMovieDetails,
                        isLoading = false,
                        error = null,
                        pendingReviews = emptyList()
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

    private fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            Log.d("MovieDetailViewModel", "Loading reviews for movie ID: $movieId")
            _state.update { it.copy(isLoadingReviews = true, reviewsError = null) }

            try {
                val reviews = getMovieReviewsUseCase(movieId)
                Log.d("MovieDetailViewModel", "Reviews loaded: ${reviews.size} reviews")
                Log.d("MovieDetailViewModel", "Reviews are: $reviews")
                _state.update { currentState ->
                    if (currentState.movieDetails != null) {
                        val updatedMovieDetails = currentState.movieDetails.copy(reviews = reviews)
                        currentState.copy(
                            movieDetails = updatedMovieDetails,
                            isLoadingReviews = false,
                            reviewsError = null
                        )
                    } else {
                        Log.d("MovieDetailViewModel", "Movie details not loaded yet, storing reviews for later")
                        currentState.copy(
                            isLoadingReviews = false,
                            reviewsError = null,
                            pendingReviews = reviews
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error loading reviews: ${e.message}", e)
                _state.update {
                    it.copy(
                        isLoadingReviews = false,
                        reviewsError = e
                    )
                }
            }
        }
    }

}