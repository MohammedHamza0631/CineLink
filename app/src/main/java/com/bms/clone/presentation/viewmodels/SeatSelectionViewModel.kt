package com.bms.clone.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.presentation.intents.SeatSelectionIntent
import com.bms.clone.presentation.states.SeatSelectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeatSelectionViewModel: ViewModel() {
    private val _state = MutableStateFlow(SeatSelectionState())
    val state: StateFlow<SeatSelectionState> = _state.asStateFlow()

    fun onIntent(intent: SeatSelectionIntent) {
        when (intent) {
            is SeatSelectionIntent.LoadCinemaLayout -> loadCinemaLayout(intent.movieDetails, intent.seatCount)
            is SeatSelectionIntent.SelectSeats -> selectSeats(intent.seatId)
            is SeatSelectionIntent.UnSelectSeats -> unSelectSeats(intent.seatId)
            is SeatSelectionIntent.ConfirmSeats -> confirmSeats()
        }
    }

    private fun loadCinemaLayout(movieDetails: MovieDetails, seatCount: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val mockSeats = generateMockSeats()
                _state.update {
                    it.copy(
                        isLoading = false,
                        seats = mockSeats,
                        maxSeats = seatCount,
                        movieTitle = movieDetails.movie.title,
                        cinemaName = "PVR: Vega City, Bannerghatta Road",
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = e)
                }
            }
        }
    }

    private fun selectSeats(seatId: String) {
        _state.update { currentState ->
            if (currentState.selectedSeats.size >= currentState.maxSeats) {
                return@update currentState.copy(
                    error = Exception("Maximum ${currentState.maxSeats} seats allowed")
                )
            }
            
            val targetSeat = findSeatById(currentState.seats, seatId)
            Log.d("SeatSelectionViewModel", "targetSeat: $targetSeat")
            if (targetSeat == null || (targetSeat.state != SeatState.AVAILABLE && targetSeat.state != SeatState.BESTSELLER)) {
                return@update currentState.copy(error = Exception("Seat not available"))
            }
            
            val updatedSeats = updateSeatState(currentState.seats, seatId, SeatState.SELECTED)
            
            currentState.copy(
                seats = updatedSeats,
                selectedSeats = currentState.selectedSeats + targetSeat.copy(state = SeatState.SELECTED),
                error = null
            )
        }
    }

    private fun unSelectSeats(seatId: String) {
        _state.update { currentState ->
            val updatedSeats = updateSeatState(currentState.seats, seatId, SeatState.AVAILABLE)
            val updatedSelectedSeats = currentState.selectedSeats.filter { it.seatId != seatId }
            
            currentState.copy(
                seats = updatedSeats,
                selectedSeats = updatedSelectedSeats,
                error = null
            )
        }
    }

    private fun confirmSeats() {
        
    }

    private fun generateMockSeats(): List<List<Seat>> {
        val rows = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
        val seatsPerRow = 12
        
        val soldSeats = setOf("A11", "A12", "B5", "B6", "C8", "D10")
        val bestsellerSeats = setOf("D1", "D2", "E1", "E2", "F1", "F2")

        return rows.map { row ->
            (1..seatsPerRow).map { number ->
                val seatId = "$row$number"
                val state = when {
                    soldSeats.contains(seatId) -> SeatState.SOLD
                    bestsellerSeats.contains(seatId) -> SeatState.BESTSELLER
                    else -> SeatState.AVAILABLE
                }
                
                val category = if (row in listOf("G", "H", "I", "J")) {
                    SeatCategory.PRIME
                } else {
                    SeatCategory.CLASSIC
                }

                Seat(
                    seatId = seatId,
                    row = row,
                    number = number,
                    category = category,
                    state = state
                )
            }
        }
    }

    private fun updateSeatState(
        seats: List<List<Seat>>, 
        seatId: String, 
        newState: SeatState
    ): List<List<Seat>> {
        return seats.map { row ->
            row.map { seat ->
                if (seat.seatId == seatId) {
                    seat.copy(state = newState)
                } else {
                    seat
                }
            }
        }
    }

    private fun findSeatById(seats: List<List<Seat>>, seatId: String): Seat? {
        return seats.flatten().find { it.seatId == seatId }
    }
}
