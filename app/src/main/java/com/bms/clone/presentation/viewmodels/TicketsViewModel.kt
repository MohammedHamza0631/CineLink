package com.bms.clone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.usecases.ticket.DeleteTicketUseCase
import com.bms.clone.domain.usecases.ticket.GetAllTicketsUseCase
import com.bms.clone.presentation.intents.TicketsIntent
import com.bms.clone.presentation.states.TicketsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TicketsViewModel(
    private val getAllTicketsUseCase: GetAllTicketsUseCase,
    private val deleteTicketUseCase: DeleteTicketUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TicketsState())
    val state: StateFlow<TicketsState> = _state.asStateFlow()

    init {
        loadTickets()
    }

    fun onIntent(intent: TicketsIntent) {
        when (intent) {
            is TicketsIntent.LoadTickets -> loadTickets()
            is TicketsIntent.DeleteTicket -> deleteTicket(intent.ticket)
        }
    }

    private fun loadTickets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            getAllTicketsUseCase()
                .catch { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception
                        )
                    }
                }
                .collect { tickets ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            tickets = tickets,
                            error = null
                        )
                    }
                }
        }
    }

    private fun deleteTicket(ticket: Ticket) {
        viewModelScope.launch {
            try {
                deleteTicketUseCase(ticket)
                _state.update {
                    it.copy(
                        tickets = it.tickets.filter { existingTicket -> 
                            existingTicket.ticketId != ticket.ticketId 
                        },
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = e)
                }
            }
        }
    }

}
