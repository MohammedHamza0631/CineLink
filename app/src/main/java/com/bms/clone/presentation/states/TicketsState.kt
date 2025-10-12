package com.bms.clone.presentation.states

import com.bms.clone.domain.model.Ticket

data class TicketsState(
    val isLoading: Boolean = false,
    val tickets: List<Ticket> = emptyList(),
    val error: Throwable? = null
)
