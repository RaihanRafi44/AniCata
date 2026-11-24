package com.raihan.anicata.ui.seasonalanime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.raihan.anicata.data.model.anime.season.year.AiredSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.ImageFormatJpg
import com.raihan.anicata.data.model.anime.season.year.ImageFormatWebp
import com.raihan.anicata.data.model.anime.season.year.ImagesSeasonAnimeYear
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import java.text.NumberFormat
import java.util.Locale

/**
 * Editorial Spotlight Hero Card untuk Anime Seasonal Terpopuler
 */
@Composable
fun SeasonalSpotlightHero(
    anime: SeasonAnimeYear,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColor = Color(0xFF006A60)
    val studioName = anime.studios.firstOrNull()?.name

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.images.jpg.largeImageUrl ?: anime.images.jpg.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.6f),
                                Color.Black.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Header Row: Spotlight Tag & Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFE65100),
                        contentColor = Color.White
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Whatshot,
                                contentDescription = "Spotlight",
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "SEASON HIGHLIGHT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    anime.score?.let { score ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.7f),
                            contentColor = Color.White
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Score",
                                    tint = Color(0xFFFFC107),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = String.format(Locale.US, "%.2f", score),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Bottom Section: Title, Studio, Genres
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = anime.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 22.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        studioName?.let { studio ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = themeColor.copy(alpha = 0.85f),
                                contentColor = Color.White
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Business,
                                        contentDescription = "Studio",
                                        modifier = Modifier.size(10.dp)
                                    )
                                    Text(
                                        text = studio,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        anime.type?.let { type ->
                            Text(
                                text = "${type.uppercase()} • ${anime.episodes ?: '?'} EPS",
                                fontSize = 11.sp,
                                color = Color.LightGray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Genre Chips
                    if (anime.genres.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            anime.genres.take(3).forEach { genre ->
                                genre.name?.let { genreName ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color.White.copy(alpha = 0.2f),
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = genreName,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Modern Card Item untuk Anime List
 */
@Composable
fun AnimeCard(
    item: SeasonAnimeYear,
    onClick: () -> Unit
) {
    val themeColor = Color(0xFF006A60)
    val studioName = item.studios.firstOrNull()?.name

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            // Poster Gambar
            Box(
                modifier = Modifier
                    .width(95.dp)
                    .height(135.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.images.jpg.largeImageUrl ?: item.images.jpg.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Type Badge
                item.type?.let { type ->
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = themeColor,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = type.uppercase(),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Right Info Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Judul Anime
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF191C1E),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )

                    // Studio & Aired Date Info
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        studioName?.let { studio ->
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFE0F2F1),
                                contentColor = themeColor
                            ) {
                                Text(
                                    text = studio,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = "Aired",
                                modifier = Modifier.size(11.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = formatAiredDate(item.aired),
                                fontSize = 11.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Genre Chips
                    if (item.genres.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            item.genres.take(3).forEach { genre ->
                                genre.name?.let { genreName ->
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFFF0F0F0),
                                        contentColor = Color(0xFF424242)
                                    ) {
                                        Text(
                                            text = genreName,
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Bottom Footer: Episodes, Members & Score
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Episodes & Members
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${if (item.episodes == null || item.episodes == 0) "?" else item.episodes} eps",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = themeColor
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "Members",
                                modifier = Modifier.size(13.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            val formatter = NumberFormat.getInstance(Locale.US)
                            Text(
                                text = formatter.format(item.members ?: 0),
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Score Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFFF8E1),
                        contentColor = Color(0xFFE65100)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Score",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = String.format(Locale.US, "%.2f", item.score ?: 0.0),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF212121)
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- Composable Utama untuk menampilkan daftar ---
@Composable
fun AnimeListLayout(
    animeList: List<SeasonAnimeYear>,
    onAnimeClick: (Int) -> Unit,
    hasNextPage: Boolean = false,
    isNextPageLoading: Boolean = false,
    onLoadMoreClick: () -> Unit = {}
) {
    val themeColor = Color(0xFF006A60)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        // Spotlight Hero Card untuk item pertama
        val highlightAnime = animeList.firstOrNull()
        highlightAnime?.let { hero ->
            key("hero_${hero.id}") {
                SeasonalSpotlightHero(
                    anime = hero,
                    onClick = { onAnimeClick(hero.id) }
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Kartu untuk anime berikutnya
        val remainingAnime = if (animeList.size > 1) animeList.drop(1) else emptyList()
        remainingAnime.forEach { anime ->
            key("card_${anime.id}") {
                AnimeCard(
                    item = anime,
                    onClick = { onAnimeClick(anime.id) }
                )
            }
        }

        // Tombol Load More Manual (jika masih ada halaman selanjutnya)
        if (hasNextPage) {
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onLoadMoreClick,
                enabled = !isNextPageLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColor.copy(alpha = 0.12f),
                    contentColor = themeColor,
                    disabledContainerColor = themeColor.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isNextPageLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = themeColor,
                            strokeWidth = 2.5.dp
                        )
                        Text(
                            text = "Loading more anime...",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.ExpandMore,
                            contentDescription = "Load More",
                            modifier = Modifier.size(20.dp),
                            tint = themeColor
                        )
                        Text(
                            text = "Load More Anime",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColor
                        )
                    }
                }
            }
        }
    }
}

private fun formatAiredDate(aired: AiredSeasonAnimeYear?): String {
    val from = aired?.prop?.from
    val to = aired?.prop?.to

    val isFromDateValid = from?.year != null && from.month != null && from.month in 1..12

    if (!isFromDateValid) {
        return aired?.prop?.string ?: "N/A"
    }

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
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

@Preview(showBackground = true)
@Composable
fun AnimeListLayoutPreview() {
    MaterialTheme {
        val dummyPreviewList = listOf(
            SeasonAnimeYear(
                id = 1,
                title = "Sousou no Frieren: Beyond Journey's End",
                aired = AiredSeasonAnimeYear("Sep 2023", null, null),
                members = 1199236,
                episodes = 28,
                score = 9.30,
                images = ImagesSeasonAnimeYear(
                    ImageFormatJpg("", "", ""),
                    ImageFormatWebp("", "", "")
                ),
                type = "TV",
                url = "", trailer = null, approved = true, titleEnglish = null,
                titleJapanese = null, titleSynonyms = emptyList(), source = null,
                status = null, airing = false, duration = null, rating = null,
                scoredBy = null, rank = null, popularity = null, favorites = null,
                synopsis = "During their decade-long quest to defeat the Demon King, the members of the hero's party bring peace to the realm.",
                background = null, season = null, year = null,
                broadcast = null, producers = emptyList(), licensors = emptyList(),
                studios = emptyList(), genres = emptyList(), explicitGenres = emptyList(),
                themes = emptyList(), demographics = emptyList()
            )
        )
        AnimeListLayout(
            animeList = dummyPreviewList,
            onAnimeClick = {}
        )
    }
}
