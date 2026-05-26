package com.raihan.anicata.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBannerClick: (Int) -> Unit,
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit,
    onViewAllTopRatedClick: () -> Unit,
    onViewAllSeasonalClick: () -> Unit,
    onViewAllUpcomingClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val scrollState = rememberScrollState()

    val upcomingState by viewModel.upcomingState.collectAsState()
    val nowAiringState by viewModel.nowAiringState.collectAsState()
    val topRatedState by viewModel.topRatedState.collectAsState()
    val recentlyViewedState by viewModel.recentlyViewed.observeAsState(ResultWrapper.Idle())

    /*val bannerList = remember(nowAiringState) {
        if (nowAiringState is ResultWrapper.Success) {
            val payload = (nowAiringState as ResultWrapper.Success).payload ?: emptyList()

            payload.take(5).map { anime ->
                BannerData(
                    id = anime.id,
                    imageUrl = anime.images.jpg.largeImageUrl,
                    title = anime.title,
                    genres = anime.genres.joinToString(separator = ", ") { it.name.toString() }.ifEmpty { "N/A" },
                    synopsis = anime.synopsis ?: "No synopsis available.",
                    type = anime.type ?: "N/A"
                )
            }
        } else {
            emptyList()
        }
    }*/

    val bannerState = remember(nowAiringState) {
        when (nowAiringState) {
            is ResultWrapper.Loading -> ResultWrapper.Loading()
            is ResultWrapper.Error -> ResultWrapper.Error(nowAiringState.exception)
            is ResultWrapper.Empty -> ResultWrapper.Empty()
            is ResultWrapper.Success -> {
                val payload = (nowAiringState as ResultWrapper.Success).payload ?: emptyList()
                val banners = payload.take(5).map { anime ->
                    BannerData(
                        id = anime.id,
                        imageUrl = anime.images.jpg.largeImageUrl,
                        title = anime.title,
                        genres = anime.genres.joinToString(separator = ", ") { it.name.toString() }.ifEmpty { "N/A" },
                        synopsis = anime.synopsis ?: "No synopsis available.",
                        type = anime.type ?: "N/A"
                    )
                }
                if (banners.isEmpty()) ResultWrapper.Empty() else ResultWrapper.Success(banners)
            }
            is ResultWrapper.Idle -> ResultWrapper.Idle()
        }
    }

    val isAnyLoading = upcomingState is ResultWrapper.Loading ||
            nowAiringState is ResultWrapper.Loading ||
            topRatedState is ResultWrapper.Loading ||
            bannerState is ResultWrapper.Loading

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isAnyLoading) {
        if (!isAnyLoading) {
            isRefreshing = false
        }
    }

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.refreshData()
        },
        modifier = Modifier.fillMaxSize(),
        state = pullRefreshState,
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullRefreshState,
                containerColor = Color(0xFF2C2C2C), // Background Color
                color = Color(0xFFFF9800)           // Icon Color
            )
        }
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BannerSlider(
            state = bannerState,
            onBannerClick = onBannerClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        RecentlyViewedSection(
            state = recentlyViewedState,
            onItemClick = { id, type ->
                val typeLowerCase = type.lowercase()
                if (typeLowerCase == "manga" || typeLowerCase == "manhwa" || typeLowerCase == "manhua" || typeLowerCase == "novel" || typeLowerCase == "light novel" || typeLowerCase == "doujin") {
                    onMangaClick(id)
                } else {
                    onAnimeClick(id)
                }
            }
            )

        Spacer(modifier = Modifier.height(20.dp))

        NowAiringSection(
            state = nowAiringState,
            onViewAllClick = onViewAllSeasonalClick,
            onAnimeClick = onAnimeClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        UpcomingSection(
            state = upcomingState,
            onViewAllClick = onViewAllUpcomingClick,
            onAnimeClick = onAnimeClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        TopRatedSection(
            state = topRatedState,
            onViewAllClick = onViewAllTopRatedClick,
            onAnimeClick = onAnimeClick
        )

        Spacer(modifier = Modifier.height(120.dp))

    }
    }
}

