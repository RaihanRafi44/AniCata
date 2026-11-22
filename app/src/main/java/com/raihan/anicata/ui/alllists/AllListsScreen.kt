package com.raihan.anicata.ui.alllists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.rememberPaginationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit
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

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No data",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            }

            is ResultWrapper.Success -> {
                val mediaList = state.payload?.first ?: emptyList()

                items(
                    count = mediaList.size,
                    key = { index -> mediaList[index].id }
                ) { index ->
                    val media = mediaList[index]
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
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
