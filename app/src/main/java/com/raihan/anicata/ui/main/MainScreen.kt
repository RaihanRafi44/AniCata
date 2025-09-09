package com.raihan.anicata.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raihan.anicata.data.model.UserData
import com.raihan.anicata.ui.archive.ArchiveScreen
import com.raihan.anicata.ui.home.HomeScreen
import com.raihan.anicata.ui.profile.ProfileScreen
import okhttp3.internal.platform.PlatformRegistry.applicationContext

/*
@Composable
fun MainScreen(
    userData: UserData?,      // <-- Terima data pengguna sebagai parameter
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    //var selectedItem by remember { mutableIntStateOf(0) }

    // Pantau back stack dari NavController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Tentukan item yang dipilih berdasarkan rute saat ini
    val selectedItem = when (currentDestination?.route) {
        "home" -> 0
        "archive" -> 1
        "profile" -> 2
        else -> 0 // Default ke home
    }

    Scaffold(
        bottomBar = {
            FloatingBottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    when (index) {
                        0 -> navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                        1 -> navController.navigate("archive") {
                            popUpTo("home") { inclusive = false }
                        }
                        2 -> navController.navigate("profile") {
                            popUpTo("home") { inclusive = false }
                        }
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
            */
/*composable("profile") {
                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            googleAuthUiClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed Out",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.popBackStack()
                        }
                    }
                )
            }*//*

            composable("profile") {
                // Teruskan parameter yang diterima ke ProfileScreen
                ProfileScreen(
                    userData = userData,
                    onSignOut = onSignOut
                )
            }
        }
    }
}

// Perbarui @Preview agar sesuai dengan fungsi yang baru
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        userData = UserData("123", "Raihan", "url_gambar_profil.com"),
        onSignOut = {}
    )
}*/

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
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "home" -> selectedItem = 0
            "archive" -> selectedItem = 1
            "profile" -> selectedItem = 2
        }
    }

    Scaffold(
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
