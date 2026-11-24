package com.raihan.anicata.ui.seasonalanime

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeasonalScreen(
    viewModel: SeasonalViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AnimeFilterGroup(
                selectedYear = uiState.selectedYear,
                onYearChange = viewModel::onYearChange,
                yearOptions = uiState.yearOptions,
                selectedSeason = uiState.selectedSeason,
                onSeasonChange = viewModel::onSeasonChange,
                seasonOptions = uiState.seasonOptions,

                selectedType = uiState.selectedType,
                onTypeChange = viewModel::onTypeChange,
                typeOptions = uiState.typeOptions,
                selectedStatus = uiState.selectedStatus,
                onStatusChange = viewModel::onStatusChange,
                statusOptions = uiState.statusOptions,

                selectedSort = uiState.selectedSort,
                onSortChange = viewModel::onSortChange,
                sortOptions = uiState.sortOptions,

                itemCount = uiState.totalShowsCount,
                onUpdateClick = viewModel::onUpdateFilter
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (val apiResult = uiState.apiResult) {
                is ResultWrapper.Loading -> {
                    SeasonalShimmerLoading()
                }
                is ResultWrapper.Success -> {
                    val animeList = apiResult.payload ?: emptyList()
                    if (animeList.isNotEmpty()) {
                        AnimeListLayout(
                            animeList = animeList,
                            onAnimeClick = onAnimeClick,
                            hasNextPage = uiState.hasNextPage,
                            isNextPageLoading = uiState.isNextPageLoading,
                            onLoadMoreClick = viewModel::loadNextPage
                        )
                    } else {
                        SeasonalEmptyState()
                    }
                }
                is ResultWrapper.Error -> {
                    val errorMessage = apiResult.message ?: apiResult.exception?.message ?: "Unknown error"
                    SeasonalErrorState(
                        message = errorMessage,
                        onRetry = viewModel::onUpdateFilter
                    )
                }
                is ResultWrapper.Empty -> {
                    SeasonalEmptyState()
                }
                is ResultWrapper.Idle -> {}
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
