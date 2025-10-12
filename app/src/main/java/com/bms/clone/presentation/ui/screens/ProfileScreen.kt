package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.clone.presentation.ui.theme.BMSColors
import com.bms.clone.presentation.ui.theme.LightGray

data class ProfileMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val showBadge: Boolean = false
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        ProfileMenuItem(
            title = "Your Orders",
            subtitle = "View all your bookings & purchases",
            icon = Icons.Default.ShoppingCart
        ),
        ProfileMenuItem(
            title = "Stream Library", 
            subtitle = "Rented & Purchased Movies",
            icon = Icons.Default.PlayArrow
        ),
        ProfileMenuItem(
            title = "Play Credit Card",
            subtitle = "View your Play Credit Card details and offers", 
            icon = Icons.Default.Star,
            showBadge = true
        ),
        ProfileMenuItem(
            title = "Help Centre",
            subtitle = "Need help or have questions?",
            icon = Icons.Default.Person
        ),
        ProfileMenuItem(
            title = "Accounts & Settings",
            subtitle = "Location, Payments, Permissions & More",
            icon = Icons.Default.Settings
        ),
        ProfileMenuItem(
            title = "Rewards",
            subtitle = "View your rewards & unlock new ones", 
            icon = Icons.Default.Share
        ),
        ProfileMenuItem(
            title = "Offers",
            subtitle = "",
            icon = Icons.Default.Notifications
        ),
        ProfileMenuItem(
            title = "Gift Cards",
            subtitle = "",
            icon = Icons.Default.ShoppingCart
        ),
        ProfileMenuItem(
            title = "Food & Beverages",
            subtitle = "",
            icon = Icons.Default.PlayArrow
        ),
        ProfileMenuItem(
            title = "List your Show",
            subtitle = "Got an event? Partner with us",
            icon = Icons.Default.Person
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BMSColors.background)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = BMSColors.surface
            ),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Hi James!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = BMSColors.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Edit Profile >",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BMSColors.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = BMSColors.onSurface,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(menuItems) { item ->
                ProfileMenuItemCard(item)
            }

            item {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = BMSColors.surface
            ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                Text(
                    text = "Share",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BMSColors.onSurface,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "  |  ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LightGray
                )
                
                Text(
                    text = "Rate Us",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BMSColors.onSurface,
                    textAlign = TextAlign.Center
                )

                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }


    }
}

@Composable
fun ProfileMenuItemCard(
    item: ProfileMenuItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BMSColors.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = BMSColors.onSurface,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = BMSColors.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                    
                    if (item.showBadge) {
                        Card(
                            modifier = Modifier.padding(start = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = BMSColors.primary
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "NEW",
                                style = MaterialTheme.typography.labelMedium,
                                color = BMSColors.onPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                if (item.subtitle.isNotEmpty()) {
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LightGray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
