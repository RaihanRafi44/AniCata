package com.raihan.anicata.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raihan.anicata.data.model.auth.UserData
import com.raihan.anicata.ui.alllists.AllListsScreen
import com.raihan.anicata.ui.archive.ArchiveMainScreen
import com.raihan.anicata.ui.archive.anime.ArchiveAnimeScreen
import com.raihan.anicata.ui.archive.anime.ArchiveAnimeScreenBookmark
import com.raihan.anicata.ui.archive.anime.ArchiveAnimeScreenFavorite
import com.raihan.anicata.ui.archive.manga.ArchiveMangaScreen
import com.raihan.anicata.ui.archive.manga.ArchiveMangaScreenBookmark
import com.raihan.anicata.ui.archive.manga.ArchiveMangaScreenFavorite
import com.raihan.anicata.ui.home.HomeScreen
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.ui.profile.ProfileScreen
import com.raihan.anicata.ui.search.ResultSearchScreen
import com.raihan.anicata.ui.search.SearchScreenLayout
import com.raihan.anicata.ui.seasonalanime.SeasonalScreen
import com.raihan.anicata.ui.top.anime.TopAnimeScreen
import com.raihan.anicata.ui.top.manga.TopMangaScreen
import com.raihan.anicata.ui.top.novel.TopNovelScreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController, // Ini NavController dari MainActivity untuk navigasi level atas
    userData: UserData?,
    onSignOut: () -> Unit
) {
    // NavController ini HANYA untuk navigasi internal (Home, Archive, Profile)
    val internalNavController = rememberNavController()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    val navBackStackEntry by internalNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val topBarColor = Color(0xFFE0F2F1)
    val systemUiController = rememberSystemUiController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    // State untuk mengontrol visibilitas search screen
    var isSearchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    LaunchedEffect(currentRoute) {
        // PERBAIKAN KECIL:
        // Jika rute saat ini adalah result_search, jangan ubah selectedItem
        if (currentRoute?.startsWith("result_search") == false) {
            when (currentRoute) {
                "home" -> selectedItem = 0
                "archive" -> selectedItem = 1
                "profile" -> selectedItem = 2
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.6f),
                drawerContainerColor = Color(0xFFE0F2F1)
            ) {
                AppDrawerContent(
                    userData = userData,
                    navController = internalNavController, // Gunakan internalNavController untuk drawer
                    scope = scope,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    onSignOut = onSignOut
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopNavBar(
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    },
                    onSearchClick = { isSearchVisible = true },
                    onSettingsClick = { /* TODO */ },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                NavHost(
                    navController = internalNavController, // NavHost ini pakai controller internal
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("home") {
                        HomeScreen(
                            onBannerClick = { animeId ->
                                // Saat banner diklik, gunakan controller utama untuk navigasi

                                navController.navigate(Screen.Detail.createRoute(animeId))
                            },
                            // ✅ 1. Sambungkan navigasi klik card
                            onAnimeClick = { animeId ->
                                // Gunakan NavController UTAMA (dari MainActivity)
                                // untuk pindah ke rute Detail
                                navController.navigate(Screen.Detail.createRoute(animeId))
                            },
                            // ✅ 2. Sambungkan navigasi klik panah "View All"
                            onViewAllTopRatedClick = {
                                // Gunakan NavController INTERNAL (dari MainScreen)
                                // untuk pindah ke rute Top Anime
                                internalNavController.navigate("top_anime")
                            },
                            onViewAllSeasonalClick = {
                                internalNavController.navigate("seasonal")
                            },
                            onViewAllUpcomingClick = {
                                internalNavController.navigate("seasonal")
                            }
                        )
                    }
                    composable("archive") {
                        ArchiveMainScreen(
                            onNavigateToAnime = {
                                internalNavController.navigate("archive_anime")
                            },
                            onNavigateToManga = {
                                internalNavController.navigate("archive_manga")
                            }
                        )
                    }

                    composable("archive_anime") {
                        ArchiveAnimeScreen(
                            onNavigateBack = { internalNavController.popBackStack() },
                            onBookmarkClicked = {
                                internalNavController.navigate("archive_anime_bookmark")
                            },
                            onFavoriteClicked = {
                                internalNavController.navigate("archive_anime_favorite")
                            }
                        )
                    }

                    composable("archive_manga") {
                        ArchiveMangaScreen(
                            onNavigateBack = { internalNavController.popBackStack() },
                            onBookmarkClicked = {
                                internalNavController.navigate("archive_manga_bookmark")
                            },
                            onFavoriteClicked = {
                                internalNavController.navigate("archive_manga_favorite")
                            }
                        )
                    }

                    composable("archive_anime_bookmark") {
                        ArchiveAnimeScreenBookmark(
                            onAnimeClick = { animeId ->
                                navController.navigate("detail/$animeId")
                            },
                            onNavigateBack = {
                                internalNavController.popBackStack()
                            },
                            onDeleteClick = {
                            },
                        )
                    }

                    composable("archive_anime_favorite") {
                        ArchiveAnimeScreenFavorite(
                            //animeList = dummyList,
                            onAnimeClick = { animeId ->
                                // Navigasi ke Detail Anime
                                navController.navigate("detail/$animeId")
                            },
                            onNavigateBack = {
                                // Kembali ke Archive Anime (Rute B)
                                internalNavController.popBackStack()
                            },
                            onDeleteClick = {}
                        )
                    }

                    composable("archive_manga_bookmark") {
                        // *** CATATAN: Ganti dummyList dengan data nyata dari ViewModel Anda ***


                        ArchiveMangaScreenBookmark(
                            onMangaClick = { mangaId ->
                                navController.navigate("detail/$mangaId")
                            },
                            onNavigateBack = {
                                internalNavController.popBackStack()
                            },
                            onDeleteClick = {}
                        )
                    }

                    composable("archive_manga_favorite") {

                        ArchiveMangaScreenFavorite(
                            onMangaClick = { mangaId ->
                                navController.navigate("detail/$mangaId")
                            },
                            onNavigateBack = {
                                internalNavController.popBackStack()
                            },
                            onDeleteClick = {}
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            userData = userData,
                            onSignOut = onSignOut
                        )
                    }
                    // ✅ 2. Tambahkan rute baru untuk SeasonalScreen
                    composable("seasonal") {
                        SeasonalScreen(
                            onAnimeClick = { animeId ->
                                navController.navigate(Screen.Detail.createRoute(animeId))
                            }
                        )
                    }

                    composable("top_anime") {
                        TopAnimeScreen(
                            // Tambahkan parameter onAnimeClick
                            onAnimeClick = { animeId ->
                                // Saat item diklik, gunakan NavController UTAMA
                                // untuk pindah ke rute Detail
                                navController.navigate(Screen.Detail.createRoute(animeId))
                            }
                        )
                    }
                    composable("top_manga") {
                        TopMangaScreen(
                            onMangaClick = { mangaId ->
                                navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                            }
                        )
                    }

                    composable("all_lists") {
                        //AllListsScreen()
                        AllListsScreen(
                            onAnimeClick = { animeId ->
                                navController.navigate(Screen.Detail.createRoute(animeId))
                            },
                            onMangaClick = { mangaId ->
                                navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                            }
                        )
                    }

                    // --- 1. TAMBAHKAN RUTE BARU DI SINI ---
                    composable(
                        route = "result_search/{query}",
                        arguments = listOf(navArgument("query") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val query = backStackEntry.arguments?.getString("query") ?: ""
                        ResultSearchScreen(
                            searchQuery = query,
                            onAnimeClick = { animeId ->
                                navController.navigate(Screen.Detail.createRoute(animeId))
                            },
                            onMangaClick = { mangaId ->
                                navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                            }
                        )
                    }

                }

                FloatingBottomNavBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp),
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        selectedItem = index
                        val route = when (index) {
                            0 -> "home"
                            1 -> "archive"
                            2 -> "profile"
                            else -> "home"
                        }
                        internalNavController.navigate(route) {
                            popUpTo(internalNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

        // Tampilkan overlay jika isSearchVisible adalah true
        if (isSearchVisible) {
            // Latar belakang gelap semi-transparan
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 1.2f))
                    // Klik di luar area pencarian akan menutup overlay
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // Menghilangkan efek ripple
                    ) {
                        isSearchVisible = false
                    }
            )

            // Konten Search Screen di atas latar belakang gelap
            Box(
                // Memberi padding agar tidak tertimpa status bar
                modifier = Modifier.statusBarsPadding()
            ) {
                SearchScreenLayout(
                    onClose = { isSearchVisible = false }, // Tombol close akan menutup overlay
                    // Tambahkan parameter onSearchSubmitted yang hilang
                    onSearchSubmitted = { query ->
                        // 1. Tutup overlay pencarian
                        isSearchVisible = false
                        /*// 2. Gunakan internalNavController, BUKAN navController
                        internalNavController.navigate("result_search/$query") {
                            // Opsi ini agar tidak menumpuk halaman pencarian
                            launchSingleTop = true
                            }*/
                        // 2. Gunakan logika navigasi LENGKAP
                        internalNavController.navigate("result_search/$query") {
                            // Pop up ke 'home' (start destination)
                            popUpTo(internalNavController.graph.findStartDestination().id) {
                                saveState = true // Simpan state layar yg ditinggal
                            }
                            launchSingleTop = true //
                            //restoreState = true // Pulihkan state jika 'result_search' pernah dibuka
                        }
                    },

                        // --- 1. TAMBAHKAN PARAMETER BARU INI ---
                        onAnimeClick = { animeId ->
                            // 2. Tutup overlay pencarian
                            isSearchVisible = false

                            // 3. Gunakan NavController UTAMA untuk navigasi ke Detail
                            navController.navigate(Screen.Detail.createRoute(animeId))
                        },
                        onMangaClick = { mangaId ->
                            isSearchVisible = false
                            navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Perbaiki preview agar tidak error
    MainScreen(
        navController = rememberNavController(), // Beri NavController palsu
        userData = UserData("123", "Raihan", "url_gambar_profil.com"),
        onSignOut = {}
    )
}
