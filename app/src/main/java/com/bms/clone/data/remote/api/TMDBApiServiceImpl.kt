package com.bms.clone.data.remote.api

import com.bms.clone.data.remote.dto.CreditsResponseDto
import com.bms.clone.data.remote.dto.MovieDetailsDto
import com.bms.clone.data.remote.dto.MovieResponseDto
import com.bms.clone.data.remote.dto.ReviewResponseDto
import com.bms.clone.utils.ApiEndPoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class TMDBApiServiceImpl(
    private val client: HttpClient
): TMDBApiService {
    override suspend fun getPopularMovies(
        region: String,
        language: String,
        page: Int
    ): MovieResponseDto {
        return client.get(ApiEndPoints.POPULAR_MOVIES) {
            parameter("language", language)
            parameter("region", region)
            parameter("page", page)
        }.body<MovieResponseDto>()
    }

    override suspend fun getNowPlaying(
        region: String,
        language: String,
        page: Int
    ): MovieResponseDto {
        return client.get(ApiEndPoints.NOW_PLAYING) {
            parameter("language", language)
            parameter("region", region)
            parameter("page", page)
        }.body<MovieResponseDto>()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return client.get("movie/$movieId").body<MovieDetailsDto>()
    }

    override suspend fun getMovieCredits(movieId: Int): CreditsResponseDto {
        return client.get("movie/$movieId/credits").body<CreditsResponseDto>()
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): ReviewResponseDto {
        return client.get("movie/$movieId/reviews") {
            parameter("page", page)
        }.body<ReviewResponseDto>()
    }
}