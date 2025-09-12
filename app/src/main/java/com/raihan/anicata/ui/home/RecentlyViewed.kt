package com.raihan.anicata.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data class untuk merepresentasikan item
data class Anime(
    val id: Int,
    val title: String,
    // Di aplikasi nyata, ini biasanya URL gambar dari internet
    // val imageUrl: String,
    val type: String = "SPECIAL"
)

// 2. Dummy data untuk ditampilkan dalam list
val recentlyViewedAnime = listOf(
    Anime(1, "Dandadan Season 2"),
    Anime(2, "Gachiakuta"),
    Anime(3, "Kaoru Hana wa Rin to Saku"),
    Anime(4, "Oshi no Ko Season 2"),
    Anime(5, "Kimi ni Todoke Season 3")
)

/**
 * Section yang berisi judul dan daftar anime yang bisa di-scroll horizontal.
 */
@Composable
fun RecentlyViewedSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Baris untuk judul "Recently Viewed" dan ikon panah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recently Viewed",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View All"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // RecyclerView Horizontal (LazyRow)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar item
        ) {
            items(recentlyViewedAnime) { anime ->
                AnimeCard(anime = anime)
            }
        }
    }
}


/**
 * Composable untuk satu item card anime.
 */
@Composable
fun AnimeCard(
    anime: Anime,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(130.dp) // Lebar setiap kartu
            .height(230.dp) // TINGGI TETAP untuk setiap kartu
    ) {
        Box(
            modifier = Modifier
                .height(180.dp) // Tinggi area gambar
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Placeholder untuk gambar. Di aplikasi nyata, gunakan library seperti Coil atau Glide
            // untuk memuat gambar dari URL.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            // Badge "TV" di pojok kanan atas
            Text(
                text = anime.type,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .background(Color(0xFFFF9800), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Judul anime di bawah gambar
        Text(
            text = anime.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecentlyViewedSectionPreview() {
    RecentlyViewedSection(modifier = Modifier.padding(top = 16.dp))
}
