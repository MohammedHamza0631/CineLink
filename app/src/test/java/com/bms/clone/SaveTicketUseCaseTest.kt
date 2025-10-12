package com.bms.clone

import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import com.bms.clone.domain.usecases.ticket.SaveTicketUsecase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SaveTicketUseCaseTest {

    private val mockTicketRepository = mockk<TicketRepository>()
    private var saveTicketUseCase = SaveTicketUsecase(mockTicketRepository)

    @Test
    fun `when invoke should save ticket successfully`() = runTest {
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
                                        Seat(
                                                "A1",
                                                "A",
                                                1,
                                                SeatCategory.CLASSIC,
                                                SeatState.SELECTED
                                        ),
                                        Seat("A2", "A", 2, SeatCategory.CLASSIC, SeatState.SELECTED)
                                ),
                        totalAmount = 472.0,
                        bookingDate = 1234567890L
                )

        coEvery { mockTicketRepository.saveTicket(ticket) } returns Unit

        // Act
        saveTicketUseCase(ticket)

        // Assert
        coVerify { mockTicketRepository.saveTicket(ticket) }
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
        val exception = IOException("Database write failed")
        coEvery { mockTicketRepository.saveTicket(ticket) } throws exception

        // Act & Assert
        try {
            saveTicketUseCase(ticket)
            fail("Expected exception to be thrown")
        } catch (e: IOException) {
            assertEquals("Database write failed", e.message)
        }

        coVerify { mockTicketRepository.saveTicket(ticket) }
    }
}
