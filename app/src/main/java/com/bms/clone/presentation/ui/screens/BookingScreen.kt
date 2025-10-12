package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.presentation.intents.BookingIntent
import com.bms.clone.presentation.viewmodels.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    selectedSeats: List<Seat>,
    movieTitle: String,
    moviePoster: String? = null,
    onBackPressed: () -> Unit,
    onBookingConfirmed: () -> Unit,
    viewModel: BookingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(selectedSeats, movieTitle, moviePoster) {
        val movieDetails = MovieDetails(
            movie = Movie(
                id = 1,
                title = movieTitle,
                overview = "",
                releaseDate = "",
                voteAverage = 0.0,
                voteCount = 0,
                genreNames = emptyList(),
                backdropPath = null,
                posterPath = moviePoster
            ),
            runtime = null,
            budget = 0L,
            cast = emptyList(),
            crew = emptyList(),
            reviews = emptyList(),
            tagline = null
        )
        
        viewModel.onIntent(
            BookingIntent.InitializeBooking(
                movieDetails = movieDetails,
                selectedSeats = selectedSeats,
                showTime = "7:00 PM",
                showDate = "Today"
            )
        )
    }
    
    LaunchedEffect(state.isBookingConfirmed) {
        if (state.isBookingConfirmed) {
            onBookingConfirmed()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Booking Confirmation",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Booking Confirmed!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Your ticket has been successfully booked!",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Text(
                    text = "Movie: $movieTitle",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Selected Seats: ${selectedSeats.joinToString(", ") { it.seatId }}",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Total Amount: ₹${calculateTotalAmount(selectedSeats)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.onIntent(BookingIntent.ConfirmBooking)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Create Ticket",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

private fun calculateTotalAmount(seats: List<Seat>): Int {
    return seats.sumOf { seat ->
        when (seat.category) {
            SeatCategory.CLASSIC -> 200
            SeatCategory.PRIME -> 250
        }.toLong()
    }.toInt()
}
