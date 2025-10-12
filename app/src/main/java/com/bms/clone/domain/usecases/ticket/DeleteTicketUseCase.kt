package com.bms.clone.domain.usecases.ticket

import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.repository.TicketRepository

class DeleteTicketUseCase (
    private val ticketRepository: TicketRepository
) {
    suspend operator fun invoke(ticket: Ticket) {
        ticketRepository.deleteTicket(ticket)
    }
}