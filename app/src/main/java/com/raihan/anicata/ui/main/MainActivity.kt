package com.raihan.anicata.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.auth.api.identity.Identity
import com.raihan.anicata.ui.detail.anime.DetailAnimeScreen
import com.raihan.anicata.ui.detail.manga.DetailMangaScreen
import com.raihan.anicata.ui.login.GoogleAuthUiClient
import com.raihan.anicata.ui.login.LoginViewModel
import com.raihan.anicata.ui.login.SignInScreen
import com.raihan.anicata.ui.navigation.NAV_ARG_ANIME_ID
import com.raihan.anicata.ui.navigation.NAV_ARG_MANGA_ID
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.ui.theme.AniCataTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient: GoogleAuthUiClient by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ){
                // NavController ini akan mengontrol navigasi level atas (Login, Main, Detail)
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Login.route){
                    composable(Screen.Login.route) {

                        val viewModel: LoginViewModel = koinViewModel()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = Unit) {
                            if (googleAuthUiClient.getSignedInUser() != null) {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val loginResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(loginResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                viewModel.resetState()
                            }
                        }

                        SignInScreen(
                            state = state,
                            onSignInClick = {
                                lifecycleScope.launch {
                                    val signIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        )
                    }
                    composable(Screen.Main.route) {
                        // Kirim NavController utama ke MainScreen
                        // agar ia bisa memicu navigasi ke "detail"
                        MainScreen(
                            navController = navController,
                            userData = googleAuthUiClient.getSignedInUser(),
                            onSignOut = {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signOut()
                                    Toast.makeText(
                                        applicationContext,
                                        "Signed Out",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("login") {
                                        popUpTo("main") { inclusive = true }
                                    }
                                }
                            }
                        )
                    }
                    // Rute "detail" didefinisikan di sini, sejajar dengan "main"
                    // Sehingga tidak akan menampilkan Scaffold dari MainScreen
                    /*composable(
                        route = Screen.Detail.route,
                        arguments = listOf(navArgument(NAV_ARG_ANIME_ID) {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->
                        // Ambil ID dari argumen navigasi
                        val animeId = navBackStackEntry.arguments?.getInt(NAV_ARG_ANIME_ID) ?: 0

                        // Panggil DetailAnimeScreen yang benar dengan ID
                        DetailAnimeScreen(animeId = animeId)
                        //DetailScreen()
                    }*/

                    composable(
                        route = Screen.Detail.route, // "detail/{animeId}"
                        arguments = listOf(navArgument(NAV_ARG_ANIME_ID) {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->

                        val animeId = navBackStackEntry.arguments?.getInt(NAV_ARG_ANIME_ID) ?: 0

                        DetailAnimeScreen(
                            animeId = animeId,
                            navController = navController // <-- 1. BERIKAN NAVCONTROLLER KE SCREEN
                        )
                    }

                    // --- 3. TAMBAHKAN BLOK COMPOSABLE BARU INI ---
                    composable(
                        route = Screen.MangaDetail.route, // "manga_detail/{mangaId}"
                        arguments = listOf(navArgument(NAV_ARG_MANGA_ID) {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->
                        // Ambil ID dari argumen
                        val mangaId = navBackStackEntry.arguments?.getInt(NAV_ARG_MANGA_ID) ?: 0

                        // Panggil MangaDetailScreen (yang sudah kita buat)
                        DetailMangaScreen(
                            mangaId = mangaId,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
