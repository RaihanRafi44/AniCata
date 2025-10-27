package com.raihan.anicata.ui.seasonalanime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.raihan.anicata.data.model.anime.season.now.AiredSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.ImageFormatJpg
import com.raihan.anicata.data.model.anime.season.now.ImageFormatWebp
import com.raihan.anicata.data.model.anime.season.now.ImagesSeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import java.text.NumberFormat
import java.util.*

// --- Composable untuk satu item dalam daftar ---
@Composable
fun AnimeCard(item: SeasonAnimeNow) { // Menggunakan SeasonAnimeNow
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            // Bagian Kiri: Gambar dari URL (Coil)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    // Mengambil URL dari data model
                    .data(item.images.jpg.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray.copy(alpha = 0.8f))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Bagian Kanan: Informasi Teks dari data model
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.title, // Dari API
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
                /*Text(
                    // Dari API (nullable)
                    text = item.aired?.prop?.string ?: "Unknown",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )*/
                // Kotak 2: Tanggal Tayang
                Text(
                    text = formatAiredDate(item.aired), // Asumsi fungsi ini ada
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
                        // Dari API (nullable)
                        text = formatter.format(item.members ?: 0),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tag Tipe (dinamis)
                    Tag(
                        // Dari API (nullable)
                        text = item.type?.uppercase() ?: "N/A",
                        backgroundColor = tvTagColor
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    // Tag Episode
                    /*Tag(
                        // Dari API (nullable)
                        text = "${item.episodes ?: '?'} eps",
                        backgroundColor = epsTagColor
                    )*/

                    // Tag Episode
                    Tag(
                        // Cek jika episodes adalah 0 (hasil map dari null), tampilkan '?'
                        text = "${if (item.episodes == 0) '?' else item.episodes} eps",
                        backgroundColor = epsTagColor
                    )

                    Spacer(Modifier.weight(1f))

                    // Rating
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = starColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        // Dari API (nullable, Double)
                        text = String.format(Locale.US, "%.2f", item.score ?: 0.0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

// Composable Tag (tidak berubah)
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

// --- Composable Utama untuk menampilkan daftar ---
@Composable
fun AnimeListLayout(
    animeList: List<SeasonAnimeNow> // Menerima list dari data model
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        // Hapus `val animeList = getDummyAnimeList()`
        // Gunakan forEach karena parent-nya (SeasonalScreen) sudah verticalScroll
        animeList.forEach { anime ->
            AnimeCard(item = anime)
        }
    }
}

private fun formatAiredDate(aired: AiredSeasonAnimeNow?): String {
    val from = aired?.prop?.from
    val to = aired?.prop?.to

    // Tambahkan pemeriksaan apakah bulan valid (antara 1 dan 12)
    val isFromDateValid = from?.year != null && from.month != null && from.month in 1..12

    if (!isFromDateValid) {
        return aired?.prop?.string ?: "N/A"
    }

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Des")

    // Kode ini sekarang aman karena kita sudah memeriksa nilai bulan di atas
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

// --- Preview untuk melihat hasil di Android Studio ---
@Preview(showBackground = true)
@Composable
fun AnimeListLayoutPreview() {
    MaterialTheme {
        // Buat data dummy HANYA untuk preview
        val dummyPreviewList = listOf(
            SeasonAnimeNow(
                id = 1,
                title = "Sousou no Frieren: Beyond Journey's End",
                aired = AiredSeasonAnimeNow("Sep 2023", null, null),
                members = 1199236,
                episodes = 28,
                score = 9.30,
                images = ImagesSeasonAnimeNow(
                    ImageFormatJpg("", "", ""),
                    ImageFormatWebp("", "", "")
                ),
                type = "TV",
                // ... isi properti lain yang dibutuhkan jika ada (set ke null atau default)
                url = "", trailer = null, approved = true, titleEnglish = null,
                titleJapanese = null, titleSynonyms = emptyList(), source = null,
                status = null, airing = false, duration = null, rating = null,
                scoredBy = null, rank = null, popularity = null, favorites = null,
                synopsis = null, background = null, season = null, year = null,
                broadcast = null, producers = emptyList(), licensors = emptyList(),
                studios = emptyList(), genres = emptyList(), explicitGenres = emptyList(),
                themes = emptyList(), demographics = emptyList()
            )
        )
        AnimeListLayout(animeList = dummyPreviewList)
    }
}