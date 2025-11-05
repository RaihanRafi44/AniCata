package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Data class dan data contoh tidak diubah
data class AnimeInfo(
    val id: Int,              // <-- TAMBAHKAN INI
    val category: String,       // <-- TAMBAHKAN INI
    val mainImage: String,
    val mainTitle: String,
    val typeAndYear: String,
    val genres: String,
)

@Composable
fun AnimeListItem(
    anime: AnimeInfo,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE8F5E9),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.4f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = anime.mainImage,
                contentDescription = anime.mainTitle,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            ) {
                // --- PERUBAHAN DI SINI ---
                // Judul dan Tahun sekarang menjadi Text terpisah di dalam Column

                // Baris 1: Judul
                Text(
                    text = anime.mainTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )

                // Baris 2: Tahun
                Text(
                    text = anime.typeAndYear,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )

                // Baris 3: Genre
                Text(
                    text = anime.genres,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )

            }
        }
    }
}

// ... Sisa kode tidak berubah
@Composable
fun SearchResultList(
    animeList: List<AnimeInfo>,
    modifier: Modifier = Modifier,
    onItemClick: (AnimeInfo) -> Unit // <-- 5. UBAH PARAMETER INI
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(animeList) { anime ->
            AnimeListItem(
                anime = anime,
                onClick = { onItemClick(anime) }
            )
        }
    }
}

/*@Preview(showBackground = true, name = "Search Result List Preview")
@Composable
fun SearchResultListPreview() {
    val sampleData = getAnimeData()
    Box(modifier = Modifier.padding(16.dp)) {
        SearchResultList(animeList = sampleData)
    }
}*/
