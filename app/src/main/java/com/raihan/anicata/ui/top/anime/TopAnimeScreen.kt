package com.raihan.anicata.ui.top.anime

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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

    val totalPages = when (val state = topAnimeState) {
        is ResultWrapper.Success -> state.payload?.second ?: 1
        is ResultWrapper.Empty -> state.payload?.second ?: 1
        else -> 1
    }

    val paginationState = rememberPaginationState(
        totalPages = totalPages,
        visiblePages = 3
    )

    LaunchedEffect(Unit) {
        viewModel.getTopAnimeData(
            page = paginationState.currentPage,
            type = "",
            filter = selectedFilter,
            limit = 25
        )
    }

    var isRefreshing by remember { mutableStateOf(false)}
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(topAnimeState) {
        if (topAnimeState !is ResultWrapper.Loading) {
            delay(250)
            isRefreshing = false
        }
    }

    //Box(modifier = Modifier.fillMaxSize()) {
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
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullRefreshState,
                containerColor = Color(0xFF2C2C2C),
                color = Color(0xFFFF9800)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
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

            Crossfade(
                targetState = topAnimeState,
                animationSpec = tween(durationMillis = 400),
                label = "StateTransition"
            ) { animatedState ->

                when (animatedState) {
                    is ResultWrapper.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ResultWrapper.Error -> {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(300.dp).padding(32.dp),
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

                    is ResultWrapper.Empty -> {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(300.dp).padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No data",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is ResultWrapper.Success -> {
                        val animeList = animatedState.payload?.first ?: emptyList()

                        Column {

                            AnimeListTopLayout(
                                animeList = animeList,
                                currentPage = paginationState.currentPage,
                                pageSize = 25,
                                modifier = Modifier,
                                onAnimeClick = onAnimeClick
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            if (paginationState.totalPages > 1) {
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

                    else -> {}
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}