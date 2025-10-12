package com.bms.clone

import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import com.bms.clone.domain.usecases.ticket.GetTicketByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.sql.SQLException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetTicketByIdUseCaseTest {

    private val mockTicketRepository = mockk<TicketRepository>()
    private var getTicketByIdUseCase = GetTicketByIdUseCase(mockTicketRepository)

    @Test
    fun `when invoke with valid ticketId should return ticket`() = runTest {
        // Arrange
        val ticketId = "BMS123456"
        val expectedTicket =
                Ticket(
                        ticketId = ticketId,
                        movieId = 1,
                        movieTitle = "Test Movie",
                        moviePoster = "/poster.jpg",
                        cinemaName = "PVR Vega City",
                        cinemaLocation = "Bannerghatta Road",
                        showDate = "Today",
                        showTime = "7:00 PM",
                        selectedSeats =
                                listOf(
                                        Seat(
                                                "A1",
                                                "A",
                                                1,
                                                SeatCategory.CLASSIC,
                                                SeatState.SELECTED
                                        ),
                                        Seat("A2", "A", 2, SeatCategory.CLASSIC, SeatState.SELECTED)
                                ),
                        totalAmount = 472.0
                )

        coEvery { mockTicketRepository.getTicketById(ticketId) } returns expectedTicket

        // Act
        val result = getTicketByIdUseCase(ticketId)

        // Assert
        assertEquals(expectedTicket, result)
        assertEquals(ticketId, result?.ticketId)
        assertEquals("Test Movie", result?.movieTitle)
        assertEquals(2, result?.selectedSeats?.size)

        coVerify { mockTicketRepository.getTicketById(ticketId) }
    }

    @Test
    fun `when repository throws exception should give exception`() = runTest {
        // Arrange
        val ticketId = "BMS123456"
        val exception = SQLException("Database query failed")
        coEvery { mockTicketRepository.getTicketById(ticketId) } throws exception

        // Act & Assert
        try {
            getTicketByIdUseCase(ticketId)
            fail("Expected exception to be thrown")
        } catch (e: SQLException) {
            assertEquals("Database query failed", e.message)
        }

        coVerify { mockTicketRepository.getTicketById(ticketId) }
    }
}
