package com.raihan.anicata.ui.detail.manga

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.raihan.anicata.ui.detail.anime.ActionButtonsGroup
import com.raihan.anicata.ui.detail.anime.CharacterListLayout
import com.raihan.anicata.ui.detail.anime.DetailShimmerLoading
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailMangaScreen(
    mangaId: Int,
    viewModel: DetailMangaViewModel = koinViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = mangaId) {
        viewModel.getMangaDetails(mangaId)
    }

    val uiState by viewModel.uiState.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Overview, 1: Characters, 2: Related

    var showAddDialogBookmarkManga by remember { mutableStateOf(false) }
    var showRemoveDialogBookmarkManga by remember { mutableStateOf(false) }

    var showAddDialogFavoriteManga by remember { mutableStateOf(false) }
    var showRemoveDialogFavoriteManga by remember { mutableStateOf(false) }

    if (showAddDialogBookmarkManga) {
        AlertDialog(
            onDismissRequest = { showAddDialogBookmarkManga = false },
            title = { Text(text = "Konfirmasi") },
            text = { Text(text = "Apakah Anda yakin ingin menambahkan manga ini ke Bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val mangaData = (uiState.mangaDetail as? ResultWrapper.Success)?.payload
                        if (mangaData != null) {
                            viewModel.saveToBookmark(mangaData)
                        }
                        showAddDialogBookmarkManga = false
                    }
                ) {
                    Text("Ya, Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogBookmarkManga = false }) { Text("Batal") }
            }
        )
    }

    if (showRemoveDialogBookmarkManga) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogBookmarkManga = false },
            title = { Text("Hapus Bookmark") },
            text = { Text("Anda yakin ingin menghapus manga ini dari bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.removeFromBookmark(mangaId.toString())
                        showRemoveDialogBookmarkManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogBookmarkManga = false }) { Text("Batal") }
            }
        )
    }

    if (showAddDialogFavoriteManga) {
        AlertDialog(
            onDismissRequest = { showAddDialogFavoriteManga = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menambahkan manga ini ke Favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val mangaData = (uiState.mangaDetail as? ResultWrapper.Success)?.payload
                        if (mangaData != null) {
                            viewModel.saveToFavorite(mangaData)
                        }
                        showAddDialogFavoriteManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Ya, Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogFavoriteManga = false }) { Text("Batal") }
            }
        )
    }

    if (showRemoveDialogFavoriteManga) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogFavoriteManga = false },
            title = { Text("Hapus Favorite") },
            text = { Text("Anda yakin ingin menghapus manga ini dari favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.removeFromFavorite(mangaId.toString())
                        showRemoveDialogFavoriteManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogFavoriteManga = false }) { Text("Batal") }
            }
        )
    }

    val canvasBg = Color(0xFFF4F6F6)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(canvasBg)
    ) {
        when (val detailResult = uiState.mangaDetail) {
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
                val mangaData = detailResult.payload!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1. Hero Header Showcase
                    MangaHeaderSection(
                        mangaData = mangaData,
                        onBackClick = { navController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 2. Action Buttons Group (Bookmark & Favorite)
                    ActionButtonsGroup(
                        isBookmarked = isBookmarked,
                        isFavorite = isFavorite,
                        onBookmarkClick = {
                            if (!isBookmarked) {
                                showAddDialogBookmarkManga = true
                            } else {
                                showRemoveDialogBookmarkManga = true
                            }
                        },
                        onFavoriteClick = {
                            if (!isFavorite) {
                                showAddDialogFavoriteManga = true
                            } else {
                                showRemoveDialogFavoriteManga = true
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // 3. Segmented Custom Tab Bar
                    MangaDetailTabBar(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // 4. Tab Contents with Crossfade Animation
                    Crossfade(targetState = selectedTab, label = "manga_tab_crossfade") { tab ->
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
                                            MangaInfoStats(mangaData = mangaData)
                                            Spacer(modifier = Modifier.height(12.dp))
                                            MangaInfoDetail(mangaData = mangaData)
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
                                        MangaSynopsisInfo(mangaData = mangaData)
                                    }
                                }

                                1 -> {
                                    // CHARACTERS TAB
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 6.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                                    ) {
                                        CharacterListLayout(charactersResult = uiState.mangaCharacters)
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
                                        OtherMangaInfo(
                                            relations = mangaData.relations,
                                            onRelationClick = { id, type ->
                                                if (type.lowercase() == "anime" || type.lowercase() == "ova" || type.lowercase() == "movie") {
                                                    navController.navigate(Screen.Detail.createRoute(id))
                                                } else {
                                                    navController.navigate(Screen.MangaDetail.createRoute(id))
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
                    text = "Data manga tidak ditemukan.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Idle -> {}
        }
    }
}

@Composable
fun MangaDetailTabBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val themeColor = Color(0xFF006A60)
    val tabs = listOf("Overview", "Characters", "Related")

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
