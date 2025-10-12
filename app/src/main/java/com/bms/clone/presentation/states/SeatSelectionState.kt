package com.bms.clone.presentation.states

import com.bms.clone.domain.model.Seat

data class SeatSelectionState(
    val isLoading: Boolean = false,
    val seats: List<List<Seat>> = emptyList(),
    val selectedSeats: List<Seat> = emptyList(),
    val maxSeats: Int = 0,
    val movieTitle: String = "",
    val cinemaName: String = "",
    val showTime: String = "01:30 PM",
    val showDate: String = "Fri, 19 Sep",
    val error: Throwable? = null
)