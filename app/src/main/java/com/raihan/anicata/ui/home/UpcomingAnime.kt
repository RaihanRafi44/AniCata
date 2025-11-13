package com.raihan.anicata.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.SeasonUpcomingData
import java.util.Locale

/*
// 1. Data class untuk merepresentasikan item
data class AnimeUpcoming(
    val id: Int,
    val title: String,
    // Di aplikasi nyata, ini biasanya URL gambar dari internet
    // val imageUrl: String,
    val type: String = "TV"
)

// 2. Dummy data untuk ditampilkan dalam list
val upcomingAnime = listOf(
    AnimeUpcoming(1, "Spy x Family Season 3"),
    AnimeUpcoming(2, "Enen no Shouboutai: San no Shou Part 2"),
    AnimeUpcoming(3, "Youjo Senki II"),
    AnimeUpcoming(4, "Dungeon Meshi 2nd Season"),
    AnimeUpcoming(5, "Tensei shitara Slime Datta Ken 4th Season")
)

*/
/**
 * Section yang berisi judul dan daftar anime yang bisa di-scroll horizontal.
 *//*

@Composable
fun UpcomingSection(
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
                text = "Upcoming Anime",
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
            items(upcomingAnime) { anime ->
                AnimeUpcomingCard(anime = anime)
            }
        }
    }
}


*/
/**
 * Composable untuk satu item card anime.
 *//*

@Composable
fun AnimeUpcomingCard(
    anime: AnimeUpcoming,
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
fun UpcomingSectionPreview() {
    UpcomingSection(modifier = Modifier.padding(top = 16.dp))
}*/

@Composable
fun UpcomingSection(
    modifier: Modifier = Modifier,
    animeList: List<SeasonAnimeUpcoming>,
    isLoading: Boolean,
    error: String?,
    onViewAllClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    Column(modifier = modifier) {
        // Baris untuk judul dan ikon panah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Seasonal Anime (Upcoming)",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View All Seasonal",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onViewAllClick() } // <-- NAVIGASI DI SINI
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Handle Loading, Error, dan Data
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gagal memuat: $error",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
            animeList.isNotEmpty() -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(animeList) { anime ->
                        AnimeUpcomingCard(
                            anime = anime,
                            onAnimeClick = onAnimeClick
                        )
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data.",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


/**
 * Composable untuk satu item card anime.
 */
@Composable
fun AnimeUpcomingCard(
    anime: SeasonAnimeUpcoming,
    modifier: Modifier = Modifier,
    onAnimeClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .width(130.dp)
            .height(230.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onAnimeClick(anime.id) } // <-- NAVIGASI DETAIL
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.images.jpg.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )

            anime.type?.let {
                Text(
                    text = it,
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

            /*anime.score?.let { score ->
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .background(
                            Color.Black.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.height(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = String.format(Locale.US, "%.2f", score),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }*/
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = anime.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 4.dp)
        )
    }
}
