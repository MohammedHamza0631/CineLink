package com.bms.clone.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.bms.clone.R

sealed class BottomNavItem (
    val title: String,
    val route: String,
    val icon: ImageVector? = null,
    @DrawableRes val iconRes: Int? = null,
    @DrawableRes val selectedIconRes: Int? = null,
    @DrawableRes val unselectedIconRes: Int? = null
) {
    object Home: BottomNavItem(
        title = "Home",
        route = Screen.Home.route,
        selectedIconRes = R.drawable.bmssvghome,
        unselectedIconRes = R.drawable.bmslogobw
    )
    
    object Movies : BottomNavItem(
        title = "Movies",
        route = Screen.Movies.route,
        icon = Icons.Default.PlayArrow
    )

    object Tickets : BottomNavItem(
        title = "Tickets",
        route = Screen.Tickets.route,
        icon = Icons.Default.Star
    )

    object Profile : BottomNavItem(
        title = "Profile",
        route = Screen.Profile.route,
        icon = Icons.Default.Person
    )
}
