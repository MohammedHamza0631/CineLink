package com.bms.clone

import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.domain.usecases.movie.GetPopularMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetPopularMoviesUseCaseTest {

    private val mockMovieRepository = mockk<MovieRepository>()
    private var getPopularMoviesUseCase = GetPopularMoviesUseCase(mockMovieRepository)

    @Test
    fun `when invoke should return list of popular movies`() = runTest {
        // Arrange
        val expectedMovies =
                listOf(
                        Movie(
                                id = 1,
                                title = "Popular Movie 1",
                                posterPath = "/poster1.jpg",
                                backdropPath = "/backdrop1.jpg",
                                releaseDate = "2024-01-01",
                                voteAverage = 8.5,
                                voteCount = 1500,
                                overview = "Great movie",
                                genreNames = listOf("Action", "Adventure")
                        ),
                        Movie(
                                id = 2,
                                title = "Popular Movie 2",
                                posterPath = "/poster2.jpg",
                                backdropPath = "/backdrop2.jpg",
                                releaseDate = "2024-02-01",
                                voteAverage = 7.8,
                                voteCount = 1200,
                                overview = "Another great movie",
                                genreNames = listOf("Comedy", "Drama")
                        )
                )

        coEvery { mockMovieRepository.getPopularMovies() } returns expectedMovies

        // Act
        val result = getPopularMoviesUseCase()

        // Assert
        assertEquals(expectedMovies, result)
        assertEquals(2, result.size)
        assertEquals("Popular Movie 1", result[0].title)

        coVerify { mockMovieRepository.getPopularMovies() }
    }

    @Test
    fun `when repository throws exception should give exception`() = runTest {
        // Arrange
        val exception = IOException("Network connection failed")
        coEvery { mockMovieRepository.getPopularMovies() } throws exception

        // Act & Assert
        try {
            getPopularMoviesUseCase()
            fail("Expected exception to be thrown")
        } catch (e: IOException) {
            assertEquals("Network connection failed", e.message)
        }

        coVerify { mockMovieRepository.getPopularMovies() }
    }
}
