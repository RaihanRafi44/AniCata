package com.raihan.anicata.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.utils.ResultWrapper
import java.util.Locale

@Composable
fun NowAiringSection(
    modifier: Modifier = Modifier,
    state: ResultWrapper<List<SeasonAnimeNow>>,
    onViewAllClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    Column(modifier = modifier) {
        HomeSectionHeader(
            title = "Seasonal Anime (Now Airing)",
            onViewAllClick = onViewAllClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            is ResultWrapper.Loading -> {
                HomeSectionShimmer()
            }
            is ResultWrapper.Error -> {
                HomeSectionError(
                    message = "Failed to load seasonal anime.",
                    onRetry = null
                )
            }
            is ResultWrapper.Empty -> {
                HomeSectionEmpty()
            }
            is ResultWrapper.Success -> {
                val animeList = state.payload ?: emptyList()
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(animeList) { anime ->
                        AnimeNowAiringCard(
                            anime = anime,
                            onAnimeClick = onAnimeClick
                        )
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun AnimeNowAiringCard(
    anime: SeasonAnimeNow,
    modifier: Modifier = Modifier,
    onAnimeClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .width(130.dp)
            .clickable { onAnimeClick(anime.id) }
    ) {
        Surface(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE0E0E0)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(anime.images.jpg.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                anime.type?.let { type ->
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF006A60),
                        contentColor = Color.White
                    ) {
                        Text(
                            text = type.uppercase(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                anime.score?.let { score ->
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(6.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.65f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Score",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = String.format(Locale.US, "%.1f", score),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = anime.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF191C1E),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
