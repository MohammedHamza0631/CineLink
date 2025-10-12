package com.bms.clone.domain.usecases.movie

import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.repository.MovieRepository

class GetMovieDetailsUseCase (
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetails {
        return repository.getMovieDetails(movieId)
    }
}