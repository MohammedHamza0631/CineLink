package com.bms.clone.utils

data class ImageUtils(
    val imageBaseUrl: String = "https://image.tmdb.org/t/p/",
    val posterSizes: List<String> = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
    val backdropSizes: List<String> = listOf("w300", "w780", "w1280", "original")
) {
    fun buildPosterUrl(filePath: String?, size: String = "w500"): String? {
        return filePath?.let { "${imageBaseUrl}$size$it" }
    }
    fun buildProfileUrl(filePath: String?, size: String = "w185"): String? {
        return filePath?.let { "${imageBaseUrl}$size$it" }
    }
    fun buildBackdropUrl(filePath: String?, size: String = "w780"): String? {
        return filePath?.let { "${imageBaseUrl}$size$it" }
    }
}