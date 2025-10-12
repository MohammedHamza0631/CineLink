package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat
import com.bms.clone.domain.model.SeatCategory
import com.bms.clone.domain.model.SeatState
import com.bms.clone.presentation.intents.SeatSelectionIntent
import com.bms.clone.presentation.viewmodels.SeatSelectionViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatSelectionScreen(
        movieDetails: MovieDetails,
        seatCount: Int,
        onBackPressed: () -> Unit,
        onProceedToBooking: (List<Seat>) -> Unit,
        viewModel: SeatSelectionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieDetails, seatCount) {
        viewModel.onIntent(SeatSelectionIntent.LoadCinemaLayout(movieDetails, seatCount))
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        SeatSelectionHeader(
                movieTitle = state.movieTitle,
                cinemaName = state.cinemaName,
                onBackPressed = onBackPressed
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            ScreenIndicator()

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                val horizontalScrollState = rememberScrollState()
                val verticalScrollState = rememberScrollState()

                Column(
                        modifier =
                                Modifier.verticalScroll(verticalScrollState)
                                        .horizontalScroll(horizontalScrollState)
                                        .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SeatGrid(
                            seats = state.seats,
                            onSeatClick = { seat ->
                                if (seat.state == SeatState.SELECTED) {
                                    viewModel.onIntent(
                                            SeatSelectionIntent.UnSelectSeats(seat.seatId)
                                    )
                                } else {
                                    viewModel.onIntent(SeatSelectionIntent.SelectSeats(seat.seatId))
                                }
                            }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    SeatLegend()
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        if (state.selectedSeats.isNotEmpty() && state.selectedSeats.size == state.maxSeats) {
            BookingSummaryDialog(
                    selectedSeats = state.selectedSeats,
                    onProceed = { onProceedToBooking(state.selectedSeats) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeatSelectionHeader(movieTitle: String, cinemaName: String, onBackPressed: () -> Unit) {
    TopAppBar(
            title = {
                Column {
                    Text(text = movieTitle, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(
                            text = cinemaName,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                    )
                }
            },
            colors =
                    TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                    )
    )
}

@Composable
fun ScreenIndicator() {
    Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
                modifier =
                        Modifier.width(200.dp)
                                .height(8.dp)
                                .background(
                                        color =
                                                MaterialTheme.colorScheme.primary.copy(
                                                        alpha = 0.3f
                                                ),
                                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                text = "SCREEN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun SeatGrid(seats: List<List<Seat>>, onSeatClick: (Seat) -> Unit) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
    ) { seats.forEach { seatRow -> SeatRow(seats = seatRow, onSeatClick = onSeatClick) } }
}

@Composable
fun SeatRow(seats: List<Seat>, onSeatClick: (Seat) -> Unit) {
    Row(
            modifier = Modifier.wrapContentWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = seats.firstOrNull()?.row ?: "",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(24.dp).padding(end = 8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        seats.forEach { seat -> SeatItem(seat = seat, onClick = { onSeatClick(seat) }) }

        Text(
                text = seats.firstOrNull()?.row ?: "",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(24.dp).padding(start = 8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SeatItem(seat: Seat, onClick: () -> Unit) {
    val seatColor =
            when (seat.state) {
                SeatState.AVAILABLE ->
                        when (seat.category) {
                            SeatCategory.CLASSIC -> MaterialTheme.colorScheme.surface
                            SeatCategory.PRIME ->
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        }
                SeatState.SELECTED -> MaterialTheme.colorScheme.primary
                SeatState.SOLD -> MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                SeatState.BESTSELLER -> MaterialTheme.colorScheme.tertiary
            }

    val borderColor =
            when (seat.state) {
                SeatState.AVAILABLE -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                SeatState.SELECTED -> MaterialTheme.colorScheme.primary
                SeatState.SOLD -> MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                SeatState.BESTSELLER -> MaterialTheme.colorScheme.tertiary
            }

    val textColor =
            when (seat.state) {
                SeatState.SELECTED -> MaterialTheme.colorScheme.onPrimary
                SeatState.SOLD -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                else -> MaterialTheme.colorScheme.onSurface
            }

    val isClickable =
            seat.state == SeatState.AVAILABLE ||
                    seat.state == SeatState.SELECTED ||
                    seat.state == SeatState.BESTSELLER

    Box(
            modifier =
                    Modifier.size(32.dp)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(seatColor)
                            .border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(4.dp)
                            )
                            .then(
                                    if (isClickable) {
                                        Modifier.clickable { onClick() }
                                    } else {
                                        Modifier
                                    }
                            ),
            contentAlignment = Alignment.Center
    ) {
        Text(
                text = seat.number.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = textColor
        )
    }
}

@Composable
fun SeatLegend() {
    Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = "Seat Categories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            SeatLegendItem(
                    color = MaterialTheme.colorScheme.surface,
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    label = "Classic ₹200",
                    textColor = MaterialTheme.colorScheme.onSurface
            )

            SeatLegendItem(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    label = "Prime ₹250",
                    textColor = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            SeatLegendItem(
                    color = MaterialTheme.colorScheme.tertiary,
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    label = "Bestseller",
                    textColor = MaterialTheme.colorScheme.onSurface
            )

            SeatLegendItem(
                    color = MaterialTheme.colorScheme.primary,
                    borderColor = MaterialTheme.colorScheme.primary,
                    label = "Selected",
                    textColor = MaterialTheme.colorScheme.onPrimary
            )

            SeatLegendItem(
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
                    borderColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    label = "Sold",
                    textColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
    }
}

@Composable
fun SeatLegendItem(color: Color, borderColor: Color, label: String, textColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
                modifier =
                        Modifier.size(24.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color)
                                .border(
                                        width = 1.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(4.dp)
                                ),
                contentAlignment = Alignment.Center
        ) { Text(text = "1", fontSize = 10.sp, fontWeight = FontWeight.Medium, color = textColor) }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BookingSummaryDialog(selectedSeats: List<Seat>, onProceed: () -> Unit) {
    val totalAmount =
            selectedSeats
                    .sumOf { seat ->
                        when (seat.category) {
                            SeatCategory.CLASSIC -> 200
                            SeatCategory.PRIME -> 250
                        }.toLong()
                    }
                    .toInt()

    Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Selected Seats", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(
                            text = selectedSeats.joinToString(", ") { it.seatId },
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                            text = "₹$totalAmount",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                            text = "${selectedSeats.size} Seats",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                    onClick = onProceed,
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                            ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                            )
            ) { Text(text = "Proceed to Book", fontSize = 16.sp, fontWeight = FontWeight.Medium) }
        }
    }
}
