package com.bms.clone.data.repository

import com.bms.clone.data.mapper.mapCreditsToCastObjects
import com.bms.clone.data.mapper.toDomainModel
import com.bms.clone.data.remote.api.TMDBApiService
import com.bms.clone.domain.model.Cast
import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Review
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.utils.ApiConstants

class MovieRepositoryImpl(
    private val api: TMDBApiService
): MovieRepository {
    
    override suspend fun getPopularMovies(): List<Movie> {
        return api.getPopularMovies(
            ApiConstants.REGION,
            ApiConstants.LANGUAGE,
            1
        ).results.map { it.toDomainModel() }
    }
    
    override suspend fun getNowPlayingMovies(): List<Movie> {
        return api.getNowPlaying(
            ApiConstants.REGION,
            ApiConstants.LANGUAGE,
            1
        ).results.map { it.toDomainModel() }
    }
    
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val movieDetails = api.getMovieDetails(movieId).toDomainModel()

        val (cast, crew) = try {
            mapCreditsToCastObjects(api.getMovieCredits(movieId))
        } catch (e: Exception) {
            emptyList<Cast>() to emptyList<String>()
        }

        return movieDetails.copy(cast = cast, crew = crew)
    }
    
    override suspend fun getMovieReviews(movieId: Int, page: Int): List<Review> {
        return api.getMovieReviews(
            movieId,
            page = page
        ).results.map { it.toDomainModel() }
    }
}
