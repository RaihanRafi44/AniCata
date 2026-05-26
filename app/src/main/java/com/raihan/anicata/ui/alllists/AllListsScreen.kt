package com.raihan.anicata.ui.alllists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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

    val totalPages = when (val state = uiState.mediaState) {
        is ResultWrapper.Success -> state.payload?.second ?: 1
        is ResultWrapper.Empty -> state.payload?.second ?: 1
        else -> 1
    }

    val paginationState = rememberPaginationState(
        //initialPage = 1,
        totalPages = totalPages,
        visiblePages = 3
    )

    LaunchedEffect(paginationState.currentPage) {
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchMediaPage(paginationState.currentPage) // GANTI: fetchAnimePage -> fetchMediaPage
        }
    }

    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter
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

            when (val state = uiState.mediaState) {
                is ResultWrapper.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ResultWrapper.Error -> {
                    ErrorStateContent(
                        errorMessage = state.exception?.message ?: "Failed load data",
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
                is ResultWrapper.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
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
                is ResultWrapper.Success -> {
                    val mediaList = state.payload?.first ?: emptyList()

                    MediaListLayout(
                        mediaList = mediaList,
                        onMediaClick = { mediaId ->
                            if (uiState.selectedCategory == "Anime") onAnimeClick(mediaId)
                            if (uiState.selectedCategory == "Manga") onMangaClick(mediaId)
                        }
                    )

                    if (paginationState.totalPages > 1) {
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
                is ResultWrapper.Idle -> {}
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
