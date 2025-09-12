package com.raihan.anicata.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raihan.anicata.data.model.UserData
import com.raihan.anicata.ui.archive.ArchiveScreen
import com.raihan.anicata.ui.home.HomeScreen
import com.raihan.anicata.ui.profile.ProfileScreen
import kotlinx.coroutines.launch
import okhttp3.internal.platform.PlatformRegistry.applicationContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    // 1. Gunakan 'rememberSaveable' agar state tidak hilang saat ada perubahan konfigurasi
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    // 2. Gunakan 'LaunchedEffect' untuk menyinkronkan 'selectedItem' dengan NavController
    // Ini akan memperbarui highlight saat menekan tombol kembali (back press)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // --- State untuk Drawer dan TopNavBar dipindahkan ke sini ---
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val topBarColor = Color(0xFFE0F2F1) // Warna yang sama dari HomeScreen
    val systemUiController = rememberSystemUiController()

    // Efek untuk mengubah warna status bar
    /*SideEffect {
        systemUiController.setStatusBarColor(
            color = topBarColor,
            darkIcons = true
        )
    }*/

    // Gunakan LaunchedEffect agar kode ini hanya berjalan sekali saat komponen dibuat
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = topBarColor, // Atur warna status bar SAMA DENGAN topBarColor
            darkIcons = true     // Tampilkan ikon (jam, baterai) menjadi gelap agar terlihat
        )
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "home" -> selectedItem = 0
            "archive" -> selectedItem = 1
            "profile" -> selectedItem = 2
        }
    }

    /*Scaffold(
        bottomBar = {
            FloatingBottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    // 3. Tetap update state secara manual untuk UI yang responsif saat diklik
                    selectedItem = index
                    val route = when (index) {
                        0 -> "home"
                        1 -> "archive"
                        2 -> "profile"
                        else -> "home"
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen() }
            composable("archive") { ArchiveScreen() }
            composable("profile") {
                ProfileScreen(
                    userData = userData,
                    onSignOut = onSignOut
                )
            }
        }
    }*/

    /*Scaffold { padding ->
        // Gunakan Box untuk menampung konten dan navigation bar
        Box(modifier = Modifier.fillMaxSize()) {

            val topPadding = padding.calculateTopPadding()

            NavHost(
                navController = navController,
                startDestination = "home",
                //modifier = Modifier.padding(padding)
                modifier = Modifier.padding(top = topPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("archive") { ArchiveScreen() }
                composable("profile") {
                    ProfileScreen(
                        userData = userData,
                        onSignOut = onSignOut
                    )
                }
            }

            // Panggil FloatingBottomNavBar di sini, di atas NavHost
            FloatingBottomNavBar(
                // Modifier ini akan memposisikan nav bar di tengah bawah
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 54.dp),
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    val route = when (index) {
                        0 -> "home"
                        1 -> "archive"
                        2 -> "profile"
                        else -> "home"
                    }
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }*/
    // --- ModalNavigationDrawer membungkus semua konten ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            /*ModalDrawerSheet {
                // TODO: Isi konten drawer di sini
                Text("Menu 1", modifier = Modifier.padding(16.dp))
                Text("Menu 2", modifier = Modifier.padding(16.dp))
            }*/
            ModalDrawerSheet(
                // ✅ 1. BATASI LEBAR DRAWER MAKSIMAL 50% DARI LAYAR
                modifier = Modifier.fillMaxWidth(0.6f),
                drawerContainerColor = Color(0xFFE0F2F1)
            ) {
                AppDrawerContent(
                    userData = userData,
                    navController = navController,
                    scope = scope,
                    closeDrawer = { scope.launch { drawerState.close() } },
                    onSignOut = onSignOut
                )
            }
        }
    ) {
        Scaffold(
            // --- TopNavBar didefinisikan di sini ---
            // ✅ Hubungkan scroll behavior ke Scaffold
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopNavBar(
                    //onMenuClick = { scope.launch { drawerState.open() } },
                    // ✅ 4. UBAH LOGIKA onMenuClick MENJADI TOGGLE
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    onSearchClick = { /* TODO: Aksi untuk search */ },
                    onSettingsClick = { /* TODO: Aksi untuk settings */ },
                    scrollBehavior = scrollBehavior // <-- Kirim scrollBehavior
                )
            },
            // --- BottomNavBar ditempatkan di dalam Box agar bisa "floating" ---
            // Menggunakan Box untuk menumpuk NavHost dan FloatingBottomNavBar
        ) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    // Terapkan padding dari Scaffold ke NavHost
                    // Ini penting agar konten halaman tidak tertutup TopBar
                    //modifier = Modifier.padding(innerPadding)
                    // Modifier NavHost tidak perlu padding lagi karena Box sudah menanganinya
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("home") { HomeScreen() }
                    composable("archive") { ArchiveScreen() }
                    composable("profile") {
                        ProfileScreen(
                            userData = userData,
                            onSignOut = onSignOut
                        )
                    }
                }

                // FloatingBottomNavBar tetap di sini, diposisikan di atas NavHost
                FloatingBottomNavBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        // Sesuaikan padding bawah jika diperlukan, innerPadding sudah
                        // menangani ruang untuk BottomNavBar jika ditaruh di slot bottomBar.
                        // Namun karena floating, kita atur manual.
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
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        userData = UserData("123", "Raihan", "url_gambar_profil.com"),
        onSignOut = {}
    )
}
