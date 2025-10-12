package com.bms.clone.presentation.intents

import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat

sealed class BookingIntent {
    data class InitializeBooking(
        val movieDetails: MovieDetails,
        val selectedSeats: List<Seat>,
        val showTime: String,
        val showDate: String
    ) : BookingIntent()
    
    object ConfirmBooking : BookingIntent()
    object CancelBooking : BookingIntent()
}