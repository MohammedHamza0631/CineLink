package com.bms.clone.domain.usecases.ticket

import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository

class GetTicketByIdUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(ticketId: String): Ticket? {
        return ticketRepository.getTicketById(ticketId)
    }
}