package com.raihan.anicata.ui.top.anime

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import com.raihan.anicata.ui.home.ModernPullToRefreshIndicator
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
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAnimeScreen(
    viewModel: TopAnimeViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val topAnimeState by viewModel.topAnimeState.collectAsState()

    var selectedFilter by rememberSaveable { mutableStateOf("") }
    var currentPage by rememberSaveable { mutableIntStateOf(1) }

    var totalPages by rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(topAnimeState) {
        when (val state = topAnimeState) {
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

    LaunchedEffect(topAnimeState) {
        if (topAnimeState is ResultWrapper.Loading) {
            isNewPageLoading = true
        } else if (topAnimeState is ResultWrapper.Success && isNewPageLoading) {
            isNewPageLoading = false
            listState.scrollToItem(0, 0)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getTopAnimeData(
            page = paginationState.currentPage,
            type = "",
            filter = selectedFilter,
            limit = 25
        )
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(topAnimeState) {
        if (topAnimeState !is ResultWrapper.Loading) {
            delay(250)
            isRefreshing = false
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.getTopAnimeData(
                page = paginationState.currentPage,
                type = "",
                filter = selectedFilter,
                limit = 25,
                forceRefresh = true
            )
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
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item(key = "filter_header") {
                Spacer(modifier = Modifier.height(16.dp))

                FilterTopAnime(
                    selectedFilterApiValue = selectedFilter,
                    onFilterSelected = { newApiValue ->
                        selectedFilter = newApiValue
                        paginationState.onPageChange(1)
                        viewModel.getTopAnimeData(
                            page = 1,
                            type = "",
                            filter = newApiValue,
                            limit = 25
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            when (val animatedState = topAnimeState) {
                is ResultWrapper.Loading, is ResultWrapper.Idle -> {
                    item(key = "shimmer_loading") {
                        TopAnimeShimmerLoading()
                    }
                }

                is ResultWrapper.Error -> {
                    item(key = "error_state") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = animatedState.exception?.message
                                    ?: "An error occurred, please try again",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                is ResultWrapper.Empty -> {
                    item(key = "empty_state") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No data",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                is ResultWrapper.Success -> {
                    val animeList = animatedState.payload?.first ?: emptyList()
                    val pageSize = 25
                    val offset = ((paginationState.currentPage - 1).coerceAtLeast(0)) * pageSize

                    items(
                        count = animeList.size,
                        key = { index -> animeList[index].id }
                    ) { index ->
                        val anime = animeList[index]
                        val rankedAnime = remember(anime, offset, index) {
                            anime.copy(rank = offset + index + 1)
                        }
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            AnimeCard(
                                item = rankedAnime,
                                onCLick = { onAnimeClick(rankedAnime.id) }
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
                                visiblePages = paginationState.visiblePages,
                                onPageChange = { newPage ->
                                    currentPage = newPage
                                    paginationState.onPageChange(newPage)
                                    viewModel.getTopAnimeData(
                                        page = newPage,
                                        type = "",
                                        filter = selectedFilter,
                                        limit = 25
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}