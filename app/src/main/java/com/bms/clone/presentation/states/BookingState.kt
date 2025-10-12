package com.bms.clone.presentation.states

import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat

data class BookingState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val selectedSeats: List<Seat> = emptyList(),
    val showTime: String = "",
    val showDate: String = "",
    val totalAmount: Double = 0.0,
    val isBookingConfirmed: Boolean = false,
    val generatedTicketId: String = "",
    val error: Throwable? = null
)