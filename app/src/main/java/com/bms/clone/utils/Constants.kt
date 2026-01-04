package com.bms.clone.utils

import com.bms.clone.BuildConfig

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    val API_KEY = BuildConfig.TMDB_API_KEY
    val ACCESS_TOKEN = BuildConfig.TMDB_ACCESS_TOKEN
    const val REGION = "IN"
    const val LANGUAGE = "en-US"
}

object ApiEndPoints {
    const val POPULAR_MOVIES = "movie/popular"
    const val MOVIE_DETAILS = "movie/{id}"
    const val MOVIE_CREDITS = "movie/{id}/credits"
    const val NOW_PLAYING = "movie/now_playing"
    const val MOVIE_REVIEWS = "movie/{id}/reviews"

}
