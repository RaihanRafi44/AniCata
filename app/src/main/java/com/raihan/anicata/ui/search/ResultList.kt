package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import java.util.Locale

// --- Data Class disesuaikan agar sama persis dengan Seasonal List ---
data class AnimeSearchInfo(
    val id: Int,
    val title: String,
    val airDate: String,
    val members: Int,
    val episodes: Int,
    val score: Float,
    val imageUrl: String = "" // Placeholder untuk URL gambar
)

// --- Fungsi data contoh disesuaikan dengan field baru ---
fun getDummyAnimeSearchList(): List<AnimeSearchInfo> {
    return listOf(
        AnimeSearchInfo(id = 1, title = "Naruto", airDate = "Oct 2002 - Feb 2007", members = 2584920, episodes = 220, score = 8.75f),
        AnimeSearchInfo(id = 2, title = "Naruto: Shippuuden", airDate = "Feb 2007 - Mar 2017", members = 2788471, episodes = 500, score = 9.15f),
        AnimeSearchInfo(id = 3, title = "One Piece", airDate = "Oct 1999 - Ongoing", members = 2399120, episodes = 1118, score = 8.95f),
        AnimeSearchInfo(id = 4, title = "Hunter x Hunter (2011)", airDate = "Oct 2011 - Sep 2014", members = 2891321, episodes = 148, score = 9.05f),
        AnimeSearchInfo(id = 5, title = "Bleach", airDate = "Oct 2004 - Mar 2012", members = 1987453, episodes = 366, score = 8.60f),
        AnimeSearchInfo(id = 6, title = "Dragon Ball Z", airDate = "Apr 1989 - Jan 1996", members = 1450221, episodes = 291, score = 8.45f),
        AnimeSearchInfo(id = 7, title = "Fairy Tail", airDate = "Oct 2009 - Mar 2013", members = 1876543, episodes = 175, score = 8.30f),
        AnimeSearchInfo(id = 8, title = "Death Note", airDate = "Oct 2006 - Jun 2007", members = 3899231, episodes = 37, score = 9.00f),
        AnimeSearchInfo(id = 9, title = "Fullmetal Alchemist: Brotherhood", airDate = "Apr 2009 - Jul 2010", members = 3205881, episodes = 64, score = 9.10f),
        AnimeSearchInfo(id = 10, title = "Mahou Shoujo Madoka★Magica", airDate = "Jan 2011 - Apr 2011", members = 1203456, episodes = 12, score = 8.85f)
    )
}


// --- Composable untuk satu item disalin dari Seasonal List ---
@Composable
fun SearchResultCard(item: AnimeSearchInfo) {
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
            // Bagian Kiri: Placeholder Gambar
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.LightGray.copy(alpha = 0.8f))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Bagian Kanan: Informasi Teks (Sama seperti Seasonal List)
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Tag(text = "TV", backgroundColor = tvTagColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Tag(text = "${item.episodes} eps", backgroundColor = epsTagColor)
                    Spacer(Modifier.weight(1f))
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

// --- Composable Tag yang dibutuhkan oleh SearchResultCard ---
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
fun SearchResultListLayout() {
    val animeList = getDummyAnimeSearchList()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        animeList.forEach { anime ->
            SearchResultCard(item = anime)
        }
    }
}


// --- Preview untuk melihat hasil di Android Studio ---
@Preview(showBackground = true)
@Composable
fun SearchResultListLayoutPreview() {
    MaterialTheme {
        SearchResultListLayout()
    }
}