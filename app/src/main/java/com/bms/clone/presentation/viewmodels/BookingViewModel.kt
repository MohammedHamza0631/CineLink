package com.bms.clone.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.Ticket
import com.bms.clone.domain.usecases.ticket.SaveTicketUsecase
import com.bms.clone.presentation.intents.BookingIntent
import com.bms.clone.presentation.states.BookingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class BookingViewModel(
    private val saveTicketUseCase: SaveTicketUsecase
) : ViewModel() {
    private val _state = MutableStateFlow(BookingState())
    val state: StateFlow<BookingState> = _state.asStateFlow()

    fun onIntent(intent: BookingIntent) {
        when (intent) {
            is BookingIntent.InitializeBooking -> initializeBooking(
                movieDetails = intent.movieDetails,
                selectedSeats = intent.selectedSeats,
                showTime = intent.showTime,
                showDate = intent.showDate
            )
            is BookingIntent.ConfirmBooking -> confirmBooking()
            is BookingIntent.CancelBooking -> cancelBooking()
        }
    }

    private fun initializeBooking(
        movieDetails: MovieDetails,
        selectedSeats: List<Seat>,
        showTime: String,
        showDate: String
    ) {
        val totalAmount = calculateTotalAmount(selectedSeats)
        
        _state.update {
            it.copy(
                movieDetails = movieDetails,
                selectedSeats = selectedSeats,
                showTime = showTime,
                showDate = showDate,
                totalAmount = totalAmount,
                error = null
            )
        }
    }

    private fun confirmBooking() {
        val currentState = _state.value
        if (currentState.movieDetails == null || currentState.selectedSeats.isEmpty()) {
            _state.update {
                it.copy(error = Exception("Booking data is incomplete"))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                val ticketId = generateTicketId()
                val ticket = Ticket(
                    ticketId = ticketId,
                    movieId = currentState.movieDetails.movie.id,
                    movieTitle = currentState.movieDetails.movie.title,
                    moviePoster = currentState.movieDetails.movie.posterPath,
                    cinemaName = "PVR: Vega City, Bannerghatta Road",
                    cinemaLocation = "Bengaluru, Karnataka",
                    showDate = currentState.showDate,
                    showTime = currentState.showTime,
                    selectedSeats = currentState.selectedSeats,
                    totalAmount = currentState.totalAmount,
                    bookingDate = System.currentTimeMillis()
                )
                
                saveTicketUseCase(ticket)
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        isBookingConfirmed = true,
                        generatedTicketId = ticketId,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e
                    )
                }
            }
        }
    }

    private fun cancelBooking() {
        _state.update {
            BookingState()
        }
    }

    private fun calculateTotalAmount(selectedSeats: List<Seat>): Double {
        return selectedSeats.sumOf { seat ->
            seat.category.price.toDouble()
        }
    }

    private fun generateTicketId(): String {
        return "BMS${System.currentTimeMillis()}"
    }
}