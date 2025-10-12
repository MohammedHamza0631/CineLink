package com.bms.clone.data.remote.api

import com.bms.clone.data.remote.dto.CreditsResponseDto
import com.bms.clone.data.remote.dto.MovieDetailsDto
import com.bms.clone.data.remote.dto.MovieResponseDto
import com.bms.clone.data.remote.dto.ReviewResponseDto
import com.bms.clone.utils.ApiConstants

interface TMDBApiService {
    suspend fun getPopularMovies(
        region: String = ApiConstants.REGION,
        language: String = ApiConstants.LANGUAGE,
        page: Int = 1
    ): MovieResponseDto

    suspend fun getNowPlaying(
        region: String = ApiConstants.REGION,
        language: String = ApiConstants.LANGUAGE,
        page: Int = 1
    ): MovieResponseDto

    suspend fun getMovieDetails(
        movieId: Int
    ): MovieDetailsDto

    suspend fun getMovieCredits(
        movieId: Int
    ): CreditsResponseDto

    suspend fun getMovieReviews(
        movieId: Int,
        page: Int = 1
    ): ReviewResponseDto
}