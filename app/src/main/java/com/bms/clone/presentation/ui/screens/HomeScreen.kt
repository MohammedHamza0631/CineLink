package com.bms.clone.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.clone.presentation.intents.HomeIntent
import com.bms.clone.presentation.ui.components.movie.MovieCard
import com.bms.clone.presentation.viewmodels.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
        onNavigateToMovie: (Int) -> Unit,
        modifier: Modifier = Modifier,
        viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onIntent(HomeIntent.LoadMovies) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
                modifier =
                        Modifier.fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 16.dp)
        ) {
            Column {
                Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                            text = "Recommended Movies",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                    )

                    TextButton(onClick = {}) {
                        Text(
                                text = "See All",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                when {
                    state.isLoading && state.movies.isEmpty() -> {
                        Box(
                                modifier = Modifier.fillMaxWidth().height(320.dp),
                                contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) }
                    }
                    state.error != null && state.movies.isEmpty() -> {
                        Box(
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .height(320.dp)
                                                .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                        text = "Failed to load movies",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                        onClick = { viewModel.onIntent(HomeIntent.LoadMovies) },
                                        colors =
                                                ButtonDefaults.buttonColors(
                                                        containerColor =
                                                                MaterialTheme.colorScheme.primary
                                                )
                                ) { Text("Retry") }
                            }
                        }
                    }
                    else -> {
                        LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.movies) { movie ->
                                MovieCard(movie = movie, onMovieClick = onNavigateToMovie)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
            ) {
                Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                            modifier = Modifier.size(48.dp).padding(8.dp),
                            contentAlignment = Alignment.Center
                    ) { Text(text = "📍", style = MaterialTheme.typography.headlineLarge) }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                                text = "Browse By Cinemas",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                                text = "See what's playing in cinemas near you",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                            text = ">",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
