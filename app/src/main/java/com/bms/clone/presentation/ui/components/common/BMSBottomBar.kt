package com.bms.clone.presentation.ui.components.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bms.clone.presentation.ui.navigation.BottomNavItem
import com.bms.clone.presentation.ui.theme.BMSColors

@Composable
fun BMSBottomBar(navController: NavController) {
    val items =
            listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Movies,
                    BottomNavItem.Tickets,
                    BottomNavItem.Profile
            )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                    icon = {
                        when {
                            item.icon != null -> {
                                val iconTint =
                                        if (currentRoute == item.route) {
                                            BMSColors.primary
                                        } else {
                                            Color.Gray
                                        }
                                Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = iconTint
                                )
                            }
                            item.selectedIconRes != null && item.unselectedIconRes != null -> {
                                val iconResource =
                                        if (currentRoute == item.route) {
                                            item.selectedIconRes
                                        } else {
                                            item.unselectedIconRes
                                        }
                                Icon(
                                        imageVector = ImageVector.vectorResource(id = iconResource),
                                        contentDescription = item.title,
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    },
                    colors =
                            NavigationBarItemDefaults.colors(
                                    selectedIconColor = BMSColors.primary,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = BMSColors.primary,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = Color.Transparent
                            ),
                    label = { Text(item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            when (item.route) {
                                "home" -> {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }
                                }
                                else -> {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        }
                    }
            )
        }
    }
}
