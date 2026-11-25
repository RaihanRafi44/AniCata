package com.raihan.anicata.ui.detail.anime

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailAnimeScreen(
    animeId: Int,
    viewModel: DetailAnimeViewModel = koinViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = animeId) {
        viewModel.getAnimeDetails(animeId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Cast & Crew, 2: Related

    var showAddDialogBookmarkAnime by remember { mutableStateOf(false) }
    var showRemoveDialogBookmarkAnime by remember { mutableStateOf(false) }

    var showAddDialogFavoriteAnime by remember { mutableStateOf(false) }
    var showRemoveDialogFavoriteAnime by remember { mutableStateOf(false) }

    if (showAddDialogBookmarkAnime) {
        AniCataConfirmationDialog(
            title = "Simpan Bookmark",
            message = "Tambahkan anime ini ke daftar Bookmark Anda?",
            confirmText = "Simpan",
            icon = Icons.Filled.Bookmark,
            iconTint = Color(0xFF006A60),
            iconBgColor = Color(0xFFE0F2F1),
            confirmButtonColor = Color(0xFF006A60),
            onConfirm = {
                val animeData = (uiState.animeDetail as? ResultWrapper.Success)?.payload
                if (animeData != null) {
                    viewModel.saveToBookmark(animeData)
                }
                showAddDialogBookmarkAnime = false
            },
            onDismissRequest = { showAddDialogBookmarkAnime = false }
        )
    }

    if (showRemoveDialogBookmarkAnime) {
        AniCataConfirmationDialog(
            title = "Hapus Bookmark",
            message = "Anda yakin ingin menghapus anime ini dari daftar Bookmark?",
            confirmText = "Hapus",
            icon = Icons.Outlined.BookmarkBorder,
            iconTint = Color(0xFFDC2626),
            iconBgColor = Color(0xFFFEE2E2),
            confirmButtonColor = Color(0xFFDC2626),
            onConfirm = {
                viewModel.removeFromBookmark(animeId.toString())
                showRemoveDialogBookmarkAnime = false
            },
            onDismissRequest = { showRemoveDialogBookmarkAnime = false }
        )
    }

    if (showAddDialogFavoriteAnime) {
        AniCataConfirmationDialog(
            title = "Tambah Favorite",
            message = "Tambahkan anime ini ke daftar Favorite Anda?",
            confirmText = "Favoritkan",
            icon = Icons.Filled.Favorite,
            iconTint = Color(0xFFE11D48),
            iconBgColor = Color(0xFFFFE4E6),
            confirmButtonColor = Color(0xFF006A60),
            onConfirm = {
                val animeData = (uiState.animeDetail as? ResultWrapper.Success)?.payload
                if (animeData != null) {
                    viewModel.saveToFavorite(animeData)
                }
                showAddDialogFavoriteAnime = false
            },
            onDismissRequest = { showAddDialogFavoriteAnime = false }
        )
    }

    if (showRemoveDialogFavoriteAnime) {
        AniCataConfirmationDialog(
            title = "Hapus Favorite",
            message = "Anda yakin ingin menghapus anime ini dari daftar Favorite?",
            confirmText = "Hapus",
            icon = Icons.Outlined.FavoriteBorder,
            iconTint = Color(0xFFDC2626),
            iconBgColor = Color(0xFFFEE2E2),
            confirmButtonColor = Color(0xFFDC2626),
            onConfirm = {
                viewModel.removeFromFavorite(animeId.toString())
                showRemoveDialogFavoriteAnime = false
            },
            onDismissRequest = { showRemoveDialogFavoriteAnime = false }
        )
    }

    val canvasBg = Color(0xFFF4F6F6)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(canvasBg)
    ) {
        when (val detailResult = uiState.animeDetail) {
            is ResultWrapper.Loading -> {
                DetailShimmerLoading()
            }
            is ResultWrapper.Error -> {
                Text(
                    text = "Failed to load data: ${detailResult.exception?.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Success -> {
                val animeData = detailResult.payload!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Hero Header Showcase
                    HeaderSection(
                        animeData = animeData,
                        onBackClick = { navController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 2. Action Buttons Group (Bookmark & Favorite)
                    ActionButtonsGroup(
                        isBookmarked = isBookmarked,
                        isFavorite = isFavorite,
                        onBookmarkClick = {
                            if (!isBookmarked) {
                                showAddDialogBookmarkAnime = true
                            } else {
                                showRemoveDialogBookmarkAnime = true
                            }
                        },
                        onFavoriteClick = {
                            if (!isFavorite) {
                                showAddDialogFavoriteAnime = true
                            } else {
                                showRemoveDialogFavoriteAnime = true
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // 3. Segmented Custom Tab Bar
                    DetailTabBar(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // 4. Tab Contents with Crossfade Animation
                    Crossfade(targetState = selectedTab, label = "tab_crossfade") { tab ->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (tab) {
                                0 -> {
                                    // OVERVIEW TAB
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            AnimeInfoStats(animeData = animeData)
                                            Spacer(modifier = Modifier.height(12.dp))
                                            AnimeInfoDetail(animeData = animeData)
                                        }
                                    }

                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        SynopsisInfo(animeData = animeData)
                                    }

                                    if (!animeData.trailer?.youtubeId.isNullOrBlank()) {
                                        ElevatedCard(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 6.dp),
                                            shape = RoundedCornerShape(20.dp),
                                            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                        ) {
                                            VideoPromoSection(trailer = animeData.trailer)
                                        }
                                    }
                                }

                                1 -> {
                                    // CAST & CREW TAB
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        CharacterListLayout(charactersResult = uiState.animeCharacters)
                                    }

                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        StaffInfo(staffResult = uiState.animeStaff)
                                    }
                                }

                                2 -> {
                                    // RELATED MEDIA TAB
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        OtherAnimeInfo(
                                            relations = animeData.relations,
                                            onRelationClick = { id, type ->
                                                if (type.lowercase() == "manga" || type.lowercase() == "novel" || type.lowercase() == "light novel") {
                                                    navController.navigate(Screen.MangaDetail.createRoute(id))
                                                } else {
                                                    navController.navigate(Screen.Detail.createRoute(id))
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "Data anime tidak ditemukan.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Idle -> {}
        }
    }
}

@Composable
fun DetailTabBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val themeColor = Color(0xFF006A60)
    val tabs = listOf("Overview", "Cast & Crew", "Related")

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE0F2F1).copy(alpha = 0.55f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTab == index
                Surface(
                    onClick = { onTabSelected(index) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) themeColor else Color.Transparent,
                    contentColor = if (isSelected) Color.White else Color(0xFF191C1E),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailShimmerLoading(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color(0xFFE0E0E0),
        Color(0xFFF5F5F5),
        Color(0xFFE0E0E0),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F6))
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(brush)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(22.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(brush)
                        )
                    }
                }
            }
        }
    }
}
