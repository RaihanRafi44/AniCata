package com.raihan.anicata.ui.alllists

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.media.MediaItem
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MediaItemCard(
    item: MediaItem,
    onClick: () -> Unit
) { // GANTI: AnimeAllCard -> MediaItemCard, SearchAnime -> MediaItem
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl) // GANTI: Pakai item.imageUrl
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Info Teks
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
                Text(
                    text = item.airedOrPublished, // GANTI: Pakai item.airedOrPublished
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Members",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val formatter = NumberFormat.getInstance(Locale.US)
                    Text(
                        text = formatter.format(item.members ?: 0),
                        fontSize = 12.sp, color = Color.DarkGray
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Tag(text = item.itemType, backgroundColor = tvTagColor) // GANTI: Pakai item.itemType
                    Spacer(modifier = Modifier.width(6.dp))
                    Tag(text = item.itemDetails, backgroundColor = epsTagColor) // GANTI: Pakai item.itemDetails
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = starColor,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, "%.2f", (item.score ?: 0.0).toFloat()),
                        fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun MediaListLayout( // GANTI: AnimeListLayout -> MediaListLayout
    mediaList: List<MediaItem>, // GANTI: animeList -> mediaList
    onMediaClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        mediaList.forEach { media -> // GANTI: anime -> media
            MediaItemCard(
                item = media,
                // 5. Panggil callback dengan ID item
                onClick = { onMediaClick(media.id) }
            ) // GANTI: AnimeAllCard -> MediaItemCard
        }
    }
}

@Composable
fun ErrorStateContent(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Failed to load data",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AllListsShimmerLoading(
    modifier: Modifier = Modifier
) {
    val shimmerColors = listOf(
        Color(0xFFF0F0F0),
        Color(0xFFFAFAFA),
        Color(0xFFF0F0F0),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(6) {
            AllListsShimmerItem(brush = brush)
        }
    }
}

@Composable
fun AllListsShimmerItem(brush: Brush) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, brush)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(115.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.45f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(22.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
}
