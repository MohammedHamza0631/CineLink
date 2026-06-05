package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bms.clone.presentation.ui.theme.BmsRed
import com.bms.clone.presentation.ui.theme.CardGradientEnd
import com.bms.clone.presentation.ui.theme.CardGradientMiddle
import com.bms.clone.presentation.ui.theme.CardGradientStart
import com.bms.clone.presentation.ui.theme.DarkGray
import com.bms.clone.presentation.ui.theme.BmsGray

data class CardItem(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val ctaLabel: String,
    val onCtaClick: () -> Unit
)

@Composable
fun CardScreen(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        CardItem(
            icon = Icons.Default.Star,
            title = "Rewards",
            description = "Earn points on every booking and redeem for free tickets.",
            ctaLabel = "View rewards",
            onCtaClick = {}
        ),
        CardItem(
            icon = Icons.Default.LocationOn,
            title = "Nearby Cinemas",
            description = "Find cinemas close to you and see showtimes.",
            ctaLabel = "Explore",
            onCtaClick = {}
        ),
        CardItem(
            icon = Icons.Default.DateRange,
            title = "Coming Soon",
            description = "New releases and blockbusters arriving next month.",
            ctaLabel = "Notify me",
            onCtaClick = {}
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = MaterialTheme.shapes.medium
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    CardGradientStart,
                                    CardGradientMiddle,
                                    CardGradientEnd
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.White.copy(alpha = 0.25f))
                        .blur(56.dp)
                )
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = BmsRed.copy(alpha = 0.12f)
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(24.dp),
                                tint = BmsRed
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = DarkGray
                            )
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = BmsGray
                            )
                        }
                        Button(
                            onClick = item.onCtaClick,
                            colors = ButtonDefaults.buttonColors(containerColor = BmsRed),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.heightIn(min = 36.dp)
                        ) {
                            Text(
                                text = item.ctaLabel,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
                }
            }
        }
    }
}
