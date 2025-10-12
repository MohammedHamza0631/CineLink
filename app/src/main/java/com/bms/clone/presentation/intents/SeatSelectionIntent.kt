package com.bms.clone.presentation.intents

import com.bms.clone.domain.model.MovieDetails

sealed class SeatSelectionIntent {
    data class LoadCinemaLayout(val movieDetails: MovieDetails, val seatCount: Int) : SeatSelectionIntent()
    data class SelectSeats(val seatId: String): SeatSelectionIntent()
    data class UnSelectSeats(val seatId: String): SeatSelectionIntent()
    object ConfirmSeats : SeatSelectionIntent()
}
