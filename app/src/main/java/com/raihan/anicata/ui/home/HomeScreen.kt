package com.raihan.anicata.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raihan.anicata.ui.main.TopNavBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    //onBannerClick: () -> Unit, // <-- TAMBAHKAN PARAMETER INI
    // Parameter navigasi baru
    onBannerClick: (Int) -> Unit,
    onAnimeClick: (Int) -> Unit,
    onViewAllTopRatedClick: () -> Unit,
    onViewAllSeasonalClick: () -> Unit,
    onViewAllUpcomingClick: () -> Unit,
    // Injeksi ViewModel
    viewModel: HomeViewModel = koinViewModel()
) {
    // HomeScreen sekarang hanya berisi konten spesifiknya.
    // Tidak perlu lagi Scaffold, TopBar, atau Drawer.
    val scrollState = rememberScrollState()

    // Kumpulkan state dari HomeViewModel
    val topRatedList by viewModel.topRatedAnime.collectAsState()
    val isLoadingTopRated by viewModel.isLoading.collectAsState()
    val errorTopRated by viewModel.error.collectAsState()

    // 2. Kumpulkan state BARU Now Airing
    val nowAiringList by viewModel.nowAiringAnime.collectAsState()
    val isLoadingNowAiring by viewModel.isLoadingNowAiring.collectAsState()
    val errorNowAiring by viewModel.errorNowAiring.collectAsState()

    // 2. Kumpulkan state BARU Upcoming
    val upcomingList by viewModel.upcomingAnime.collectAsState()
    val isLoadingUpcoming by viewModel.isLoadingUpcoming.collectAsState()
    val errorUpcoming by viewModel.errorUpcoming.collectAsState()

    // --- 2. BUAT LIST BANNER DI SINI ---
    // Ambil 5 teratas dari Now Airing dan map ke BannerData
    val bannerList = nowAiringList.take(5).map { anime ->
        BannerData(
            id = anime.id,
            imageUrl = anime.images.jpg.largeImageUrl,
            title = anime.title,
            // Gabungkan list genre menjadi satu String
            genres = anime.genres.joinToString(separator = ", ") { it.name.toString() }
                .ifEmpty { "N/A" },
            synopsis = anime.synopsis ?: "No synopsis available.",
            type = anime.type ?: "N/A"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 🔥 Banner slider tampil di bagian atas
        //BannerSlider()
        //BannerSlider(onBannerClick = onBannerClick)
        BannerSlider(
            banners = bannerList,
            onBannerClick = onBannerClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🌟 Section "Recently Viewed" dari file RecentlyViewed.kt
        RecentlyViewedSection()

        Spacer(modifier = Modifier.height(20.dp))

        // 🌟 Section "Now Airing" dari file SeasonalAnimeNowAiring.kt
        //NowAiringSection()
        NowAiringSection(
            animeList = nowAiringList,
            isLoading = isLoadingNowAiring,
            error = errorNowAiring,
            onViewAllClick = onViewAllSeasonalClick, // Navigasi ke SeasonalScreen
            onAnimeClick = onAnimeClick              // Navigasi ke DetailScreen
        )

        Spacer(modifier = Modifier.height(20.dp))

        //UpcomingSection()
        UpcomingSection(
            animeList = upcomingList,
            isLoading = isLoadingUpcoming,
            error = errorUpcoming,
            onViewAllClick = onViewAllUpcomingClick, // Navigasi ke SeasonalScreen
            onAnimeClick = onAnimeClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        //TopRatedSection()
        TopRatedSection(
            animeList = topRatedList,
            isLoading = isLoadingTopRated,
            error = errorTopRated,
            onViewAllClick = onViewAllTopRatedClick, // Navigasi ke TopAnimeScreen
            onAnimeClick = onAnimeClick      // Navigasi ke DetailScreen
        )

        Spacer(modifier = Modifier.height(20.dp))

        NewsUpdateScreen()

        // Beri ruang agar konten terakhir tidak tertutup FloatingBottomNavBar
        Spacer(modifier = Modifier.height(120.dp))
    }
}

