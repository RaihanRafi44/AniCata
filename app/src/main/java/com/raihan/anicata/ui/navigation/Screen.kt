package com.raihan.anicata.ui.navigation

// Nama argumen yang akan kita kirim (ID anime)
const val NAV_ARG_ANIME_ID = "animeId"
const val NAV_ARG_MANGA_ID = "mangaId" // <-- TAMBAHKAN INI

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")

    // Rute untuk layar detail
    // Perhatikan {animeId}: ini adalah placeholder untuk argumen
    object Detail : Screen("detail/{$NAV_ARG_ANIME_ID}") {
        // Fungsi helper untuk membuat rute dengan ID yang spesifik
        fun createRoute(animeId: Int) = "detail/$animeId"
    }

    // --- 2. TAMBAHKAN OBJEK BARU DI BAWAH INI ---
    object MangaDetail : Screen("manga_detail/{$NAV_ARG_MANGA_ID}") {
        fun createRoute(mangaId: Int) = "manga_detail/$mangaId"
    }
}