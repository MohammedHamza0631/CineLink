package com.bms.clone.data.repository

import com.bms.clone.data.local.database.TicketDao
import com.bms.clone.data.local.database.TicketEntity
import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TicketRepositoryImpl(
    private val ticketDao: TicketDao
): TicketRepository {
    
    override suspend fun saveTicket(ticket: Ticket) {
        val ticketEntity = ticket.toEntity()
        ticketDao.insertTicket(ticketEntity)
    }

    override fun getAllTickets(): Flow<List<Ticket>> {
        return ticketDao.getAllTickets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTicketById(ticketId: String): Ticket? {
        return ticketDao.getTicketById(ticketId)?.toDomain()
    }

    override suspend fun deleteTicket(ticket: Ticket) {
        val ticketEntity = ticket.toEntity()
        ticketDao.deleteTicket(ticketEntity)
    }

    private fun Ticket.toEntity(): TicketEntity {
        return TicketEntity(
            ticketId = this.ticketId,
            movieId = this.movieId,
            movieTitle = this.movieTitle,
            moviePoster = this.moviePoster,
            cinemaName = this.cinemaName,
            cinemaLocation = this.cinemaLocation,
            showDate = this.showDate,
            showTime = this.showTime,
            selectedSeatIds = this.selectedSeats.joinToString(",") { it.seatId },
            selectedSeatCategories = this.selectedSeats.joinToString(",") { it.category.name },
            totalAmount = this.totalAmount,
            bookingDate = this.bookingDate
        )
    }

    private fun TicketEntity.toDomain(): Ticket {
        val seatIds = this.selectedSeatIds.split(",")
        val seatCategories = this.selectedSeatCategories.split(",")
        
        val seats = seatIds.mapIndexed { index, seatId ->
            val categoryName = seatCategories.getOrNull(index) ?: "CLASSIC"
            val category = try {
                SeatCategory.valueOf(categoryName)
            } catch (e: IllegalArgumentException) {
                SeatCategory.CLASSIC
            }
            
            val row = seatId.take(1)
            val number = seatId.drop(1).toIntOrNull() ?: 1
            
            Seat(
                seatId = seatId,
                row = row,
                number = number,
                category = category,
                state = SeatState.SELECTED
            )
        }
        
        return Ticket(
            ticketId = this.ticketId,
            movieId = this.movieId,
            movieTitle = this.movieTitle,
            moviePoster = this.moviePoster,
            cinemaName = this.cinemaName,
            cinemaLocation = this.cinemaLocation,
            showDate = this.showDate,
            showTime = this.showTime,
            selectedSeats = seats,
            totalAmount = this.totalAmount,
            bookingDate = this.bookingDate
        )
    }
}
