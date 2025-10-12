package com.bms.clone

import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import com.bms.clone.domain.usecases.ticket.GetAllTicketsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.sql.SQLException
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetAllTicketsUseCaseTest {

    private val mockTicketRepository = mockk<TicketRepository>()
    private var getAllTicketsUseCase = GetAllTicketsUseCase(mockTicketRepository)

    @Test
    fun `when invoke should return flow of all tickets`() = runTest {
        // Arrange
        val expectedTickets =
                listOf(
                        Ticket(
                                ticketId = "BMS123456",
                                movieId = 1,
                                movieTitle = "Test Movie 1",
                                moviePoster = "/poster1.jpg",
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
                                                )
                                        ),
                                totalAmount = 236.0
                        ),
                        Ticket(
                                ticketId = "BMS789012",
                                movieId = 2,
                                movieTitle = "Test Movie 2",
                                moviePoster = "/poster2.jpg",
                                cinemaName = "INOX Forum Mall",
                                cinemaLocation = "Koramangala",
                                showDate = "Tomorrow",
                                showTime = "9:30 PM",
                                selectedSeats =
                                        listOf(
                                                Seat(
                                                        "B5",
                                                        "B",
                                                        5,
                                                        SeatCategory.PRIME,
                                                        SeatState.SELECTED
                                                )
                                        ),
                                totalAmount = 350.0
                        )
                )

        every { mockTicketRepository.getAllTickets() } returns flowOf(expectedTickets)

        // Act
        val result = getAllTicketsUseCase().toList()

        // Assert
        assertEquals(1, result.size)
        assertEquals(expectedTickets, result[0])
        assertEquals(2, result[0].size)
        assertEquals("BMS123456", result[0][0].ticketId)
        assertEquals("Test Movie 1", result[0][0].movieTitle)

        verify { mockTicketRepository.getAllTickets() }
    }

    @Test
    fun `when repository throws exception should give exception`() = runTest {
        // Arrange
        val exception = SQLException("Database read failed")
        every { mockTicketRepository.getAllTickets() } throws exception

        // Act & Assert
        try {
            getAllTicketsUseCase()
            fail("Expected exception to be thrown")
        } catch (e: SQLException) {
            assertEquals("Database read failed", e.message)
        }

        verify { mockTicketRepository.getAllTickets() }
    }
}
