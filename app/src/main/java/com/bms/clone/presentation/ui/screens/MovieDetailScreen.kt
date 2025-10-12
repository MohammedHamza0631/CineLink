package com.bms.clone.presentation.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bms.clone.domain.model.MovieDetails
import com.bms.clone.presentation.intents.MovieDetailsIntent
import com.bms.clone.presentation.ui.components.common.BottomSheet
import com.bms.clone.presentation.ui.theme.BMSColors
import com.bms.clone.presentation.viewmodels.MovieDetailViewModel
import com.bms.clone.utils.ImageUtils
import kotlin.collections.setOf
import org.koin.androidx.compose.koinViewModel

@SuppressLint("DefaultLocale")
@Composable
fun MovieDetailScreen(
        modifier: Modifier = Modifier,
        movieId: Int,
        onNavigateBack: () -> Unit,
        onNavigateToSeatSelection: (MovieDetails, Int) -> Unit = { _, _ -> },
        viewModel: MovieDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var expandedReviews by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(movieId) {
        Log.d("MovieDetailScreen", "Loading movie details for ID: $movieId")
        viewModel.onIntent(MovieDetailsIntent.LoadMovieDetails(movieId))
        viewModel.onIntent(MovieDetailsIntent.LoadReviews(movieId))
    }

    //    LaunchedEffect(state.movieDetails) {
    //        state.movieDetails?.let { details ->
    //            Log.d("MovieDetailScreen", "Movie details loaded: ${details.movie.title}")
    //            Log.d("MovieDetailScreen", "Reviews count: ${details.reviews.size}")
    //        }
    //    }

    fun isReviewExpanded(reviewId: String) = expandedReviews.contains(reviewId)
    fun toggleReviewExpanded(reviewId: String) {
        expandedReviews =
                if (isReviewExpanded(reviewId)) {
                    expandedReviews - reviewId
                } else {
                    expandedReviews + reviewId
                }
    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                    modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                        onClick = {
                            Log.d("MovieDetailScreen", "Back button clicked")
                            onNavigateBack()
                        }
                ) {
                    Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                        text = state.movieDetails?.movie?.title ?: "",
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                )

                IconButton(onClick = {}) {
                    Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            when {
                state.isLoading && state.movieDetails == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BMSColors.primary)
                    }
                }
                state.error != null && state.movieDetails == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Failed to load movie details", color = Color.Red)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                    onClick = {
                                        viewModel.onIntent(
                                                MovieDetailsIntent.LoadMovieDetails(movieId)
                                        )
                                    },
                                    colors =
                                            ButtonDefaults.buttonColors(
                                                    containerColor = BMSColors.primary
                                            )
                            ) { Text("Retry") }
                        }
                    }
                }
                else -> {
                    state.movieDetails?.let { movieDetails ->
                        Column(
                                modifier =
                                        Modifier.weight(1f)
                                                .verticalScroll(rememberScrollState())
                                                .padding(bottom = 80.dp)
                        ) {
                            Card(
                                    modifier =
                                            Modifier.fillMaxWidth()
                                                    .height(300.dp)
                                                    .padding(horizontal = 16.dp),
                                    shape = RoundedCornerShape(12.dp)
                            ) {
                                Box {
                                    val imageUtils = ImageUtils()
                                    AsyncImage(
                                            model =
                                                    imageUtils.buildBackdropUrl(
                                                            movieDetails.movie.backdropPath,
                                                            "w780"
                                                    ),
                                            contentDescription = movieDetails.movie.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                    )

                                    Box(
                                            modifier =
                                                    Modifier.fillMaxWidth()
                                                            .align(Alignment.BottomCenter)
                                                            .padding(16.dp)
                                    ) {
                                        Card(
                                                colors =
                                                        CardDefaults.cardColors(
                                                                containerColor =
                                                                        MaterialTheme.colorScheme
                                                                                .onSurface.copy(
                                                                                alpha = 0.7f
                                                                        )
                                                        )
                                        ) {
                                            Text(
                                                    text = "In cinemas",
                                                    modifier =
                                                            Modifier.padding(
                                                                    horizontal = 12.dp,
                                                                    vertical = 6.dp
                                                            ),
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                        text =
                                                "${String.format("%.1f", movieDetails.movie.voteAverage)}/10",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                        text =
                                                "(${String.format("%.1fK", movieDetails.movie.voteCount / 1000.0)} Votes)",
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                    text = "2D, ICE, 4DX, DOLBY CINEMA 2D, MX4D, IMAX 2D",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                    text = "JAPANESE, ENGLISH, +3",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val runtime =
                                    movieDetails.runtime?.let { "${it / 60}h ${it % 60}m" } ?: "N/A"
                            val genres = movieDetails.movie.genreNames.joinToString(", ")

                            Text(
                                    text =
                                            "$runtime • $genres • UA13+ • ${movieDetails.movie.releaseDate}",
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Text(
                                        text = movieDetails.movie.overview,
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                            text = "Top reviews",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                            text = "${movieDetails.reviews.size} reviews >",
                                            color = BMSColors.primary,
                                            fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                if (state.isLoadingReviews) {
                                    CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = BMSColors.primary
                                    )
                                } else if (movieDetails.reviews.isEmpty()) {
                                    Text(
                                            text = "No reviews available",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                } else {
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                        items(movieDetails.reviews.take(5)) { review ->
                                            Card(
                                                    modifier =
                                                            Modifier.width(300.dp)
                                                                    .padding(vertical = 4.dp),
                                                    colors =
                                                            CardDefaults.cardColors(
                                                                    containerColor =
                                                                            MaterialTheme
                                                                                    .colorScheme
                                                                                    .surfaceVariant
                                                            )
                                            ) {
                                                Column(modifier = Modifier.padding(16.dp)) {
                                                    Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            horizontalArrangement =
                                                                    Arrangement.SpaceBetween,
                                                            verticalAlignment =
                                                                    Alignment.CenterVertically
                                                    ) {
                                                        Row(
                                                                verticalAlignment =
                                                                        Alignment.CenterVertically
                                                        ) {
                                                            Box(
                                                                    modifier =
                                                                            Modifier.size(32.dp)
                                                                                    .clip(
                                                                                            RoundedCornerShape(
                                                                                                    16.dp
                                                                                            )
                                                                                    )
                                                                                    .background(
                                                                                            BMSColors
                                                                                                    .primary
                                                                                    ),
                                                                    contentAlignment =
                                                                            Alignment.Center
                                                            ) {
                                                                Text(
                                                                        text =
                                                                                review.author
                                                                                        .take(1)
                                                                                        .uppercase(),
                                                                        fontSize = 16.sp,
                                                                        fontWeight =
                                                                                FontWeight.Bold,
                                                                        color =
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onPrimary
                                                                )
                                                            }
                                                            Spacer(modifier = Modifier.width(8.dp))
                                                            Column {
                                                                Text(
                                                                        text = review.author,
                                                                        fontSize = 14.sp,
                                                                        fontWeight =
                                                                                FontWeight.Medium
                                                                )
                                                                Text(
                                                                        text = "Reviewed on TMDB",
                                                                        fontSize = 12.sp,
                                                                        color =
                                                                                MaterialTheme
                                                                                        .colorScheme
                                                                                        .onSurfaceVariant
                                                                )
                                                            }
                                                        }
                                                    }

                                                    Spacer(modifier = Modifier.height(8.dp))

                                                    Text(
                                                            text = review.content,
                                                            fontSize = 14.sp,
                                                            maxLines =
                                                                    if (isReviewExpanded(review.id))
                                                                            Int.MAX_VALUE
                                                                    else 3,
                                                            overflow =
                                                                    if (isReviewExpanded(review.id))
                                                                            TextOverflow.Visible
                                                                    else TextOverflow.Ellipsis,
                                                            modifier =
                                                                    Modifier.clickable {
                                                                        toggleReviewExpanded(
                                                                                review.id
                                                                        )
                                                                    }
                                                    )

                                                    Spacer(modifier = Modifier.height(8.dp))

                                                    if (review.content.length > 150) {
                                                        Text(
                                                                text =
                                                                        if (isReviewExpanded(
                                                                                        review.id
                                                                                )
                                                                        )
                                                                                "Show less"
                                                                        else "Read More >",
                                                                color = BMSColors.primary,
                                                                fontSize = 12.sp,
                                                                modifier =
                                                                        Modifier.clickable {
                                                                            toggleReviewExpanded(
                                                                                    review.id
                                                                            )
                                                                        }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Text(text = "Cast", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                                Spacer(modifier = Modifier.height(12.dp))

                                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    items(movieDetails.cast.take(10)) { cast ->
                                        Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.width(80.dp)
                                        ) {
                                            val imageUtils = ImageUtils()
                                            Box(
                                                    modifier =
                                                            Modifier.size(80.dp)
                                                                    .clip(
                                                                            RoundedCornerShape(
                                                                                    40.dp
                                                                            )
                                                                    ),
                                                    contentAlignment = Alignment.Center
                                            ) {
                                                if (cast.profilePath != null) {
                                                    AsyncImage(
                                                            model =
                                                                    imageUtils.buildProfileUrl(
                                                                            cast.profilePath,
                                                                            "w185"
                                                                    ),
                                                            contentDescription = cast.name,
                                                            modifier = Modifier.fillMaxSize(),
                                                            contentScale = ContentScale.Crop
                                                    )
                                                } else {
                                                    Box(
                                                            modifier =
                                                                    Modifier.fillMaxSize()
                                                                            .background(
                                                                                    BMSColors
                                                                                            .primary
                                                                            ),
                                                            contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                                text =
                                                                        cast.name
                                                                                .take(1)
                                                                                .uppercase(),
                                                                fontSize = 24.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .onPrimary
                                                        )
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                    text = cast.name,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                            )

                                            Text(
                                                    text = cast.character,
                                                    fontSize = 10.sp,
                                                    color =
                                                            MaterialTheme.colorScheme
                                                                    .onSurfaceVariant,
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        state.movieDetails?.let { movieDetails ->
            BottomSheet(
                    onNavigateToSeatSelection = { seatCount ->
                        onNavigateToSeatSelection(movieDetails, seatCount)
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
