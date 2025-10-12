package com.bms.clone

import com.bms.clone.domain.model.Review
import com.bms.clone.domain.repository.MovieRepository
import com.bms.clone.domain.usecases.movie.GetMovieReviewsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Test

class GetMovieReviewsUseCaseTest {
    private val mockMovieRepository = mockk<MovieRepository>()

    private var getMovieReviewsUseCase = GetMovieReviewsUseCase(mockMovieRepository)

    @Test
    fun `when invoke is called then return list of movie reviews`() = runTest {
        // arrange
        val movieId = 123
        val page = 1
        val expectedReviews = listOf(
            Review(
                id = "1",
                author = "John Doe",
                content = "This is a good movie",
                createdAt = "2021-01-01T00:00:00.000Z"
            ),
            Review(
                id = "2",
                author = "Bill Park",
                content = "This is a good movie",
                createdAt = "2021-01-01T00:00:00.000Z"
            )
        )

        coEvery { mockMovieRepository.getMovieReviews(movieId, page) } returns expectedReviews

        // act
        val reviews = getMovieReviewsUseCase(movieId, page)


        // assert
        assertEquals(expectedReviews, reviews)
        assertEquals(expectedReviews.size, reviews.size)
        assertEquals(expectedReviews[0].id, reviews[0].id)
        assertEquals(expectedReviews[0].content, reviews[0].content)
    }

    @Test
    fun `when reviews load fails then give exception`() = runTest {
        val exception = IOException("Failed to load reviews")
        coEvery { mockMovieRepository.getMovieReviews(123, 1) } throws exception

        try {
            getMovieReviewsUseCase(123, 1)
            fail("Expected an exception to be thrown")
        } catch (e: IOException) {
            assertEquals("Failed to load reviews", e.message)
        }
    }
}