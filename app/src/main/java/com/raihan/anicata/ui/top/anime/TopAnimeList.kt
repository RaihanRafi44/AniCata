package com.raihan.anicata.ui.top.anime

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.anime.top.AiredTopAnime
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.ui.top.RankMedalBadge
import java.text.NumberFormat
import java.util.*

private fun formatAiredDate(aired: AiredTopAnime?): String {
    val from = aired?.prop?.from
    val to = aired?.prop?.to

    val isFromDateValid = from?.year != null && from.month != null && from.month in 1..12

    if (!isFromDateValid) {
        return aired?.prop?.string ?: "N/A"
    }

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Des")

    val fromMonth = monthNames[from.month - 1]
    val fromString = "$fromMonth ${from.year}"

    val isToDateValid = to?.year != null && to.month != null && to.month in 1..12
    val toString = if (isToDateValid) {
        val toMonth = monthNames[to.month - 1]
        " - $toMonth ${to.year}"
    } else {
        ""
    }

    return fromString + toString
}

/**
 * Top 3 Podium Leaderboard Component (Adaptif untuk ukuran layar hp kecil, sedang, dan besar)
 */
@Composable
fun AnimeTop3PodiumShowcase(
    top3AnimeList: List<TopAnime>,
    onAnimeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (top3AnimeList.size < 3) return

    val first = top3AnimeList[0]
    val second = top3AnimeList[1]
    val third = top3AnimeList[2]

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFE0F2F1).copy(alpha = 0.55f),
        border = BorderStroke(1.dp, Color(0xFF006A60).copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Top 3
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = "Trophy",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "TOP 3 HALL OF FAME",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = Color(0xFF191C1E)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3 Columns Podium Layout dengan weight & gap adaptif
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                // Rank #2 (Silver - Left)
                PodiumAnimeItem(
                    item = second,
                    rank = 2,
                    imageHeight = 135.dp,
                    modifier = Modifier.weight(1f),
                    onClick = { onAnimeClick(second.id) }
                )

                // Rank #1 (Gold - Center & Taller)
                PodiumAnimeItem(
                    item = first,
                    rank = 1,
                    imageHeight = 160.dp,
                    modifier = Modifier.weight(1.1f),
                    onClick = { onAnimeClick(first.id) }
                )

                // Rank #3 (Bronze - Right)
                PodiumAnimeItem(
                    item = third,
                    rank = 3,
                    imageHeight = 125.dp,
                    modifier = Modifier.weight(1f),
                    onClick = { onAnimeClick(third.id) }
                )
            }
        }
    }
}

@Composable
fun PodiumAnimeItem(
    item: TopAnime,
    rank: Int,
    imageHeight: Dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (badgeBg, badgeText) = when (rank) {
        1 -> Brush.linearGradient(listOf(Color(0xFFFFD700), Color(0xFFFFA500))) to Color.Black
        2 -> Brush.linearGradient(listOf(Color(0xFFE0E0E0), Color(0xFF9E9E9E))) to Color.Black
        else -> Brush.linearGradient(listOf(Color(0xFFCD7F32), Color(0xFF8B4513))) to Color.White
    }

    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Badge
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(badgeBg)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    if (rank == 1) {
                        Text(text = "👑", fontSize = 10.sp)
                    }
                    Text(
                        text = "#$rank",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.sp,
                        color = badgeText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Image Poster
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            shape = RoundedCornerShape(12.dp),
            shadowElevation = if (rank == 1) 6.dp else 2.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.images.jpg.largeImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Rating overlay at bottom of poster
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.75f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Score",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = String.format(Locale.US, "%.2f", item.score),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Title
        Text(
            text = item.title,
            fontWeight = if (rank == 1) FontWeight.Bold else FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color(0xFF191C1E),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp
        )
    }
}

@Composable
fun AnimeCard(
    item: TopAnime,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColor = Color(0xFF006A60)
    val studioName = item.studios.firstOrNull()?.name

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCLick() }
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Watermark Rank Number Typography (#04, #05, etc.) di pojok kanan atas agar tidak tertutup chip score
            Text(
                text = String.format(Locale.US, "#%02d", item.rank ?: 0),
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                color = themeColor.copy(alpha = 0.12f),
                lineHeight = 42.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp, top = 2.dp)
            )

            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                // Poster Gambar dengan Rank Badge Overlay
                Box(
                    modifier = Modifier
                        .width(95.dp)
                        .height(135.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0E0E0))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.images.jpg.largeImageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay Rank Medal Badge
                    RankMedalBadge(
                        rank = item.rank,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Right Info Column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        // Judul Anime
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF191C1E),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(end = 28.dp)
                        )

                        // Studio & Type Info Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            studioName?.let { studio ->
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFFE0F2F1),
                                    contentColor = themeColor
                                ) {
                                    Text(
                                        text = studio,
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                                    )
                                }
                            }

                            item.type?.let { type ->
                                val epText = if (item.episodes == null || item.episodes == 0) "? eps" else "${item.episodes} eps"
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color(0xFFF0F0F0),
                                    contentColor = Color(0xFF424242)
                                ) {
                                    Text(
                                        text = "$type • $epText",
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                                    )
                                }
                            }
                        }

                        // Aired Date
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = "Aired",
                                modifier = Modifier.size(11.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = formatAiredDate(item.aired),
                                fontSize = 11.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Genre Chips
                        if (item.genres.isNotEmpty()) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                item.genres.take(3).forEach { genre ->
                                    genre.name?.let { genreName ->
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = Color(0xFFF0F0F0),
                                            contentColor = Color(0xFF424242)
                                        ) {
                                            Text(
                                                text = genreName,
                                                fontSize = 9.5.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Footer Row: Members & Score
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "Members",
                                modifier = Modifier.size(12.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            val formatter = NumberFormat.getInstance(Locale.US)
                            Text(
                                text = "${formatter.format(item.members)} members",
                                fontSize = 10.5.sp,
                                color = Color.Gray
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFE0F2F1),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Score",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = String.format(Locale.US, "%.2f", item.score),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = themeColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TopAnimeShimmerLoading(
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
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.height(12.dp))

        repeat(5) {
            TopAnimeShimmerItem(brush = brush)
        }
    }
}

@Composable
fun TopAnimeShimmerItem(brush: Brush) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(135.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(95.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(brush)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
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

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
}
