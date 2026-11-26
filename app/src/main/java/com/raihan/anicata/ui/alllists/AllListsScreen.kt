package com.raihan.anicata.ui.alllists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import com.raihan.anicata.ui.home.ModernPullToRefreshIndicator
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.rememberPaginationState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit,
    scrollToTopTrigger: Int = 0,
    scrollToBottomTrigger: Int = 0
) {
    val uiState by viewModel.uiState.collectAsState()

    var totalPages by rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(uiState.mediaState) {
        when (val state = uiState.mediaState) {
            is ResultWrapper.Success -> totalPages = state.payload?.second ?: totalPages
            is ResultWrapper.Empty -> totalPages = state.payload?.second ?: totalPages
            else -> {}
        }
    }

    val paginationState = rememberPaginationState(
        totalPages = totalPages,
        visiblePages = 3
    )

    val listState = rememberLazyListState()
    var isNewPageLoading by remember { mutableStateOf(false) }

    LaunchedEffect(scrollToTopTrigger) {
        if (scrollToTopTrigger > 0) {
            listState.scrollToItem(0, 0)
        }
    }

    LaunchedEffect(scrollToBottomTrigger) {
        if (scrollToBottomTrigger > 0) {
            val totalItems = listState.layoutInfo.totalItemsCount
            if (totalItems > 0) {
                listState.scrollToItem(totalItems - 1)
            }
        }
    }

    LaunchedEffect(uiState.mediaState) {
        if (uiState.mediaState is ResultWrapper.Loading) {
            isNewPageLoading = true
        } else if (uiState.mediaState is ResultWrapper.Success && isNewPageLoading) {
            isNewPageLoading = false
            listState.scrollToItem(0, 0)
        }
    }

    LaunchedEffect(paginationState.currentPage) {
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchMediaPage(paginationState.currentPage)
        }
    }

    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(uiState.mediaState) {
        if (uiState.mediaState !is ResultWrapper.Loading) {
            delay(250)
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.applyFilters()
        },
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        indicator = {
            ModernPullToRefreshIndicator(
                isRefreshing = isRefreshing,
                state = pullRefreshState
            )
        }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item(key = "filter_header") {
                AllListsFilterGroup(
                    uiState = uiState,
                    onCategoryChanged = viewModel::updateCategory,
                    onSortChanged = viewModel::updateSortFilter,
                    onTypeChanged = viewModel::updateTypeFilter,
                    onGenreChanged = viewModel::updateGenreFilter,
                    onThemeChanged = viewModel::updateThemeFilter,
                    onTargetChanged = viewModel::updateTargetFilter,
                    onUpdateFilterClicked = viewModel::applyFilters
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            when (val state = uiState.mediaState) {
                is ResultWrapper.Loading, is ResultWrapper.Idle -> {
                    item(key = "shimmer_loading") {
                        AllListsShimmerLoading()
                    }
                }

                is ResultWrapper.Error -> {
                    item(key = "error_state") {
                        ErrorStateContent(
                            errorMessage = state.exception?.message ?: "Failed load data",
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }
                }

                is ResultWrapper.Empty -> {
                    item(key = "empty_state") {
                        EmptyStateFilterContent(
                            onResetFilters = {
                                viewModel.updateTypeFilter("All")
                                viewModel.updateGenreFilter("All")
                                viewModel.updateTargetFilter("All")
                                viewModel.updateThemeFilter("All")
                                viewModel.applyFilters()
                            }
                        )
                    }
                }

                is ResultWrapper.Success -> {
                    val mediaList = state.payload?.first ?: emptyList()

                    items(
                        count = mediaList.size,
                        key = { index -> mediaList[index].id }
                    ) { index ->
                        val media = mediaList[index]
                        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)) {
                            MediaItemCard(
                                item = media,
                                onClick = {
                                    if (uiState.selectedCategory == "Anime") onAnimeClick(media.id)
                                    if (uiState.selectedCategory == "Manga") onMangaClick(media.id)
                                }
                            )
                        }
                    }

                    if (paginationState.totalPages > 1) {
                        item(key = "pagination_controls") {
                            Spacer(modifier = Modifier.height(16.dp))
                            PaginationControls(
                                currentPage = paginationState.currentPage,
                                startPage = paginationState.startPage,
                                totalPages = paginationState.totalPages,
                                onPageChange = { newPage ->
                                    paginationState.onPageChange(newPage)
                                    viewModel.fetchMediaPage(newPage)
                                },
                                visiblePages = paginationState.visiblePages
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateFilterContent(
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColor = Color(0xFF006A60)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = themeColor.copy(alpha = 0.1f),
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.SearchOff,
                        contentDescription = "No Results",
                        tint = themeColor,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "No Results Found",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF191C1E)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Try adjusting or resetting your filter criteria",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onResetFilters,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = themeColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Reset All Filters",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
