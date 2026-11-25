package com.raihan.anicata.ui.archive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.archive.anime.ArchiveAnimeViewModel
import com.raihan.anicata.ui.archive.manga.ArchiveMangaViewModel
import com.raihan.anicata.ui.home.ModernPullToRefreshIndicator
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

/**
 * Unified Modern Archive Screen aligned with AniCata Brand Design.
 * Highly optimized for 60/120fps scrolling and small device responsiveness.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnifiedArchiveScreen(
    modifier: Modifier = Modifier,
    animeViewModel: ArchiveAnimeViewModel = koinViewModel(),
    mangaViewModel: ArchiveMangaViewModel = koinViewModel(),
    onItemClick: (id: Int, isManga: Boolean) -> Unit = { _, _ -> },
    initialIsManga: Boolean = false,
    initialCategoryIndex: Int = 0 // 0 = All, 1 = Bookmarks, 2 = Favorites
) {
    LaunchedEffect(Unit) {
        animeViewModel.getBookmarks()
        animeViewModel.getFavorites()
        mangaViewModel.getBookmarksManga()
        mangaViewModel.getFavoritesManga()
    }

    // State Collections
    val animeBookmarkState by animeViewModel.bookmarkState.collectAsState()
    val animeFavoriteState by animeViewModel.favoriteState.collectAsState()
    val mangaBookmarkState by mangaViewModel.bookmarkMangaState.collectAsState()
    val mangaFavoriteState by mangaViewModel.favoriteMangaState.collectAsState()

    val animeSelectedIds by animeViewModel.selectedIds.collectAsState()
    val isAnimeSelectionMode by animeViewModel.isSelectionMode.collectAsState()

    val mangaSelectedIds by mangaViewModel.selectedId.collectAsState()
    val isMangaSelectionMode by mangaViewModel.isSelectionMode.collectAsState()

    var selectedIsManga by remember { mutableStateOf(initialIsManga) }
    var categoryIndex by remember { mutableIntStateOf(initialCategoryIndex) }
    var isGridView by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    val activeIsSelectionMode = if (selectedIsManga) isMangaSelectionMode else isAnimeSelectionMode
    val activeSelectedIds = if (selectedIsManga) mangaSelectedIds else animeSelectedIds

    LaunchedEffect(animeBookmarkState, animeFavoriteState, mangaBookmarkState, mangaFavoriteState) {
        delay(200)
        isRefreshing = false
    }

    // Remember mapped items to avoid object allocation churn on scroll
    val animeBookmarks = remember(animeBookmarkState) {
        (animeBookmarkState as? ResultWrapper.Success)?.payload?.map { it.toArchiveUiItem() } ?: emptyList()
    }
    val animeFavorites = remember(animeFavoriteState) {
        (animeFavoriteState as? ResultWrapper.Success)?.payload?.map { it.toArchiveUiItem() } ?: emptyList()
    }
    val mangaBookmarks = remember(mangaBookmarkState) {
        (mangaBookmarkState as? ResultWrapper.Success)?.payload?.map { it.toArchiveUiItem() } ?: emptyList()
    }
    val mangaFavorites = remember(mangaFavoriteState) {
        (mangaFavoriteState as? ResultWrapper.Success)?.payload?.map { it.toArchiveUiItem() } ?: emptyList()
    }

    val currentRawItems = remember(selectedIsManga, categoryIndex, animeBookmarks, animeFavorites, mangaBookmarks, mangaFavorites) {
        if (!selectedIsManga) {
            when (categoryIndex) {
                1 -> animeBookmarks
                2 -> animeFavorites
                else -> (animeBookmarks + animeFavorites).distinctBy { it.id }
            }
        } else {
            when (categoryIndex) {
                1 -> mangaBookmarks
                2 -> mangaFavorites
                else -> (mangaBookmarks + mangaFavorites).distinctBy { it.id }
            }
        }
    }

    val filteredItems = remember(currentRawItems, searchQuery) {
        if (searchQuery.isBlank()) currentRawItems else currentRawItems.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }

    val isAnyLoading = animeBookmarkState is ResultWrapper.Loading ||
            animeFavoriteState is ResultWrapper.Loading ||
            mangaBookmarkState is ResultWrapper.Loading ||
            mangaFavoriteState is ResultWrapper.Loading

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            if (activeIsSelectionMode) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "${activeSelectedIds.size} Selected",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = AniCataTextPrimary
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White),
                    windowInsets = WindowInsets(0, 0, 0, 0),
                    navigationIcon = {
                        IconButton(onClick = {
                            if (selectedIsManga) mangaViewModel.clearSelection() else animeViewModel.clearSelection()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel",
                                tint = AniCataTextPrimary
                            )
                        }
                    },
                    actions = {
                        if (activeSelectedIds.isNotEmpty()) {
                            IconButton(onClick = {
                                if (selectedIsManga) {
                                    mangaViewModel.deleteSelectedBookmarks()
                                    mangaViewModel.deleteSelectedFavorites()
                                } else {
                                    animeViewModel.deleteSelectedBookmarks()
                                    animeViewModel.deleteSelectedFavorites()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                animeViewModel.getBookmarks()
                animeViewModel.getFavorites()
                mangaViewModel.getBookmarksManga()
                mangaViewModel.getFavoritesManga()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pullRefreshState,
            indicator = {
                ModernPullToRefreshIndicator(
                    isRefreshing = isRefreshing,
                    state = pullRefreshState
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                if (!activeIsSelectionMode) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Streamlined Top Header with Title & Count
                    ArchiveCompactTopHeader(
                        totalCount = currentRawItems.size
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Full-width Media Switcher (Anime / Manga)
                    ArchiveMediaSegmentedControl(
                        selectedIsManga = selectedIsManga,
                        onTabSelected = { isManga ->
                            selectedIsManga = isManga
                            if (isManga) mangaViewModel.clearSelection() else animeViewModel.clearSelection()
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Streamlined Row with Search, Category Pills (All, Bookmarks, Favorites), and View Toggle
                    ArchiveSearchAndFilterRow(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        selectedCategoryIndex = categoryIndex,
                        onCategorySelected = { categoryIndex = it },
                        isGridView = isGridView,
                        onToggleGridView = { isGridView = it }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Content Display Area
                if (isAnyLoading && currentRawItems.isEmpty()) {
                    ArchiveShimmerLoading()
                } else if (currentRawItems.isEmpty()) {
                    if (categoryIndex == 2) {
                        EmptyArchiveFavoriteContent(
                            title = if (selectedIsManga) "No Favorite Manga" else "No Favorite Anime",
                            message = "You haven't added any titles to your favorites list yet."
                        )
                    } else {
                        EmptyArchiveBookmarkContent(
                            title = if (selectedIsManga) "No Bookmarked Manga" else "No Bookmarked Anime",
                            message = "You haven't added any titles to your bookmark list yet."
                        )
                    }
                } else if (filteredItems.isEmpty()) {
                    EmptyArchiveBookmarkContent(
                        title = "No Matching Titles",
                        message = "No saved item matches \"$searchQuery\"."
                    )
                } else {
                    if (isGridView) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(bottom = 130.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = filteredItems,
                                key = { it.id }
                            ) { item ->
                                val isSelected = activeSelectedIds.contains(item.id)
                                ArchiveGridCard(
                                    item = item,
                                    isSelected = isSelected,
                                    isSelectionMode = activeIsSelectionMode,
                                    onClick = {
                                        if (activeIsSelectionMode) {
                                            if (selectedIsManga) mangaViewModel.toggleSelection(item.id)
                                            else animeViewModel.toggleSelection(item.id)
                                        } else {
                                            val idInt = item.id.toIntOrNull() ?: 0
                                            onItemClick(idInt, item.isManga)
                                        }
                                    },
                                    onLongClick = {
                                        if (!activeIsSelectionMode) {
                                            if (selectedIsManga) mangaViewModel.startSelection(item.id)
                                            else animeViewModel.startSelection(item.id)
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 160.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                items = filteredItems,
                                key = { it.id }
                            ) { item ->
                                val isSelected = activeSelectedIds.contains(item.id)
                                ArchiveListCard(
                                    item = item,
                                    isSelected = isSelected,
                                    isSelectionMode = activeIsSelectionMode,
                                    onClick = {
                                        if (activeIsSelectionMode) {
                                            if (selectedIsManga) mangaViewModel.toggleSelection(item.id)
                                            else animeViewModel.toggleSelection(item.id)
                                        } else {
                                            val idInt = item.id.toIntOrNull() ?: 0
                                            onItemClick(idInt, item.isManga)
                                        }
                                    },
                                    onLongClick = {
                                        if (!activeIsSelectionMode) {
                                            if (selectedIsManga) mangaViewModel.startSelection(item.id)
                                            else animeViewModel.startSelection(item.id)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Legacy Layout wrapper maintained for backward compatibility.
 */
@Composable
fun ArchiveMainLayout(
    onAnimeClicked: () -> Unit,
    onMangaClicked: () -> Unit
) {
    UnifiedArchiveScreen(
        onItemClick = { _, isManga ->
            if (isManga) onMangaClicked() else onAnimeClicked()
        }
    )
}
