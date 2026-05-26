package com.raihan.anicata.ui.top.manga

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
fun TopMangaScreen(
    viewModel: TopMangaViewModel = koinViewModel(),
    onMangaClick: (Int) -> Unit
) {
    val topMangaState by viewModel.topMangaState.collectAsState()

    var selectedFilter by rememberSaveable { mutableStateOf("") }
    var currentPage by rememberSaveable { mutableIntStateOf(1) }

    val totalPages = when (val state = topMangaState) {
        is ResultWrapper.Success -> state.payload?.second ?: 1
        is ResultWrapper.Empty -> state.payload?.second ?: 1
        else -> 1
    }

    val paginationState = rememberPaginationState(
        totalPages = totalPages,
        visiblePages = 3
    )

    LaunchedEffect(Unit) {
        viewModel.getTopMangaData(
            page = paginationState.currentPage,
            type = "",
            filter = selectedFilter,
            limit = 25
        )
    }

    var isRefreshingManga by remember { mutableStateOf(false)}
    val pullRefreshStateManga = rememberPullToRefreshState()

    // Deactivate refresh indicator if data finish loaded
    LaunchedEffect(topMangaState) {
        if (topMangaState !is ResultWrapper.Loading) {
            delay(250)
            isRefreshingManga = false
        }
    }


        //Box(modifier = Modifier.fillMaxSize()) {
    PullToRefreshBox(
        isRefreshing = isRefreshingManga,
        onRefresh = {
            isRefreshingManga = true
            viewModel.getTopMangaData(
                page = paginationState.currentPage,
                type = "",
                filter = selectedFilter,
                limit = 25,
                forceRefreshing = true
            )
        },
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshStateManga,
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshingManga,
                state = pullRefreshStateManga,
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

                FilterTopManga(
                    selectedFilterApiValue = selectedFilter,
                    onFilterSelected = { newApiValue ->
                        selectedFilter = newApiValue
                        paginationState.onPageChange(1)
                        viewModel.getTopMangaData(
                            page = 1,
                            type = "",
                            filter = newApiValue,
                            limit = 25
                        )
                    }
                )

                Crossfade(
                    targetState = topMangaState,
                    animationSpec = tween(durationMillis = 400),
                    label = "StateTransitionTopManga"
                ) { animatedStateTopManga ->

                when (animatedStateTopManga) {
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
                                text = animatedStateTopManga.exception?.message
                                    ?: "An error occurred, please try again",
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    is ResultWrapper.Success -> {
                        val mangaList = animatedStateTopManga.payload?.first ?: emptyList()

                        Column {
                            MangaListTopLayout(
                                mangaList = mangaList,
                                currentPage = paginationState.currentPage,
                                pageSize = 25,
                                modifier = Modifier,
                                onMangaClick = onMangaClick
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
                                        viewModel.getTopMangaData(
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

