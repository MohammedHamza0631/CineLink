package com.bms.clone.presentation.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.clone.R
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.presentation.ui.theme.BMSColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onNavigateToSeatSelection: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedSeatCount by rememberSaveable { mutableStateOf(2) }

    fun getVehicleIcon(count: Int): Int {
        return when (count) {
            1 -> R.drawable.cycling
            2 -> R.drawable.scooter
            3 -> R.drawable.rickshaw
            4 -> R.drawable.car
            5, 6 -> R.drawable.sedan
            7, 8 -> R.drawable.minivan
            9, 10 -> R.drawable.bus
            else -> R.drawable.car
        }
    }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                showBottomSheet = true
            },
        colors = CardDefaults.cardColors(containerColor = BMSColors.primary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Book tickets",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    
    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet = false
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "How many seats?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Icon(
                    painter = painterResource(id = getVehicleIcon(selectedSeatCount)),
                    contentDescription = "Vehicle for $selectedSeatCount seats",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 32.dp),
                    tint = Color.Unspecified
                )

                LazyRow(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed((1..10).toList()) { index, count ->
                        val isSelected = count == selectedSeatCount
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) BMSColors.primary else Color.Transparent
                                )
                                .border(
                                    width = if (isSelected) 0.dp else 1.dp,
                                    color = if (isSelected) Color.Transparent else Color.Gray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable {
                                    selectedSeatCount = count
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = count.toString(),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PRIME",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            text = "₹236",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "CLASSIC",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            text = "₹236",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                            onNavigateToSeatSelection(selectedSeatCount)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BMSColors.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Select Seats",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
