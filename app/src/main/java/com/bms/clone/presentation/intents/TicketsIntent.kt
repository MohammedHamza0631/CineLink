package com.bms.clone.presentation.intents

import com.bms.clone.domain.model.Ticket

sealed class TicketsIntent {
    object LoadTickets : TicketsIntent()
    data class DeleteTicket(val ticket: Ticket) : TicketsIntent()
}
