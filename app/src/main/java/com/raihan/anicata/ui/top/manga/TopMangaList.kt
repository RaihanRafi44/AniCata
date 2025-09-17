package com.raihan.anicata.ui.top.manga

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.*

// --- Data Class untuk menampung informasi Anime ---
data class MangaItem(
    val id: Int,
    val title: String,
    val airDate: String,
    val members: Int,
    val episodes: Int,
    val score: Float,
    val imageUrl: String // Biasanya diisi URL, di sini kita kosongkan untuk placeholder
)

// --- Fungsi untuk membuat data contoh ---
fun getDummyMangaList(): List<MangaItem> {
    return List(20) { i ->
        if (i % 2 == 0) {
            MangaItem(
                id = i,
                title = "Sousou no Frieren: Beyond Journey's End",
                airDate = "Sep 2023 - Mar 2024",
                members = 1199236,
                episodes = 28,
                score = 9.30f,
                imageUrl = ""
            )
        } else {
            MangaItem(
                id = i,
                title = "Fullmetal Alchemist: Brotherhood",
                airDate = "Apr 2009 - Jul 2010",
                members = 3205881,
                episodes = 64,
                score = 9.10f,
                imageUrl = ""
            )
        }
    }
}


// --- Composable untuk satu item dalam daftar ---
@Composable
fun MangaCard(item: MangaItem) {
    //val cardBackgroundColor = Color(0xFFE6F5E9)
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        //color = cardBackgroundColor,
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        // Struktur kembali ke Row utama, tanpa Box untuk overlay
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // Untuk tinggi gambar adaptif
        ) {
            // Bagian Kiri: Placeholder Gambar
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight() // Tinggi gambar mengikuti tinggi Column di sebelahnya
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray.copy(alpha = 0.8f))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Bagian Kanan: Informasi Teks
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
                    text = item.airDate,
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
                        text = formatter.format(item.members),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
                // --- PERUBAHAN DI SINI ---
                // Row ini sekarang berisi Tag di kiri dan Rating di kanan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian Tag
                    Tag(text = "TV", backgroundColor = tvTagColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Tag(text = "${item.episodes} eps", backgroundColor = epsTagColor)

                    // Spacer dengan weight mendorong elemen berikutnya ke ujung kanan
                    Spacer(Modifier.weight(1f))

                    // Bagian Rating
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

// --- Composable kecil untuk membuat Tag (TV, eps) ---
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
fun MangaListTopLayout() {
    val mangaList = getDummyMangaList()
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF0F0F0))
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        mangaList.forEach { manga ->
            MangaCard(item = manga)
        }
    }
}


// --- Preview untuk melihat hasil di Android Studio ---
@Preview(showBackground = true)
@Composable
fun MangaListLayoutPreview() {
    MaterialTheme {
        MangaListTopLayout()
    }
}