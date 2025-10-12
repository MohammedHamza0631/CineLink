package com.bms.clone.domain.repository

import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Review

interface MovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieReviews(movieId: Int, page: Int = 1): List<Review>
}