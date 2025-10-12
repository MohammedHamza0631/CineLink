package com.bms.clone.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Seat(
    val seatId: String,
    val row: String,
    val number: Int,
    val category: SeatCategory,
    val state: SeatState
)

@Serializable
enum class SeatState {
    AVAILABLE,
    SELECTED,
    SOLD,
    BESTSELLER
}

@Serializable
enum class SeatCategory(val price: Int) {
    PRIME(350),
    CLASSIC(236)
}

data class CinemaLayout(
    val totalRows: List<String> = listOf("A", "B", "C", "D", "E", "F", "G", "H", "J", "K"),
    val seatsPerRow: Int = 12,
    val seats: List<List<Seat>> = emptyList(),
    val cinemaName: String = "PVR: Vega City, Bannerghatta Road",
    val movieTitle: String = ""
)
