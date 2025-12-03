package com.raihan.anicata.ui.archive.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
// --- Data Dummy (Digunakan di Preview) ---
private val dummyList = listOf(
    DummyArchiveAnimeBookmark(1, "Naruto", "https://cdn.myanimelist.net/images/anime/1769/126629.jpg", 8.02, 2700000, "TV", 12, 2022, "Fall"),
    DummyArchiveAnimeBookmark(2, "One Piece", "https://cdn.myanimelist.net/images/anime/1665/134707.jpg", 8.44, 3200000, "TV", 16, 2023, "Fall"),
    DummyArchiveAnimeBookmark(3, "Sword Art Online", "https://cdn.myanimelist.net/images/anime/1812/129758.jpg", 7.77, 1200000, "TV", null, 2023, "Winter"),
    DummyArchiveAnimeBookmark(4, "My Hero Academia", "https://cdn.myanimelist.net/images/anime/1758/125868.jpg", 8.00, 2000000, "TV", 25, 2021, "Spring"),
    DummyArchiveAnimeBookmark(5, "Jujutsu Kaisen", "https://cdn.myanimelist.net/images/anime/4/85888.jpg", 8.70, 4000000, "TV", 24, 2020, "Fall"),
    DummyArchiveAnimeBookmark(6, "Demon Slayer", "https://cdn.myanimelist.net/images/anime/1498/107024.jpg", 8.60, 4500000, "TV", 26, 2019, "Spring"),
)
// ------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveAnimeScreenBookmark(
    // Dalam implementasi nyata, ganti 'List<DummyArchiveAnimeBookmark>' dengan List dari data model Anda
    animeList: List<DummyArchiveAnimeBookmark>,
    onAnimeClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Membuat kolom bisa di-scroll
    ) {
        // --- 1. Top Bar ---
        TopAppBar(
            title = {
                Text(
                    text = "Anime Archive Bookmark",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            // Anda bisa menambahkan Navigation Icon jika diperlukan
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- 2. List Anime ---
        if (animeList.isEmpty()) {
            // Tampilkan pesan jika list kosong
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp), // Beri tinggi agar terlihat di tengah
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Belum ada anime di bookmark.")
            }
        } else {
            // Tampilkan list anime yang bisa di-scroll
            ArchiveAnimeListBookmarkLayout(
                animeList = animeList,
                onAnimeClick = onAnimeClick,
                // Tidak perlu modifier tambahan, karena sudah di-scroll oleh Column induk
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Ruang di bagian bawah
    }
}

@Preview(showBackground = true)
@Composable
fun ArchiveAnimeScreenBookmarkPreview() {
    ArchiveAnimeScreenBookmark(
        animeList = dummyList,
        onAnimeClick = {}
    )
}*/

// --- Data Dummy (Ubah jadi PUBLIC val agar bisa dipanggil di MainScreen) ---
val dummy_anime_archive_bookmark_data = listOf(
    DummyArchiveAnimeBookmark(1, "Naruto", "https://cdn.myanimelist.net/images/anime/1769/126629.jpg", 8.02, 2700000, "TV", 12, 2022, "Fall"),
    DummyArchiveAnimeBookmark(2, "One Piece", "https://cdn.myanimelist.net/images/anime/1665/134707.jpg", 8.44, 3200000, "TV", 16, 2023, "Fall"),
    DummyArchiveAnimeBookmark(3, "Sword Art Online", "https://cdn.myanimelist.net/images/anime/1812/129758.jpg", 7.77, 1200000, "TV", null, 2023, "Winter"),
    DummyArchiveAnimeBookmark(4, "My Hero Academia", "https://cdn.myanimelist.net/images/anime/1758/125868.jpg", 8.00, 2000000, "TV", 25, 2021, "Spring"),
    DummyArchiveAnimeBookmark(5, "Jujutsu Kaisen", "https://cdn.myanimelist.net/images/anime/4/85888.jpg", 8.70, 4000000, "TV", 24, 2020, "Fall"),
    DummyArchiveAnimeBookmark(6, "Demon Slayer", "https://cdn.myanimelist.net/images/anime/1498/107024.jpg", 8.60, 4500000, "TV", 26, 2019, "Spring"),
)
// ------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveAnimeScreenBookmark(
    // Default value diisi dengan data dummy kita, jadi pemanggil tidak wajib mengirim list jika belum ada data asli
    animeList: List<DummyArchiveAnimeBookmark> = dummy_anime_archive_bookmark_data,
    onAnimeClick: (Int) -> Unit,
    //onNavigateBack: () -> Unit // <-- 1. Callback Back ditambahkan
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // --- 1. Top Bar ---
        TopAppBar(
            title = {
                Text(
                    text = "Anime Archive Bookmark",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            // --- 2. Tombol Back ---
            /*navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            }*/
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- 2. List Anime ---
        if (animeList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Belum ada anime di bookmark.")
            }
        } else {
            ArchiveAnimeListBookmarkLayout(
                animeList = animeList,
                onAnimeClick = onAnimeClick,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ArchiveAnimeScreenBookmarkPreview() {
    ArchiveAnimeScreenBookmark(
        animeList = dummy_anime_archive_bookmark_data, // Gunakan variable public tadi
        onAnimeClick = {},
        //onNavigateBack = {}
    )
}
