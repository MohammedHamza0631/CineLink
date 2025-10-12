package com.bms.clone.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bms.clone.R
import com.bms.clone.presentation.intents.TicketsIntent
import com.bms.clone.presentation.viewmodels.TicketsViewModel
import com.bms.clone.utils.ImageUtils
import java.util.*
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailScreen(
        ticketId: String,
        onBackPressed: () -> Unit,
        viewModel: TicketsViewModel = koinViewModel()
) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val ticket = state.tickets.find { it.ticketId == ticketId }

        BackHandler { onBackPressed() }

        LaunchedEffect(ticketId) {
                if (state.tickets.isEmpty()) {
                        viewModel.onIntent(TicketsIntent.LoadTickets)
                }
        }

        if (ticket == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (state.isLoading) {
                                CircularProgressIndicator()
                        } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                                text = "Ticket not found",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Button(onClick = onBackPressed) { Text("Go Back") }
                                }
                        }
                }
                return
        }

        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                Row(
                        modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        IconButton(onClick = onBackPressed) {
                                Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                        }

                        Text(
                                text = "Your Ticket",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.width(48.dp))
                }

                Column(
                        modifier =
                                Modifier.fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .padding(horizontal = 24.dp)
                ) {
                        Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors =
                                        CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        )
                        ) {
                                Column(
                                        modifier = Modifier.padding(20.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                                verticalAlignment = Alignment.Top
                                        ) {
                                                Card(
                                                        modifier =
                                                                Modifier.size(
                                                                        width = 60.dp,
                                                                        height = 80.dp
                                                                ),
                                                        shape = RoundedCornerShape(8.dp)
                                                ) {
                                                        val imageUtils = ImageUtils()
                                                        val imageUrl =
                                                                imageUtils.buildPosterUrl(
                                                                        ticket.moviePoster,
                                                                        "w300"
                                                                )

                                                        if (!ticket.moviePoster.isNullOrEmpty() &&
                                                                        imageUrl != null
                                                        ) {
                                                                AsyncImage(
                                                                        model = imageUrl,
                                                                        contentDescription =
                                                                                ticket.movieTitle,
                                                                        modifier =
                                                                                Modifier.fillMaxSize(),
                                                                        contentScale =
                                                                                ContentScale.Crop
                                                                )
                                                        } else {
                                                                Box(
                                                                        modifier =
                                                                                Modifier.fillMaxSize()
                                                                                        .background(
                                                                                                MaterialTheme
                                                                                                        .colorScheme
                                                                                                        .primary
                                                                                                        .copy(
                                                                                                                alpha =
                                                                                                                        0.1f
                                                                                                        )
                                                                                        ),
                                                                        contentAlignment =
                                                                                Alignment.Center
                                                                ) {
                                                                        Text(
                                                                                text =
                                                                                        ticket.movieTitle
                                                                                                .take(
                                                                                                        2
                                                                                                )
                                                                                                .uppercase(),
                                                                                fontSize = 16.sp,
                                                                                fontWeight =
                                                                                        FontWeight
                                                                                                .Bold,
                                                                                color =
                                                                                        MaterialTheme
                                                                                                .colorScheme
                                                                                                .primary
                                                                        )
                                                                }
                                                        }
                                                }

                                                Column(modifier = Modifier.weight(1f)) {
                                                        Text(
                                                                text = ticket.movieTitle,
                                                                fontSize = 18.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurface
                                                        )

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text(
                                                                text = "Action • Adventure",
                                                                fontSize = 12.sp,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                        )

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text(
                                                                text =
                                                                        "${ticket.showDate} | ${ticket.showTime}",
                                                                fontSize = 12.sp,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant
                                                        )

                                                        Spacer(modifier = Modifier.height(4.dp))

                                                        Text(
                                                                text = ticket.cinemaName,
                                                                fontSize = 12.sp,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurfaceVariant,
                                                                maxLines = 2
                                                        )
                                                }
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        Icon(
                                                painter = painterResource(id = R.drawable.qrcode),
                                                contentDescription = "QR Code",
                                                modifier = Modifier.size(120.dp),
                                                tint = MaterialTheme.colorScheme.onSurface
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                                Text(
                                                        text =
                                                                "${ticket.selectedSeats.size} Ticket(s)",
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium
                                                )

                                                Text(
                                                        text =
                                                                ticket.selectedSeats.joinToString(
                                                                        ", "
                                                                ) { it.seatId },
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Bold
                                                )
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(
                                                text = "BOOKING ID: ${ticket.ticketId}",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                textAlign = TextAlign.Center
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                                text = "Tap to see more",
                                                fontSize = 10.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                textAlign = TextAlign.Center
                                        )
                                }
                        }

                        Text(
                                text =
                                        "A confirmation is sent on e-mail/SMS/WhatsApp within 15 minutes of booking.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 16.dp)
                        )

                        Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                                OutlinedButton(
                                        onClick = {
                                                viewModel.onIntent(
                                                        TicketsIntent.DeleteTicket(ticket)
                                                )
                                                onBackPressed()
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors =
                                                ButtonDefaults.outlinedButtonColors(
                                                        contentColor =
                                                                MaterialTheme.colorScheme.error
                                                ),
                                        border =
                                                androidx.compose.foundation.BorderStroke(
                                                        width = 1.dp,
                                                        color = MaterialTheme.colorScheme.error
                                                )
                                ) {
                                        Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Delete ticket")
                                }

                                OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
                                        Icon(
                                                imageVector = Icons.Default.Call,
                                                contentDescription = null,
                                                modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Contact support")
                                }
                        }

                        Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                colors =
                                        CardDefaults.cardColors(
                                                containerColor = MaterialTheme.colorScheme.surface
                                        )
                        ) {
                                Row(
                                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                                text = "Total Amount",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                        )

                                        Text(
                                                text =
                                                        "Rs ${String.format("%.2f", ticket.totalAmount)}",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                        )
                                }
                        }

                        Spacer(modifier = Modifier.height(100.dp))
                }
        }
}
