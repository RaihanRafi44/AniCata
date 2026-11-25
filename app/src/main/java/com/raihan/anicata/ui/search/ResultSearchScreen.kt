package com.raihan.anicata.ui.search

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import com.raihan.anicata.ui.alllists.AllListsShimmerLoading
import com.raihan.anicata.ui.home.ModernPullToRefreshIndicator
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.rememberPaginationState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun ResultSearchScreen(
    searchQuery: String,
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit,
    viewModel: ResultSearchViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val paginationInfo = uiState.paginationInfo
    val result = uiState.result

    val paginationState = rememberPaginationState(
        totalPages = paginationInfo.totalPages,
        visiblePages = 3
    )

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = searchQuery) {
        viewModel.searchMedia(searchQuery)
    }

    LaunchedEffect(key1 = paginationInfo.currentPage) {
        paginationState.onPageChange(paginationInfo.currentPage)
        listState.scrollToItem(0, 0)
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()

    LaunchedEffect(result) {
        if (result !is ResultWrapper.Loading) {
            delay(250)
            isRefreshing = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.searchMedia(searchQuery)
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
                item(key = "search_header") {
                    Spacer(modifier = Modifier.height(16.dp))
                    ResultSearchHeader(searchQuery = searchQuery)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                when (result) {
                    is ResultWrapper.Loading, is ResultWrapper.Idle -> {
                        item(key = "shimmer_loading") {
                            AllListsShimmerLoading()
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
                                    text = result.exception?.message
                                        ?: "An error occurred, please try again",
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    is ResultWrapper.Empty -> {
                        item(key = "empty_state") {
                            EmptySearchResultContent(searchQuery = searchQuery)
                        }
                    }

                    is ResultWrapper.Success -> {
                        val mediaList = result.payload ?: emptyList()

                        if (mediaList.isEmpty()) {
                            item(key = "empty_payload") {
                                EmptySearchResultContent(searchQuery = searchQuery)
                            }
                        } else {
                            items(
                                count = mediaList.size,
                                key = { index -> mediaList[index].id }
                            ) { index ->
                                val media = mediaList[index]
                                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                                    SearchResultCard(
                                        item = media,
                                        onClick = {
                                            if (media.itemType.equals("TV", ignoreCase = true) ||
                                                media.itemType.equals("Movie", ignoreCase = true) ||
                                                media.itemType.equals("OVA", ignoreCase = true) ||
                                                media.itemType.equals("Special", ignoreCase = true) ||
                                                media.itemType.equals("ONA", ignoreCase = true) ||
                                                media.itemType.equals("Music", ignoreCase = true) ||
                                                media.itemType.equals("CM", ignoreCase = true) ||
                                                media.itemType.equals("PV", ignoreCase = true) ||
                                                media.itemType.equals("TV Special", ignoreCase = true)) {
                                                onAnimeClick(media.id)
                                            } else if (media.itemType.equals("Manga", ignoreCase = true) ||
                                                media.itemType.equals("Manhua", ignoreCase = true) ||
                                                media.itemType.equals("Manhwa", ignoreCase = true) ||
                                                media.itemType.equals("Novel", ignoreCase = true) ||
                                                media.itemType.equals("Doujin", ignoreCase = true) ||
                                                media.itemType.equals("Light Novel", ignoreCase = true) ||
                                                media.itemType.equals("One-shot", ignoreCase = true)) {
                                                onMangaClick(media.id)
                                            }
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
                                        visiblePages = paginationState.visiblePages,
                                        onPageChange = { newPage ->
                                            paginationState.onPageChange(newPage)
                                            viewModel.onPageChange(newPage)
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
}

@Composable
private fun EmptySearchResultContent(searchQuery: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFFFFFFF),
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = "No results",
                    tint = Color(0xFF006A60),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No Results Found",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF191C1E)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "No anime or manga matched the keyword \"$searchQuery\". Try checking the spelling or use different keywords.",
            fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}
