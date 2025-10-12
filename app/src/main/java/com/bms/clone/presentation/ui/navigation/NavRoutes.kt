package com.bms.clone.presentation.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Movies : Screen("movies")
    object Profile : Screen("profile")
    object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }

    object SeatSelection :
            Screen("seat_selection/{movieId}/{seatCount}/{movieTitle}/{moviePoster}") {
        fun createRoute(movieId: Int, seatCount: Int, movieTitle: String, moviePoster: String) =
                "seat_selection/$movieId/$seatCount/$movieTitle/$moviePoster"
    }

    object Booking : Screen("booking/{movieTitle}/{selectedSeats}/{moviePoster}") {
        fun createRoute(movieTitle: String, selectedSeats: String, moviePoster: String) =
                "booking/$movieTitle/$selectedSeats/$moviePoster"
    }

    object Tickets : Screen("tickets")

    object TicketDetail : Screen("ticket_detail/{ticketId}") {
        fun createRoute(ticketId: String) = "ticket_detail/$ticketId"
    }
}
