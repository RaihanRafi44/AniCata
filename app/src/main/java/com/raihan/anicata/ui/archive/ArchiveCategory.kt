package com.raihan.anicata.ui.archive

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Grid3x3
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import com.raihan.anicata.data.model.storage.UserBookmarkManga
import com.raihan.anicata.data.model.storage.UserFavoriteAnime
import com.raihan.anicata.data.model.storage.UserFavoriteManga
import com.raihan.anicata.utils.clickableSingle
import java.util.Locale

// AniCata Brand Theme Colors
val AniCataTeal = Color(0xFF006A60)
val AniCataLightTeal = Color(0xFFE0F2F1)
val AniCataTextPrimary = Color(0xFF191C1E)
val AniCataTextSecondary = Color(0xFF64748B)
val AniCataRose = Color(0xFFE11D48)
val AniCataLightRose = Color(0xFFFFE4E6)
val AniCataGold = Color(0xFFD97706)
val AniCataLightGold = Color(0xFFFEF3C7)

/**
 * Unified UI Data class representing any saved item in Archive.
 * Marked as @Immutable for smooth compose skipping & 60/120fps scrolling.
 */
@Immutable
data class ArchiveUiItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val type: String,
    val subInfo: String,
    val isFavorite: Boolean = false,
    val isManga: Boolean = false
)

fun UserBookmarkAnime.toArchiveUiItem() = ArchiveUiItem(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    type = type.ifBlank { "ANIME" },
    subInfo = if (episodes == null || episodes == 0) "? eps" else "$episodes eps",
    isFavorite = false,
    isManga = false
)

fun UserFavoriteAnime.toArchiveUiItem() = ArchiveUiItem(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    type = type.ifBlank { "ANIME" },
    subInfo = if (episodes == null || episodes == 0) "? eps" else "$episodes eps",
    isFavorite = true,
    isManga = false
)

fun UserBookmarkManga.toArchiveUiItem() = ArchiveUiItem(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    type = type.ifBlank { "MANGA" },
    subInfo = if (chapters == null || chapters == 0) "? ch" else "$chapters ch",
    isFavorite = false,
    isManga = true
)

fun UserFavoriteManga.toArchiveUiItem() = ArchiveUiItem(
    id = id,
    title = title,
    imageUrl = imageUrl,
    score = score,
    type = type.ifBlank { "MANGA" },
    subInfo = if (chapters == null || chapters == 0) "? ch" else "$chapters ch",
    isFavorite = true,
    isManga = true
)

/**
 * Streamlined Compact Top Header Bar.
 * Standardized spacing and heights for balanced visual breathing room.
 */
@Composable
fun ArchiveCompactTopHeader(
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Title & Count Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 20.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(AniCataTeal)
            )

            Text(
                text = "My Archive",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AniCataTextPrimary,
                letterSpacing = (-0.3).sp
            )

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AniCataLightTeal,
                border = BorderStroke(0.5.dp, AniCataTeal.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "$totalCount items",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = AniCataTeal,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

/**
 * Full-width Media Switcher (Anime / Manga) Segmented Control.
 * Guarantees zero clipping across all screen dimensions.
 */
@Composable
fun ArchiveMediaSegmentedControl(
    selectedIsManga: Boolean,
    onTabSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF1F5F9),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(3.dp)
        ) {
            val animeBg by animateColorAsState(
                targetValue = if (!selectedIsManga) AniCataTeal else Color.Transparent,
                animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing),
                label = "animeBg"
            )
            val animeContentColor by animateColorAsState(
                targetValue = if (!selectedIsManga) Color.White else Color(0xFF64748B),
                label = "animeTextColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(11.dp))
                    .background(animeBg)
                    .clickableSingle { onTabSelected(false) },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = "Anime",
                        tint = animeContentColor,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Anime",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = animeContentColor
                    )
                }
            }

            val mangaBg by animateColorAsState(
                targetValue = if (selectedIsManga) AniCataTeal else Color.Transparent,
                animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing),
                label = "mangaBg"
            )
            val mangaContentColor by animateColorAsState(
                targetValue = if (selectedIsManga) Color.White else Color(0xFF64748B),
                label = "mangaTextColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(11.dp))
                    .background(mangaBg)
                    .clickableSingle { onTabSelected(true) },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = "Manga",
                        tint = mangaContentColor,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Manga",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = mangaContentColor
                    )
                }
            }
        }
    }
}

/**
 * Compact Search and Category Row.
 * Uses LazyRow for Category Pills so it NEVER clips on small mobile screens.
 */
@Composable
fun ArchiveSearchAndFilterRow(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit,
    isGridView: Boolean,
    onToggleGridView: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Search & View Toggle Line
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Search Input Field
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                    .weight(1f)
                    .height(42.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = AniCataTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (query.isEmpty()) {
                            Text(
                                text = "Search saved titles...",
                                fontSize = 13.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                        BasicTextField(
                            value = query,
                            onValueChange = onQueryChange,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 13.5.sp,
                                color = AniCataTextPrimary,
                                fontWeight = FontWeight.Medium
                            ),
                            cursorBrush = SolidColor(AniCataTeal),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = { onQueryChange("") },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = AniCataTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // View Mode Toggle Button (Grid / List)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.size(42.dp)
            ) {
                IconButton(
                    onClick = { onToggleGridView(!isGridView) },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = if (isGridView) Icons.AutoMirrored.Filled.List else Icons.Default.Grid3x3,
                        contentDescription = "Toggle View",
                        tint = AniCataTeal,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Category Filter Chips Line (Scrolls horizontally on small devices to prevent clipping)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            val categories = listOf("All", "Bookmarks", "Favorites")
            items(categories.size) { index ->
                val label = categories[index]
                val isSelected = selectedCategoryIndex == index
                val chipBg = if (isSelected) AniCataTeal else Color(0xFFF8FAFC)
                val chipTextColor = if (isSelected) Color.White else Color(0xFF475569)
                val chipBorder = if (isSelected) AniCataTeal else Color(0xFFE2E8F0)

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = chipBg,
                    border = BorderStroke(1.dp, chipBorder),
                    modifier = Modifier.clickableSingle { onCategorySelected(index) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        if (index == 1) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else AniCataTeal,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        } else if (index == 2) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else AniCataRose,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = chipTextColor
                        )
                    }
                }
            }
        }
    }
}

