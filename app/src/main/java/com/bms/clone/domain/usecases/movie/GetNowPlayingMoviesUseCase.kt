package com.bms.clone.domain.usecases.movie

import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.repository.MovieRepository

class GetNowPlayingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getNowPlayingMovies()
    }
}