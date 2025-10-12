package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.clone.presentation.intents.MoviesIntent
import com.bms.clone.presentation.ui.components.movie.MovieCard
import com.bms.clone.presentation.ui.theme.BMSColors
import com.bms.clone.presentation.viewmodels.MoviesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
        onNavigateToMovie: (Int) -> Unit,
        modifier: Modifier = Modifier,
        viewModel: MoviesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onIntent(MoviesIntent.LoadNowPlayingMovies) }

    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        // Custom Header
        Row(
                modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                        text = "Now Showing",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                        text = "Bengaluru | ${state.nowPlayingMovies.size} Movies",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {}) {
                Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading && state.nowPlayingMovies.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BMSColors.primary)
                }
            }
            state.error != null && state.nowPlayingMovies.isEmpty() -> {
                Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                                text = "Failed to load movies",
                                color = Color.Red,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                                onClick = { viewModel.onIntent(MoviesIntent.LoadNowPlayingMovies) },
                                colors =
                                        ButtonDefaults.buttonColors(
                                                containerColor = BMSColors.primary
                                        )
                        ) { Text("Retry") }
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                ) {
                    items(state.nowPlayingMovies) { movie ->
                        MovieCard(movie = movie, onMovieClick = onNavigateToMovie)
                    }
                }
            }
        }
    }
}
