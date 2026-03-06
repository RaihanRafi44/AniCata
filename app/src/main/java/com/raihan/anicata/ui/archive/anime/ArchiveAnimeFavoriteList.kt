package com.raihan.anicata.ui.archive.anime

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import com.raihan.anicata.data.model.storage.UserFavoriteAnime
import com.raihan.anicata.ui.top.anime.Tag // Menggunakan Tag yang sudah ada
import java.text.NumberFormat
import java.util.*
import kotlin.collections.forEach

@Composable
fun ArchiveAnimeCardFavorite(
    item: UserFavoriteAnime,
    isSelected: Boolean, // Parameter baru: Status terpilih
    isSelectionMode: Boolean, // Parameter baru: Apakah sedang mode pilih?
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    val cardShape = RoundedCornerShape(8.dp)

    val backgroundColor = if (isSelected) Color(0xFFE3F2FD) else MaterialTheme.colorScheme.surface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.6f)

    val formattedAiredDateAnime = remember(item.airedFrom, item.airedTo) {
        formatAiredDateFavorite(item.airedFrom, item.airedTo)
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
        //.clickable { onCLick() },
        shape = cardShape,
        color = backgroundColor,
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f)),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                // Gambar
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
                        .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Kolom Info Utama
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Judul
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp,
                    )

                    // Season dan Tahun
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Aired Date",
                            modifier = Modifier.size(16.dp),
                            tint = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = formattedAiredDateAnime,
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Members
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

                    // Episode dan Score
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Tag(text = item.type, backgroundColor = tvTagColor)
                        Spacer(modifier = Modifier.width(8.dp))
                        // Episode Tag
                        val episodeCount = item.episodes
                        val episodeText = if (episodeCount == null || episodeCount == 0) "? eps" else "$episodeCount eps"
                        Tag(text = episodeText, backgroundColor = epsTagColor)

                        Spacer(Modifier.weight(1f))

                        // Score
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Score",
                            tint = starColor,
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
fun ArchiveAnimeListFavoriteLayout(
    animeList: List<UserFavoriteAnime>,
    selectedIds: Set<String>,
    isSelectionMode: Boolean,
    onAnimeClick: (String) -> Unit,
    onAnimeLongClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        animeList.forEach { anime ->
            ArchiveAnimeCardFavorite(
                item = anime,
                isSelected = selectedIds.contains(anime.id),
                isSelectionMode = isSelectionMode,
                onClick = { onAnimeClick(anime.id) },
                onLongClick = { onAnimeLongClick(anime.id) }
            )
        }
    }
}

fun formatAiredDateFavorite(fromDate: String?, toDate: String?): String {
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