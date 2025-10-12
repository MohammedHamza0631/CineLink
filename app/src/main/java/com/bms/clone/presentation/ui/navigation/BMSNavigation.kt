package com.bms.clone.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bms.clone.domain.model.Movie
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.domain.model.Seat
import com.bms.clone.presentation.ui.components.common.BMSBottomBar
import com.bms.clone.presentation.ui.components.common.BMSTopBar
import com.bms.clone.presentation.ui.screens.BookingScreen
import com.bms.clone.presentation.ui.screens.HomeScreen
import com.bms.clone.presentation.ui.screens.MovieDetailScreen
import com.bms.clone.presentation.ui.screens.MoviesScreen
import com.bms.clone.presentation.ui.screens.ProfileScreen
import com.bms.clone.presentation.ui.screens.SeatSelectionScreen
import com.bms.clone.presentation.ui.screens.TicketDetailScreen
import com.bms.clone.presentation.ui.screens.TicketsScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BMSNavigation() {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination

        val showTopBar =
                when {
                        currentDestination?.route == Screen.MovieDetail.route -> false
                        currentDestination?.route == Screen.Movies.route -> false
                        currentDestination?.route == Screen.Profile.route -> false
                        currentDestination?.route?.startsWith("seat_selection") == true -> false
                        currentDestination?.route?.startsWith("booking") == true -> false
                        currentDestination?.route == Screen.Tickets.route -> false
                        currentDestination?.route?.startsWith("ticket_detail") == true -> false
                        else -> true
                }

        val showBottomBar =
                when {
                        currentDestination?.route == Screen.MovieDetail.route -> false
                        currentDestination?.route?.startsWith("seat_selection") == true -> false
                        currentDestination?.route?.startsWith("booking") == true -> false
                        currentDestination?.route?.startsWith("ticket_detail") == true -> false
                        else -> true
                }

        Scaffold(
                topBar = { if (showTopBar) BMSTopBar() },
                bottomBar = { if (showBottomBar) BMSBottomBar(navController) }
        ) { innerPadding ->
                NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier =
                                if (showTopBar && showBottomBar) {
                                        Modifier.padding(innerPadding)
                                } else if (showBottomBar) {
                                        Modifier.padding(
                                                bottom = innerPadding.calculateBottomPadding()
                                        )
                                } else {
                                        Modifier
                                }
                ) {
                        composable(Screen.Home.route) {
                                HomeScreen(
                                        onNavigateToMovie = { movieId ->
                                                navController.navigate(
                                                        Screen.MovieDetail.createRoute(movieId)
                                                )
                                        }
                                )
                        }

                        composable(Screen.Movies.route) {
                                MoviesScreen(
                                        onNavigateToMovie = { movieId ->
                                                navController.navigate(
                                                        Screen.MovieDetail.createRoute(movieId)
                                                )
                                        }
                                )
                        }

                        composable(Screen.Profile.route) { ProfileScreen() }

                        composable(
                                Screen.MovieDetail.route,
                                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                        ) { backStackEntry ->
                                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                                MovieDetailScreen(
                                        movieId = movieId,
                                        onNavigateBack = { navController.popBackStack() },
                                        onNavigateToSeatSelection = { movieDetails, seatCount ->
                                                val encodedMovieTitle =
                                                        URLEncoder.encode(
                                                                movieDetails.movie.title,
                                                                StandardCharsets.UTF_8.toString()
                                                        )
                                                val encodedMoviePoster =
                                                        URLEncoder.encode(
                                                                movieDetails.movie.posterPath ?: "",
                                                                StandardCharsets.UTF_8.toString()
                                                        )
                                                navController.navigate(
                                                        Screen.SeatSelection.createRoute(
                                                                movieDetails.movie.id,
                                                                seatCount,
                                                                encodedMovieTitle,
                                                                encodedMoviePoster
                                                        )
                                                )
                                        }
                                )
                        }
                        composable(
                                Screen.SeatSelection.route,
                                arguments =
                                        listOf(
                                                navArgument("movieId") { type = NavType.IntType },
                                                navArgument("seatCount") { type = NavType.IntType },
                                                navArgument("movieTitle") { type = NavType.StringType },
                                                navArgument("moviePoster") { type = NavType.StringType }
                                        )
                        ) { backStackEntry ->
                                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                                val seatCount = backStackEntry.arguments?.getInt("seatCount") ?: 1
                                val encodedMovieTitle =
                                        backStackEntry.arguments?.getString("movieTitle") ?: ""
                                val encodedMoviePoster =
                                        backStackEntry.arguments?.getString("moviePoster") ?: ""
                                val movieTitle =
                                        URLDecoder.decode(
                                                encodedMovieTitle,
                                                StandardCharsets.UTF_8.toString()
                                        )
                                val moviePoster =
                                        URLDecoder.decode(
                                                encodedMoviePoster,
                                                StandardCharsets.UTF_8.toString()
                                        )
                                val movieDetails =
                                        MovieDetails(
                                                movie =
                                                        Movie(
                                                                id = movieId,
                                                                title = movieTitle,
                                                                overview = "",
                                                                releaseDate = "",
                                                                voteAverage = 0.0,
                                                                voteCount = 0,
                                                                genreNames = emptyList(),
                                                                backdropPath = null,
                                                                posterPath = if (moviePoster.isNotEmpty()) moviePoster else null
                                                        ),
                                                runtime = null,
                                                budget = 0L,
                                                cast = emptyList(),
                                                crew = emptyList(),
                                                reviews = emptyList(),
                                                tagline = null
                                        )

                                SeatSelectionScreen(
                                        movieDetails = movieDetails,
                                        seatCount = seatCount,
                                        onBackPressed = { navController.popBackStack() },
                                        onProceedToBooking = { selectedSeats ->
                                                val seatsJson = Json.encodeToString(selectedSeats)
                                                val encodedSeats =
                                                        URLEncoder.encode(
                                                                seatsJson,
                                                                StandardCharsets.UTF_8.toString()
                                                        )
                                                val bookingEncodedTitle =
                                                        URLEncoder.encode(
                                                                movieTitle,
                                                                StandardCharsets.UTF_8.toString()
                                                        )
                                                val bookingEncodedPoster =
                                                        URLEncoder.encode(
                                                                moviePoster,
                                                                StandardCharsets.UTF_8.toString()
                                                        )
                                                navController.navigate(
                                                        Screen.Booking.createRoute(
                                                                bookingEncodedTitle,
                                                                encodedSeats,
                                                                bookingEncodedPoster
                                                        )
                                                )
                                        }
                                )
                        }

                        composable(
                                Screen.Booking.route,
                                arguments =
                                        listOf(
                                                navArgument("movieTitle") {
                                                        type = NavType.StringType
                                                },
                                                navArgument("selectedSeats") {
                                                        type = NavType.StringType
                                                },
                                                navArgument("moviePoster") {
                                                        type = NavType.StringType
                                                }
                                        )
                        ) { backStackEntry ->
                                val encodedMovieTitle =
                                        backStackEntry.arguments?.getString("movieTitle") ?: ""
                                val encodedSeats =
                                        backStackEntry.arguments?.getString("selectedSeats") ?: ""
                                val encodedMoviePoster =
                                        backStackEntry.arguments?.getString("moviePoster") ?: ""

                                val movieTitle =
                                        URLDecoder.decode(
                                                encodedMovieTitle,
                                                StandardCharsets.UTF_8.toString()
                                        )
                                val moviePoster =
                                        URLDecoder.decode(
                                                encodedMoviePoster,
                                                StandardCharsets.UTF_8.toString()
                                        )
                                val seatsJson =
                                        URLDecoder.decode(
                                                encodedSeats,
                                                StandardCharsets.UTF_8.toString()
                                        )
                                val selectedSeats =
                                        try {
                                                Json.decodeFromString<List<Seat>>(seatsJson)
                                        } catch (e: Exception) {
                                                emptyList()
                                        }

                                BookingScreen(
                                        selectedSeats = selectedSeats,
                                        movieTitle = movieTitle,
                                        moviePoster =
                                                if (moviePoster.isNotEmpty()) moviePoster else null,
                                        onBackPressed = { navController.popBackStack() },
                                        onBookingConfirmed = {
                                                navController.navigate(Screen.Tickets.route) {
                                                        popUpTo(Screen.Home.route) {
                                                                inclusive = false
                                                        }
                                                        launchSingleTop = true
                                                }
                                        }
                                )
                        }

                        composable(Screen.Tickets.route) {
                                TicketsScreen(
                                        onBackPressed = { navController.popBackStack() },
                                        onTicketClick = { ticketId ->
                                                navController.navigate(
                                                        Screen.TicketDetail.createRoute(ticketId)
                                                )
                                        }
                                )
                        }

                        composable(
                                Screen.TicketDetail.route,
                                arguments =
                                        listOf(
                                                navArgument("ticketId") {
                                                        type = NavType.StringType
                                                }
                                        )
                        ) { backStackEntry ->
                                val ticketId = backStackEntry.arguments?.getString("ticketId") ?: ""
                                TicketDetailScreen(
                                        ticketId = ticketId,
                                        onBackPressed = { navController.popBackStack() }
                                )
                        }
                }
        }
}
