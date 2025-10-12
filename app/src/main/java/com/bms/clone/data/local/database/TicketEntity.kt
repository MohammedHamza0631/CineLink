package com.bms.clone.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketEntity (
    @PrimaryKey val ticketId: String,
    val movieId: Int,
    val movieTitle: String,
    val moviePoster: String?,
    val cinemaName: String,
    val cinemaLocation: String,
    val showDate: String,
    val showTime: String,
    val selectedSeatIds: String,
    val selectedSeatCategories: String,
    val totalAmount: Double,
    val bookingDate: Long
)
