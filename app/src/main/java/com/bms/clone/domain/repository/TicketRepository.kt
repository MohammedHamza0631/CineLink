package com.bms.clone.domain.repository

import com.bms.clone.domain.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun saveTicket(ticket: Ticket)
    fun getAllTickets(): Flow<List<Ticket>>
    suspend fun getTicketById(ticketId: String): Ticket?
    suspend fun deleteTicket(ticket: Ticket)
}