/**
 * Modern Poster Grid Card optimized for fast 60/120fps scrolling.
 */
@Composable
fun ArchiveGridCard(
    item: ArchiveUiItem,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(14.dp)
    val context = LocalContext.current

    val formattedScore = remember(item.score) {
        String.format(Locale.US, "%.1f", item.score)
    }

    val imageRequest = remember(item.imageUrl) {
        ImageRequest.Builder(context)
            .data(item.imageUrl)
            .crossfade(true)
            .build()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) AniCataTeal else Color(0xFFE2E8F0)
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.72f)
                    .background(Color(0xFFF1F5F9))
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Bottom Scrim for Title Contrast
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Format Badge (Top Left)
                if (item.type.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = AniCataTeal.copy(alpha = 0.95f),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(6.dp)
                    ) {
                        Text(
                            text = item.type,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                        )
                    }
                }

                // Rating Badge (Top Right)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Score",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = formattedScore,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Selection Checkmark Overlay
                if (isSelected) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Selected",
                                tint = AniCataTeal,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            // Info Container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.5.sp,
                    color = AniCataTextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.5.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.subInfo,
                        fontSize = 12.sp,
                        color = AniCataTextSecondary,
                        fontWeight = FontWeight.Medium
                    )

                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = if (item.isFavorite) AniCataRose else AniCataTeal,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

/**
 * Modern Row Card for Archive List View optimized for fast 60/120fps scrolling.
 */
@Composable
fun ArchiveListCard(
    item: ArchiveUiItem,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(14.dp)
    val context = LocalContext.current

    val formattedScore = remember(item.score) {
        String.format(Locale.US, "%.2f", item.score)
    }

    val imageRequest = remember(item.imageUrl) {
        ImageRequest.Builder(context)
            .data(item.imageUrl)
            .crossfade(true)
            .build()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) AniCataLightTeal.copy(alpha = 0.5f) else Color.White),
        border = BorderStroke(
            if (isSelected) 1.5.dp else 1.dp,
            if (isSelected) AniCataTeal else Color(0xFFE2E8F0)
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF1F5F9))
                ) {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.5.sp,
                            color = AniCataTextPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (item.type.isNotBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFF1F5F9)
                                ) {
                                    Text(
                                        text = item.type,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF334155),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (item.isManga) AniCataLightRose else AniCataLightTeal
                            ) {
                                Text(
                                    text = item.subInfo,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (item.isManga) AniCataRose else AniCataTeal,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(
                                    color = AniCataLightGold,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Score",
                                tint = AniCataGold,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = formattedScore,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFFB45309)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = if (item.isFavorite) AniCataRose else AniCataTeal,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (item.isFavorite) "Favorite" else "Bookmarked",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = AniCataTextSecondary
                            )
                        }
                    }
                }
            }

            if (isSelected) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = AniCataTeal,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VaultSearchFilterBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String = "Search in archive...",
    itemCount: Int = 0,
    modifier: Modifier = Modifier
) {
    ArchiveSearchAndFilterRow(
        query = query,
        onQueryChange = onQueryChange,
        selectedCategoryIndex = 0,
        onCategorySelected = {},
        isGridView = true,
        onToggleGridView = {},
        modifier = modifier
    )
}

@Composable
fun EmptyArchiveBookmarkContent(
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = AniCataLightTeal,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = AniCataTeal,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AniCataTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = message,
            fontSize = 13.sp,
            color = AniCataTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

@Composable
fun EmptyArchiveFavoriteContent(
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = AniCataLightRose,
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = AniCataRose,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AniCataTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = message,
            fontSize = 13.sp,
            color = AniCataTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}

/* Legacy Bento Cards maintained for backward compatibility */

@Composable
fun ArchiveCategoryCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = "",
    badgeText: String = "",
    icon: ImageVector? = null,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit
) {
    val isAnime = title.contains("Anime", ignoreCase = true)
    val cardIcon = icon ?: if (isAnime) Icons.Default.Movie else Icons.AutoMirrored.Filled.MenuBook
    val defaultSubtitle = if (subtitle.isNotBlank()) subtitle else if (isAnime) "Explore bookmarked & favorite anime series" else "Explore bookmarked & favorite manga series"
    val defaultBadge = if (badgeText.isNotBlank()) badgeText else if (isAnime) "ANIME" else "MANGA"

    ElevatedCard(
        modifier = modifier
            .height(180.dp)
            .clickableSingle { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = AniCataTeal,
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = cardIcon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = defaultBadge,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = defaultSubtitle,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Open",
                                tint = AniCataTeal,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArchiveDetailCard(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = "",
    icon: ImageVector? = null,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit,
    overlayColor: Color
) {
    ArchiveCategoryCard(
        title = title,
        subtitle = subtitle,
        icon = icon,
        imageRes = imageRes,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun SmallArchiveHeaderCard(
    title: String,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .height(64.dp)
            .clickableSingle { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = AniCataLightTeal,
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = AniCataTeal,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    color = AniCataTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tap to switch category",
                    color = AniCataTextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
