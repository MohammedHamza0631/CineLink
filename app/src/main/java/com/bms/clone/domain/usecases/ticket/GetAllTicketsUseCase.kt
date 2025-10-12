package com.bms.clone.domain.usecases.ticket

import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow

class GetAllTicketsUseCase (
    private val ticketRepository: TicketRepository
) {
    operator fun invoke(): Flow<List<Ticket>> {
        return ticketRepository.getAllTickets()
    }
}