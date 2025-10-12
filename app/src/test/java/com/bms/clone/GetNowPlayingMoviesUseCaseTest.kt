package com.bms.clone

import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.domain.usecases.movie.GetNowPlayingMoviesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.SocketTimeoutException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetNowPlayingMoviesUseCaseTest {

    private val mockMovieRepository = mockk<MovieRepository>()
    private var getNowPlayingMoviesUseCase = GetNowPlayingMoviesUseCase(mockMovieRepository)

    @Test
    fun `when invoke should return list of now playing movies`() = runTest {
        // Arrange
        val expectedMovies =
                listOf(
                        Movie(
                                id = 100,
                                title = "Now Playing Movie",
                                posterPath = "/now_playing.jpg",
                                backdropPath = "/backdrop.jpg",
                                releaseDate = "2024-12-01",
                                voteAverage = 9.0,
                                voteCount = 2000,
                                overview = "Currently in theaters",
                                genreNames = listOf("Thriller", "Mystery")
                        )
                )

        coEvery { mockMovieRepository.getNowPlayingMovies() } returns expectedMovies

        // Act
        val result = getNowPlayingMoviesUseCase()

        // Assert
        assertEquals(expectedMovies, result)
        assertEquals(1, result.size)
        assertEquals("Now Playing Movie", result[0].title)

        coVerify { mockMovieRepository.getNowPlayingMovies() }
    }

    @Test
    fun `when repository throws exception should give exception`() = runTest {
        // Arrange
        val exception = SocketTimeoutException("Request timed out")
        coEvery { mockMovieRepository.getNowPlayingMovies() } throws exception

        // Act & Assert
        try {
            getNowPlayingMoviesUseCase()
            fail("Expected exception to be thrown")
        } catch (e: SocketTimeoutException) {
            assertEquals("Request timed out", e.message)
        }

        coVerify { mockMovieRepository.getNowPlayingMovies() }
    }
}
