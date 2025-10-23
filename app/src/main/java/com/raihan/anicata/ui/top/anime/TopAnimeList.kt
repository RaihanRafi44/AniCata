package com.raihan.anicata.ui.top.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.anime.top.AiredTopAnime
import com.raihan.anicata.data.model.anime.top.TopAnime
import java.text.NumberFormat
import java.util.*

private fun formatAiredDate(aired: AiredTopAnime?): String {
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

@Composable
fun AnimeCard(item: TopAnime) {
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // <- Diambil dari kode referensi Anda
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f)),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp) // Padding internal 8.dp
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // <- Diambil dari kode referensi Anda
        ) {
            // Gambar (sama persis)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.images.jpg.largeImageUrl)
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

            // Kolom Info Utama (Layout dari kotak merah + spacing dari Anda)
            Column(
                modifier = Modifier
                    .weight(1f) // Gunakan weight(1f)
                    .fillMaxWidth(),
                // MENGGUNAKAN verticalArrangement DARI KODE REFERENSI ANDA:
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Kotak 1: Judul
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )

                // Kotak 2: Tanggal Tayang
                Text(
                    text = formatAiredDate(item.aired), // Asumsi fungsi ini ada
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )

                // Kotak 3: Members dan Rank (Layout dari kotak merah)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian kiri (Members)
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

                    // Spacer untuk mendorong Rank ke kanan
                    Spacer(Modifier.weight(1f))

                    // Bagian kanan (Rank)
                    Text(
                        text = "Rank ${item.rank?.toString() ?: "?"}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }

                // Kotak 4: Tags dan Score (Layout dari kotak merah)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian kiri (Tags)
                    item.type?.let { Tag(text = it, backgroundColor = tvTagColor) }
                    Spacer(modifier = Modifier.width(6.dp))
                    item.episodes?.let { Tag(text = "$it eps", backgroundColor = epsTagColor) }

                    // Spacer untuk mendorong Score ke kanan
                    Spacer(Modifier.weight(1f))

                    // Bagian kanan (Score)
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
fun AnimeListTopLayout(
    animeList: List<TopAnime>,
    currentPage: Int = 1,   // halaman sekarang, 1-based
    pageSize: Int = 25,     // jumlah item per halaman
    // isLoading dan error DIHAPUS dari parameter
    modifier: Modifier = Modifier // Ubah jadi modifier biasa
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Pindahkan padding horizontal ke sini
    ) {

        // Langsung hitung offset dan rank berdasarkan animeList (urutan asli dari API)
        val offset = ((currentPage - 1).coerceAtLeast(0)) * pageSize //
        val displayRanked: List<TopAnime> = animeList.mapIndexed { index, anime -> // <-- GANTI 'sorted' menjadi 'animeList'
            anime.copy(rank = offset + index + 1) //
        }

        // Tampilkan list
        displayRanked.forEach { anime ->
            AnimeCard(item = anime)
        }
    }
}