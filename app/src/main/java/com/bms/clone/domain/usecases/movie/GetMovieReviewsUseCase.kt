package com.bms.clone.domain.usecases.movie

import com.bms.clone.domain.model.Review
import com.bms.clone.domain.repository.MovieRepository

class GetMovieReviewsUseCase (
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int = 1): List<Review> {
        return repository.getMovieReviews(movieId, page)
    }
}