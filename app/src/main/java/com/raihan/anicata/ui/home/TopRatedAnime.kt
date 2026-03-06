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
import com.raihan.anicata.data.model.anime.top.TopAnime
import java.util.Locale

@Composable
fun TopRatedSection(
    modifier: Modifier = Modifier,
    animeList: List<TopAnime>,
    isLoading: Boolean,
    error: String?,
    onViewAllClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    Column(modifier = modifier) {
        // Baris untuk judul "Top Rated Anime" dan ikon panah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Top Rated Anime",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View All Top Rated",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onViewAllClick() } // <-- NAVIGASI DI SINI
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Handle Loading, Error, dan Data
        when {
            // 1. Tampilkan loading
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp), // Sama tingginya dengan card
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // 2. Tampilkan error
            error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp) // Sama tingginya dengan card
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

            // 3. Tampilkan data jika berhasil dan tidak kosong
            animeList.isNotEmpty() -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar item
                ) {
                    items(animeList) { anime ->
                        AnimeTopRatedCard(
                            anime = anime,
                            onAnimeClick = onAnimeClick // <-- Teruskan navigasi klik
                        )
                    }
                }
            }

            // 4. Handle jika data kosong (tapi tidak error)
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp) // Sama tingginya dengan card
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
 * Menggunakan data model TopAnime
 */
@Composable
fun AnimeTopRatedCard(
    anime: TopAnime,
    modifier: Modifier = Modifier,
    onAnimeClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .width(130.dp) // Lebar setiap kartu
            .height(230.dp) // TINGGI TETAP untuk setiap kartu
            .clip(RoundedCornerShape(8.dp))
            .clickable { onAnimeClick(anime.id) } // <-- NAVIGASI DETAIL
    ) {
        Box(
            modifier = Modifier
                .height(180.dp) // Tinggi area gambar
                .fillMaxWidth()
        ) {
            // Gambar dari Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.images.jpg.largeImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            )

            // Badge Tipe (TV, ONA, Movie)
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

            // Badge Skor
            anime.score?.let { score ->
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
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Judul anime di bawah gambar
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
