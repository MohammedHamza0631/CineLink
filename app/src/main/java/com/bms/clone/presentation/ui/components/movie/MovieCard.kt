package com.bms.clone.presentation.ui.components.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bms.clone.domain.model.Movie
import com.bms.clone.utils.ImageUtils

@Composable
fun MovieCard(movie: Movie, onMovieClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.width(160.dp).clickable { onMovieClick(movie.id) }) {
        val imageUtils = ImageUtils()
        AsyncImage(
                model = imageUtils.buildPosterUrl(movie.posterPath, "w342")
                                ?: imageUtils.buildBackdropUrl(movie.backdropPath, "w300"),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth().height(240.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            if (movie.voteAverage > 0) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                            text = String.format("%.1f", movie.voteAverage),
                            style =
                                    MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold
                                    ),
                            color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Votes",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        text = formatVotes(movie.voteCount),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
                text = movie.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
        )
    }
}

private fun formatVotes(count: Int): String {
    return when {
        count >= 1000000 -> "${(count / 100000) / 10.0}M votes"
        count >= 1000 -> "${(count / 100) / 10.0}K+ votes"
        else -> "$count votes"
    }
}
