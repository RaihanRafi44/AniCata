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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raihan.anicata.ui.main.TopNavBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onBannerClick: () -> Unit // <-- TAMBAHKAN PARAMETER INI
) {
    // HomeScreen sekarang hanya berisi konten spesifiknya.
    // Tidak perlu lagi Scaffold, TopBar, atau Drawer.
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 🔥 Banner slider tampil di bagian atas
        //BannerSlider()
        BannerSlider(onBannerClick = onBannerClick)

        Spacer(modifier = Modifier.height(12.dp))

        // 🌟 Section "Recently Viewed" dari file RecentlyViewed.kt
        RecentlyViewedSection()

        Spacer(modifier = Modifier.height(20.dp))

        // 🌟 Section "Now Airing" dari file SeasonalAnimeNowAiring.kt
        NowAiringSection()

        Spacer(modifier = Modifier.height(20.dp))

        UpcomingSection()

        Spacer(modifier = Modifier.height(20.dp))

        TopRatedSection()

        Spacer(modifier = Modifier.height(20.dp))

        NewsUpdateScreen()

        // Beri ruang agar konten terakhir tidak tertutup FloatingBottomNavBar
        Spacer(modifier = Modifier.height(120.dp))
    }
}

