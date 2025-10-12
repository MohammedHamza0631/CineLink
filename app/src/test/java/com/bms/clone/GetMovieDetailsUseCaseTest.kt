package com.bms.clone

import com.bms.clone.domain.model.*
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.domain.usecases.movie.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetMovieDetailsUseCaseTest {

        private val mockMovieRepository = mockk<MovieRepository>()
        private var getMovieDetailsUseCase = GetMovieDetailsUseCase(mockMovieRepository)

        @Test
        fun `when invoke with valid movieId, should return movie details`() = runTest {
                // Arrange
                val movieId = 123
                val expectedMovieDetails =
                        MovieDetails(
                                movie =
                                        Movie(
                                                id = movieId,
                                                title = "Test Movie",
                                                posterPath = "/poster.jpg",
                                                backdropPath = "/backdrop.jpg",
                                                releaseDate = "2024-01-01",
                                                voteAverage = 8.5,
                                                voteCount = 1500,
                                                overview = "A great test movie",
                                                genreNames = listOf("Action", "Adventure")
                                        ),
                                runtime = 120,
                                budget = 50000000L,
                                cast =
                                        listOf(
                                                Cast(
                                                        name = "John Doe",
                                                        character = "Hero",
                                                        profilePath = "/actor.jpg"
                                                )
                                        ),
                                crew = listOf("Director: Jane Smith"),
                                reviews =
                                        listOf(
                                                Review(
                                                        id = "1",
                                                        author = "Reviewer",
                                                        content = "Great movie!",
                                                        createdAt = "2024-01-01"
                                                )
                                        ),
                                tagline = "The ultimate test"
                        )

                coEvery { mockMovieRepository.getMovieDetails(movieId) } returns expectedMovieDetails

                // Act
                val result = getMovieDetailsUseCase(movieId)

                // Assert
                assertEquals(expectedMovieDetails, result)
                assertEquals(movieId, result.movie.id)
                assertEquals("Test Movie", result.movie.title)
                assertEquals(120, result.runtime)
                assertEquals(1, result.cast.size)
                assertEquals("John Doe", result.cast[0].name)

                coVerify { mockMovieRepository.getMovieDetails(movieId) }
        }

        @Test
        fun `when repository throws exception, should give exception`() = runTest {
                // Arrange
                val movieId = 999
                val exception = Exception("Network error")
                coEvery { mockMovieRepository.getMovieDetails(movieId) } throws exception

                // Act & Assert
                try {
                        getMovieDetailsUseCase(movieId)
                        fail("Expected exception to be thrown")
                } catch (e: Exception) {
                        assertEquals("Network error", e.message)
                }

                coVerify { mockMovieRepository.getMovieDetails(movieId) }
        }
}
