package com.raihan.anicata.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.math.abs
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

    var isCollapsed by remember { mutableStateOf(false) }
    var expandJob by remember { mutableStateOf<Job?>(null) }

    var scrollToTopTrigger by remember { mutableIntStateOf(0) }
    var scrollToBottomTrigger by remember { mutableIntStateOf(0) }

    val specialPageTitle = when {
        currentRoute?.startsWith("top_anime") == true -> "Top Anime"
        currentRoute?.startsWith("top_manga") == true -> "Top Manga"
        currentRoute?.startsWith("all_lists") == true -> "All Lists"
        currentRoute?.startsWith("seasonal") == true -> "Seasonal Anime"
        currentRoute?.startsWith("result_search") == true -> "Result Search"
        else -> null
    }

    val bottomBarNestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if (delta < -0.1f) { // Scroll KE BAWAH -> Mengecil (Collapse)
                    isCollapsed = true
                    expandJob?.cancel()
                    expandJob = scope.launch {
                        delay(800)
                        isCollapsed = false
                    }
                } else if (delta > 0.1f) { // Scroll KE ATAS -> Mengembang (Expand)
                    isCollapsed = false
                    expandJob?.cancel()
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y
                if (delta < -0.1f) { // Scroll KE BAWAH -> Mengecil (Collapse)
                    isCollapsed = true
                    expandJob?.cancel()
                    expandJob = scope.launch {
                        delay(800)
                        isCollapsed = false
                    }
                } else if (delta > 0.1f) { // Scroll KE ATAS -> Mengembang (Expand)
                    isCollapsed = false
                    expandJob?.cancel()
                }
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }

    LaunchedEffect(currentRoute) {
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
                modifier = Modifier.fillMaxWidth(0.75f),
                drawerContainerColor = Color(0xFFF7FAF9)
            ) {
                AppDrawerContent(
                    userData = userData,
                    navController = internalNavController,
                    scope = scope,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    onSignOut = onSignOut
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Scaffold(
                containerColor = Color.White,
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .nestedScroll(bottomBarNestedScrollConnection),
                topBar = {
                    TopNavBar(
                        onMenuClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        },
                        onSearchClick = { isSearchVisible = true },
                        scrollBehavior = scrollBehavior
                    )
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
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
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                onMangaClick = { mangaId ->
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                                },
                                // ✅ 2. Sambungkan navigasi klik panah "View All"
                                onViewAllTopRatedAnimeClick = {
                                    internalNavController.navigate("top_anime")
                                },
                                onViewAllTopRatedMangaClick = {
                                    internalNavController.navigate("top_manga")
                                },
                                onViewAllSeasonalClick = {
                                    internalNavController.navigate("seasonal")
                                },
                                onViewAllUpcomingClick = {
                                    internalNavController.navigate("seasonal?season=upcoming")
                                }
                            )
                        }
                        composable("archive") {
                            ArchiveMainScreen(
                                onItemClick = { id, isManga ->
                                    if (isManga) {
                                        navController.navigate(Screen.MangaDetail.createRoute(id))
                                    } else {
                                        navController.navigate(Screen.Detail.createRoute(id))
                                    }
                                }
                            )
                        }

                        composable("archive_anime") {
                            ArchiveAnimeScreen(
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                }
                            )
                        }

                        composable("archive_manga") {
                            ArchiveMangaScreen(
                                onMangaClick = { mangaId ->
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                                }
                            )
                        }

                        composable("archive_anime_bookmark") {
                            ArchiveAnimeScreenBookmark(
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                onNavigateBack = {
                                    internalNavController.popBackStack()
                                },
                                onDeleteClick = {}
                            )
                        }

                        composable("archive_anime_favorite") {
                            ArchiveAnimeScreenFavorite(
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                onNavigateBack = {
                                    internalNavController.popBackStack()
                                },
                                onDeleteClick = {}
                            )
                        }

                        composable("archive_manga_bookmark") {
                            ArchiveMangaScreenBookmark(
                                onMangaClick = { mangaId ->
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
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
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
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
                        composable(
                            route = "seasonal?season={season}",
                            arguments = listOf(
                                navArgument("season") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = null
                                }
                            )
                        ) { backStackEntry ->
                            val seasonArg = backStackEntry.arguments?.getString("season")
                            SeasonalScreen(
                                initialSeason = seasonArg,
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                scrollToTopTrigger = scrollToTopTrigger,
                                scrollToBottomTrigger = scrollToBottomTrigger
                            )
                        }

                        composable("top_anime") {
                            TopAnimeScreen(
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                scrollToTopTrigger = scrollToTopTrigger,
                                scrollToBottomTrigger = scrollToBottomTrigger
                            )
                        }
                        composable("top_manga") {
                            TopMangaScreen(
                                onMangaClick = { mangaId ->
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                                },
                                scrollToTopTrigger = scrollToTopTrigger,
                                scrollToBottomTrigger = scrollToBottomTrigger
                            )
                        }

                        composable("all_lists") {
                            AllListsScreen(
                                onAnimeClick = { animeId ->
                                    navController.navigate(Screen.Detail.createRoute(animeId))
                                },
                                onMangaClick = { mangaId ->
                                    navController.navigate(Screen.MangaDetail.createRoute(mangaId))
                                },
                                scrollToTopTrigger = scrollToTopTrigger,
                                scrollToBottomTrigger = scrollToBottomTrigger
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
                                },
                                scrollToTopTrigger = scrollToTopTrigger,
                                scrollToBottomTrigger = scrollToBottomTrigger
                            )
                        }

                    }

                    if (specialPageTitle != null) {
                        SpecialPageFloatingNavBar(
                            onScrollToTop = { scrollToTopTrigger++ },
                            onScrollToBottom = { scrollToBottomTrigger++ },
                            onNavigateTo = { route ->
                                selectedItem = when (route) {
                                    "home" -> 0
                                    "archive" -> 1
                                    "profile" -> 2
                                    else -> 0
                                }
                                internalNavController.navigate(route) {
                                    popUpTo(internalNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            accentColor = Color(0xFF006A60),
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        FloatingBottomNavBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp),
                            selectedItem = selectedItem,
                            isCollapsed = isCollapsed,
                            containerColor = Color(0xFFFCFCFC),
                            accentColor = Color(0xFF006A60),
                            inactiveColor = Color(0xFF8E8E93),
                            onItemSelected = { index ->
                                val route = when (index) {
                                    0 -> "home"
                                    1 -> "archive"
                                    2 -> "profile"
                                    else -> "home"
                                }
                                if (selectedItem == index) {
                                    internalNavController.popBackStack(route, inclusive = false)
                                } else {
                                    selectedItem = index
                                    internalNavController.navigate(route) {
                                        popUpTo(internalNavController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }

            // --- BAGIAN BACKGROUND GELAP MODAL (Scrim) ---
            if (isSearchVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.55f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            isSearchVisible = false
                        }
                )
            }

            // --- ANIMASI MODAL TERPUSAT DI TENGAH VERTIKAL ---
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = fadeIn(animationSpec = tween(250)) +
                        scaleIn(
                            initialScale = 0.92f,
                            transformOrigin = TransformOrigin(0.5f, 0.5f),
                            animationSpec = tween(250)
                        ),
                exit = fadeOut(animationSpec = tween(200)) +
                        scaleOut(
                            targetScale = 0.92f,
                            transformOrigin = TransformOrigin(0.5f, 0.5f),
                            animationSpec = tween(200)
                        )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    SearchScreenLayout(
                        onClose = { isSearchVisible = false },
                        onSearchSubmitted = { query ->
                            isSearchVisible = false
                            internalNavController.navigate("result_search/$query") {
                                popUpTo(internalNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        },
                        onAnimeClick = { animeId ->
                            isSearchVisible = false
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
    }

