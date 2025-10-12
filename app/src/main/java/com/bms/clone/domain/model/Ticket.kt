package com.bms.clone.domain.model

data class Ticket(
    val ticketId: String,
    val movieId: Int,
    val movieTitle: String,
    val moviePoster: String?,
    val cinemaName: String,
    val cinemaLocation: String,
    val showDate: String,
    val showTime: String,
    val selectedSeats: List<Seat>,
    val totalAmount: Double,
    val bookingDate: Long = System.currentTimeMillis()
)
