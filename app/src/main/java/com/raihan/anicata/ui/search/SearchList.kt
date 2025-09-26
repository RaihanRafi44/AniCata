/*
package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
// ... (import lainnya sama seperti sebelumnya)
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AnimeInfo(
    val title: String,
    val genres: String,
    val studio: String
)

fun getAnimeData(): List<AnimeInfo> {
    return listOf(
        AnimeInfo("Naruto (TV, 2002)", "Genres : Action, Adventure, Fantasy", "Studio Pierrot"),
        AnimeInfo("Naruto: Shippuuden (TV, 2007)", "Genres : Action, Adventure, Fantasy", "Studio Pierrot"),
        // ... (data lainnya)
    )
}

@Composable
fun AnimeListItem(anime: AnimeInfo) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE8F5E9),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = anime.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                    )
                Text(
                    text = anime.genres,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
                Text(
                    text = anime.studio,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun SearchResultList(animeList: List<AnimeInfo>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(animeList) { anime ->
            AnimeListItem(anime = anime)
        }
    }
}

@Preview(showBackground = true, name = "Search Result List Preview")
@Composable
fun SearchResultListPreview() {
    // Menggunakan data contoh untuk ditampilkan di preview
    val sampleData = getAnimeData()

    // Box digunakan untuk memberi sedikit padding di sekitar list pada mode preview
    Box(modifier = Modifier.padding(16.dp)) {
        SearchResultList(animeList = sampleData)
    }
}*/

/*package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
// ... (import lainnya sama seperti sebelumnya)
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data class diubah untuk memisahkan judul dan tahun
data class AnimeInfo(
    val mainTitle: String,
    val typeAndYear: String,
    val genres: String,
    val studio: String
)

// 2. Data contoh disesuaikan dengan struktur data class yang baru
fun getAnimeData(): List<AnimeInfo> {
    return listOf(
        AnimeInfo(
            mainTitle = "Naruto",
            typeAndYear = "(TV, 2002)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Studio Pierrot"
        ),
        AnimeInfo(
            mainTitle = "Naruto: Shippuuden",
            typeAndYear = "(TV, 2007)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Studio Pierrot"
        ),
        AnimeInfo(
            mainTitle = "One Piece",
            typeAndYear = "(TV, 1999)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Toei Animation"
        ),
        AnimeInfo(
            mainTitle = "Hunter x Hunter",
            typeAndYear = "(TV, 2011)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Madhouse"
        ),
        AnimeInfo(
            mainTitle = "Bleach",
            typeAndYear = "(TV, 2001)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Pierrot"
        ),
        AnimeInfo(
            mainTitle = "Dragon Ball Z",
            typeAndYear = "(TV, 1989)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Toei Animation"
        ),
        AnimeInfo(
            mainTitle = "Fairy Tail",
            typeAndYear = "(TV, 2009)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "A-1 Pictures"
        ),
        AnimeInfo(
            mainTitle = "Tokyo Ghoul",
            typeAndYear = "(TV, 2011)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Pierrot"
        ),
        AnimeInfo(
            mainTitle = "Death Note",
            typeAndYear = "(TV, 2006)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Madhouse"
        ),
        AnimeInfo(
            mainTitle = "Fullmetal Alchemist: Brotherhood",
            typeAndYear = "(TV, 2009)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "Bones"
        ),
        AnimeInfo(
            mainTitle = "Mahou Shoujo Madoka★Magica Movie 3: Hangyaku no Monogatari",
            typeAndYear = "(TV, 2010)",
            genres = "Genres : Action, Adventure, Fantasy",
            studio = "A-1 Pictures"
        ),

    )
}

@Composable
fun AnimeListItem(anime: AnimeInfo) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE8F5E9),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            ) {
                // 3. Text untuk judul diganti dengan Row berisi dua Text
                *//*Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = anime.mainTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = anime.typeAndYear,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.Black,
                        lineHeight = 20.sp,
                    )
                }*//*
                // --- PERBAIKAN DI SINI ---
                // Menggabungkan dua text menjadi satu dengan gaya berbeda
                // --- PERBAIKAN DI SINI ---
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = anime.mainTitle,
                        modifier = Modifier.weight(1f, fill = false),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 2, // Hanya berlaku untuk Text ini
                        overflow = TextOverflow.Ellipsis, // Hanya berlaku untuk Text ini
                        lineHeight = 20.sp,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = anime.typeAndYear,
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        softWrap = false,
                        lineHeight = 20.sp,
                    )
                }
                Text(
                    text = anime.genres,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
                Text(
                    text = anime.studio,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun SearchResultList(animeList: List<AnimeInfo>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(animeList) { anime ->
            AnimeListItem(anime = anime)
        }
    }
}

@Preview(showBackground = true, name = "Search Result List Preview")
@Composable
fun SearchResultListPreview() {
    // Menggunakan data contoh untuk ditampilkan di preview
    val sampleData = getAnimeData()

    // Box digunakan untuk memberi sedikit padding di sekitar list pada mode preview
    Box(modifier = Modifier.padding(16.dp)) {
        SearchResultList(animeList = sampleData)
    }
}*/

package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class dan data contoh tidak diubah
data class AnimeInfo(
    val mainTitle: String,
    val typeAndYear: String,
    val genres: String,
)

fun getAnimeData(): List<AnimeInfo> {
    return listOf(
        AnimeInfo(
            mainTitle = "Naruto",
            typeAndYear = "(TV, 2002)",
            genres = "Genres : Action, Adventure, Fantasy",
        ),
        AnimeInfo(
            mainTitle = "Fullmetal Alchemist: Brotherhood",
            typeAndYear = "(TV, 2009)",
            genres = "Genres : Action, Adventure, Fantasy",
        ),
        AnimeInfo(
            mainTitle = "Mahou Shoujo Madoka★Magica Movie 3: Hangyaku no Monogatari",
            typeAndYear = "(Movie, 2013)",
            genres = "Genres : Action, Adventure, Fantasy",
        ),
        AnimeInfo(
            mainTitle = "Death Note",
            typeAndYear = "(TV, 2006)",
            genres = "Genres : Action, Adventure, Fantasy",
        )
    )
}

@Composable
fun AnimeListItem(anime: AnimeInfo) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE8F5E9),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red)
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
fun SearchResultList(animeList: List<AnimeInfo>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(animeList) { anime ->
            AnimeListItem(anime = anime)
        }
    }
}

@Preview(showBackground = true, name = "Search Result List Preview")
@Composable
fun SearchResultListPreview() {
    val sampleData = getAnimeData()
    Box(modifier = Modifier.padding(16.dp)) {
        SearchResultList(animeList = sampleData)
    }
}
