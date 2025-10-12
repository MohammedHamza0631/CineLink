package com.bms.clone.utils

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "f6b81d6329c5206d0b3ab537557542c1"
    const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNmI4MWQ2MzI5YzUyMDZkMGIzYWI1Mzc1NTc1NDJjMSIsIm5iZiI6MTc1NzMyNjE4OC45NTcsInN1YiI6IjY4YmVhYjZjMzhhZTRiZmFiNTFmNTYwNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.CceME0qm69aBz3HjJ0NI_UmLSFHBw7Bz3MQ2NkPS7fQ"
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
