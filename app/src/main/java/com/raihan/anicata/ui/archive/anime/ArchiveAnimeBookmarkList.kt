package com.raihan.anicata.ui.archive.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ArchiveAnimeCardBookmark(
    item: UserBookmarkAnime,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val themeColor = Color(0xFF006A60)
    val cardShape = RoundedCornerShape(16.dp)

    val backgroundColor = if (isSelected) Color(0xFFE0F2F1) else Color.White
    val borderStroke = if (isSelected) {
        BorderStroke(1.5.dp, themeColor)
    } else {
        BorderStroke(1.dp, Color(0xFFE2E8F0))
    }

    val formattedAiredDate = remember(item.airedFrom, item.airedTo) {
        formatAiredDate(item.airedFrom, item.airedTo)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(cardShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = borderStroke
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                // Poster Image (90dp x 130dp)
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE2E8F0))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Info Column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        // Title
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 19.sp
                        )

                        // Badges Row (Type & Episodes)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (item.type.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFE0F2F1),
                                    contentColor = themeColor
                                ) {
                                    Text(
                                        text = item.type,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.5.dp)
                                    )
                                }
                            }

                            val episodeCount = item.episodes
                            val episodeText = if (episodeCount == null || episodeCount == 0) "? eps" else "$episodeCount eps"
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFDCFCE7),
                                contentColor = Color(0xFF15803D)
                            ) {
                                Text(
                                    text = episodeText,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.5.dp)
                                )
                            }
                        }

                        // Aired Date Range
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 1.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = "Aired Date",
                                modifier = Modifier.size(13.dp),
                                tint = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = formattedAiredDate,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF475569),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Footer Row: Members & Score Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "Members",
                                modifier = Modifier.size(13.dp),
                                tint = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            val formatter = NumberFormat.getInstance(Locale.US)
                            Text(
                                text = "${formatter.format(item.members)} members",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFEF3C7),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 7.dp, vertical = 3.5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Score",
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = String.format(Locale.US, "%.2f", item.score),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.5.sp,
                                color = Color(0xFFB45309)
                            )
                        }
                    }
                }
            }

            // Checkmark overlay when selected
            if (isSelected) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = themeColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArchiveAnimeListBookmarkLayout(
    animeList: List<UserBookmarkAnime>,
    selectedIds: Set<String>,
    isSelectionMode: Boolean,
    onAnimeClick: (String) -> Unit,
    onAnimeLongClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        animeList.forEach { anime ->
            ArchiveAnimeCardBookmark(
                item = anime,
                isSelected = selectedIds.contains(anime.id),
                isSelectionMode = isSelectionMode,
                onClick = { onAnimeClick(anime.id) },
                onLongClick = { onAnimeLongClick(anime.id) }
            )
        }
    }
}

fun formatAiredDate(fromDate: String?, toDate: String?): String {
    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    fun parseDate(dateStr: String?): String? {
        if (dateStr.isNullOrBlank()) return null
        try {
            val datePart = dateStr.substringBefore("T")
            val parts = datePart.split("-")
            if (parts.size >= 2) {
                val year = parts[0]
                val month = parts[1].toIntOrNull()
                if (month != null && month in 1..12) {
                    val monthName = monthNames[month - 1]
                    return "$monthName $year"
                }
            }
        } catch (e: Exception) {
            return null
        }
        return null
    }

    val fromStr = parseDate(fromDate)
    val toStr = parseDate(toDate)

    return when {
        fromStr != null && toStr != null -> {
            if (fromStr == toStr) fromStr else "$fromStr - $toStr"
        }
        fromStr != null -> fromStr
        else -> "-"
    }
}
