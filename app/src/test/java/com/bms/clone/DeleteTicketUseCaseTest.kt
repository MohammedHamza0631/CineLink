package com.bms.clone

import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import com.bms.clone.domain.usecases.ticket.DeleteTicketUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.sql.SQLException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class DeleteTicketUseCaseTest {

    private val mockTicketRepository = mockk<TicketRepository>()
    private var deleteTicketUseCase = DeleteTicketUseCase(mockTicketRepository)

    @Test
    fun `when invoke should delete ticket successfully`() = runTest {
        // Arrange
        val ticket =
                Ticket(
                        ticketId = "BMS123456",
                        movieId = 1,
                        movieTitle = "Test Movie",
                        moviePoster = "/poster.jpg",
                        cinemaName = "PVR Vega City",
                        cinemaLocation = "Bannerghatta Road",
                        showDate = "Today",
                        showTime = "7:00 PM",
                        selectedSeats =
                                listOf(
                                        Seat("A1", "A", 1, SeatCategory.CLASSIC, SeatState.SELECTED)
                                ),
                        totalAmount = 236.0
                )

        coEvery { mockTicketRepository.deleteTicket(ticket) } returns Unit

        // Act
        deleteTicketUseCase(ticket)

        // Assert
        coVerify { mockTicketRepository.deleteTicket(ticket) }
    }

    @Test
    fun `when repository throws exception should give exception`() = runTest {
        // Arrange
        val ticket =
                Ticket(
                        ticketId = "BMS123456",
                        movieId = 1,
                        movieTitle = "Test Movie",
                        moviePoster = "/poster.jpg",
                        cinemaName = "PVR Vega City",
                        cinemaLocation = "Bannerghatta Road",
                        showDate = "Today",
                        showTime = "7:00 PM",
                        selectedSeats =
                                listOf(
                                        Seat("A1", "A", 1, SeatCategory.CLASSIC, SeatState.SELECTED)
                                ),
                        totalAmount = 236.0
                )
        val exception = SQLException("Database delete failed: Ticket not found")
        coEvery { mockTicketRepository.deleteTicket(ticket) } throws exception

        // Act & Assert
        try {
            deleteTicketUseCase(ticket)
            fail("Expected exception to be thrown")
        } catch (e: SQLException) {
            assertEquals("Database delete failed: Ticket not found", e.message)
        }

        coVerify { mockTicketRepository.deleteTicket(ticket) }
    }
}
