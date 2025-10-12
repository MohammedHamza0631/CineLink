package com.bms.clone.data.mapper

import com.bms.clone.data.remote.dto.CreditsResponseDto
import com.bms.clone.data.remote.dto.MovieDetailsDto
import com.bms.clone.data.remote.dto.MovieDto
import com.bms.clone.data.remote.dto.ReviewDto
import com.bms.clone.domain.model.Cast
import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Review
fun mapCreditsToCastObjects(credits: CreditsResponseDto): Pair<List<Cast>, List<String>> {
    val castObjects = credits.cast.take(10).map { 
        Cast(
            name = it.name,
            character = it.character,
            profilePath = it.profile_path
        )
    }
    val crewNames = credits.crew
        .filter {
            it.job in listOf("Director", "Producer", "Writer")
        }
        .map {
            it.name
        }
    return Pair(castObjects, crewNames)
}

fun mapGenreIds(genreIds: List<Int>): List<String> {
    val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )
    return genreIds.mapNotNull { genreMap[it] }
}

fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        overview = this.overview ?: "",
        genreNames = mapGenreIds(this.genreIds)
    )
}

fun MovieDetailsDto.toDomainModel(): MovieDetails {
    val movie = Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath,
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        overview = this.overview ?: "",
        genreNames = this.genres.map { it.name }
    )

    return MovieDetails(
        movie = movie,
        runtime = this.runtime,
        budget = this.budget,
        cast = emptyList(),
        crew = emptyList(),
        reviews = emptyList(),
        tagline = this.tagline
    )
}

fun ReviewDto.toDomainModel(): Review {
    return Review(
        id = this.id,
        author = this.author,
        content = this.content,
        createdAt = this.createdAt
    )
}
