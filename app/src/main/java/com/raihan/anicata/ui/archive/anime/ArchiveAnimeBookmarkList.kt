package com.raihan.anicata.ui.archive.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.raihan.anicata.ui.top.anime.Tag // Menggunakan Tag yang sudah ada
import java.text.NumberFormat
import java.util.*

// --- Data Dummy (Ganti dengan model data Archive Anda) ---
data class DummyArchiveAnimeBookmark(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val members: Int,
    val type: String,
    val episodes: Int?,
    val year: Int,
    val season: String
)

private val dummyList = listOf(
    DummyArchiveAnimeBookmark(1, "Naruto", "https://cdn.myanimelist.net/images/anime/1769/126629.jpg", 8.02, 2700000, "TV", 12, 2022, "Fall"),
    DummyArchiveAnimeBookmark(2, "One Piece", "https://cdn.myanimelist.net/images/anime/1665/134707.jpg", 8.44, 3200000, "TV", 16, 2023, "Fall"),
    DummyArchiveAnimeBookmark(3, "Sword Art Online", "https://cdn.myanimelist.net/images/anime/1812/129758.jpg", 7.77, 1200000, "TV", null, 2023, "Winter"),
)
// -----------------------------------------------------------

@Composable
fun ArchiveAnimeCardBookmark(
    item: DummyArchiveAnimeBookmark, // Ganti dengan model data Archive yang sebenarnya
    onCLick: () -> Unit
) {
    val tvTagColor = Color(0xFFF4842D) // Orange
    val epsTagColor = Color(0xFF4CAF50) // Green
    val starColor = Color(0xFFFFC107) // Yellow
    //val archiveTagColor = Color(0xFF673AB7) // Purple

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onCLick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f)),
    ) {
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
                        contentDescription = "Season/Year",
                        modifier = Modifier.size(16.dp),
                        tint = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${item.season} ${item.year}",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }

                // Members dan Archive/Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Members
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

                    // Spacer
                    Spacer(Modifier.weight(1f))

                    // Archive / Type Tag (Contoh mengganti Rank dengan informasi Archive)
                    //Tag(text = item.type, backgroundColor = archiveTagColor)
                }

                // Tags dan Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tags
                    Tag(text = item.type, backgroundColor = tvTagColor)
                    Spacer(modifier = Modifier.width(6.dp))

                    val episodeCount = item.episodes
                    val episodeText = if (episodeCount == null || episodeCount == 0) {
                        "? eps"
                    } else {
                        "$episodeCount eps"
                    }
                    Tag(text = episodeText, backgroundColor = epsTagColor)

                    // Spacer
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
    }
}

@Composable
fun ArchiveAnimeListBookmarkLayout(
    animeList: List<DummyArchiveAnimeBookmark>, // Ganti dengan List<ArchiveAnime>
    modifier: Modifier = Modifier,
    onAnimeClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Padding horizontal seperti di TopAnimeList.kt
    ) {
        // Tampilkan list
        animeList.forEach { anime ->
            ArchiveAnimeCardBookmark(
                item = anime,
                onCLick = {
                    onAnimeClick(anime.id)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArchiveAnimeListPreview() {
    ArchiveAnimeListBookmarkLayout(
        animeList = dummyList,
        onAnimeClick = {}
    )
}