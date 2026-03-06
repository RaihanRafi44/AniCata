package com.raihan.anicata.ui.archive.manga

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.storage.UserFavoriteManga
import com.raihan.anicata.ui.archive.anime.formatAiredDateFavorite
import com.raihan.anicata.ui.top.anime.Tag // Menggunakan Tag yang sudah ada
import java.text.NumberFormat
import java.util.*

@Composable
fun ArchiveMangaCardFavorite(
    item: UserFavoriteManga,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val typeTagColor = Color(0xFFF4842D)
    val chapterTagColor = Color(0xFF4CAF50)
    val ratingColor = Color(0xFFFFC107)

    val cardShape = RoundedCornerShape(8.dp)

    val backgroundColor = if (isSelected) Color(0xFFE3F2FD) else MaterialTheme.colorScheme.surface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.6f)

    val formattedAiredDateMangaFavorite = remember(item.publishedFrom, item.publishedTo) {
        formatAiredDateFavoriteManga(item.publishedFrom, item.publishedTo)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(cardShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = cardShape,
        color = backgroundColor,
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .border(1.dp, Color.Gray)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Aired Date",
                            modifier = Modifier.size(16.dp),
                            tint = Color.DarkGray
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = formattedAiredDateMangaFavorite,
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Members",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        val formatter = NumberFormat.getInstance(Locale.US)
                        Text(
                            text = formatter.format(item.members),
                            fontSize = 12.sp,
                            color = Color.DarkGray
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Tag(text = item.type, backgroundColor = typeTagColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        // Episode Tag
                        val episodeCount = item.chapters
                        val episodeText = if (episodeCount == null || episodeCount == 0) "? eps" else "$episodeCount eps"
                        Tag(text = episodeText, backgroundColor = chapterTagColor)

                        Spacer(Modifier.weight(1f))

                        // Score
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Score",
                            tint = ratingColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format(Locale.US, "%.2f", item.score),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ArchiveMangaListFavoriteLayout(
    mangaList: List<UserFavoriteManga>,
    selectedIds: Set<String>,
    isSelectionMode: Boolean,
    onMangaClick: (String) -> Unit,
    onMangaLongClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        mangaList.forEach { manga ->
            ArchiveMangaCardFavorite(
                item = manga,
                isSelected = selectedIds.contains(manga.id),
                isSelectionMode = isSelectionMode,
                onClick = { onMangaClick(manga.id) },
                onLongClick = { onMangaLongClick(manga.id) }
            )
        }
        }
}

fun formatAiredDateFavoriteManga(fromDate: String?, toDate: String?): String {
    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    fun parseDate(dateStr: String?): String? {
        if (dateStr.isNullOrBlank()) return null
        try {
            val datePart = dateStr.substringBefore("T")
            val parts = datePart.split("-")
            if(parts.size >= 2) {
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
