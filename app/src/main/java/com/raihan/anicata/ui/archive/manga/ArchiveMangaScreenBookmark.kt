package com.raihan.anicata.ui.archive.manga

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val dummy_manga_archive_bookmark_data = listOf(
    DummyArchiveMangaBookmark(1, "Naruto", "https://cdn.myanimelist.net/images/anime/1769/126629.jpg", 8.02, 2700000, "Manga", 12, 2022, "Fall"),
    DummyArchiveMangaBookmark(2, "One Piece", "https://cdn.myanimelist.net/images/anime/1665/134707.jpg", 8.44, 3200000, "Novel", 16, 2023, "Fall"),
    DummyArchiveMangaBookmark(3, "Sword Art Online", "https://cdn.myanimelist.net/images/anime/1812/129758.jpg", 7.77, 1200000, "Manga", null, 2023, "Winter"),
    DummyArchiveMangaBookmark(4, "My Hero Academia", "https://cdn.myanimelist.net/images/anime/1758/125868.jpg", 8.00, 2000000, "Light Novel", 25, 2021, "Spring"),
    DummyArchiveMangaBookmark(5, "Jujutsu Kaisen", "https://cdn.myanimelist.net/images/anime/4/85888.jpg", 8.70, 4000000, "Manga", 24, 2020, "Fall"),
    DummyArchiveMangaBookmark(6, "Demon Slayer", "https://cdn.myanimelist.net/images/anime/1498/107024.jpg", 8.60, 4500000, "Novel", 26, 2019, "Spring"),
)
// ------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveMangaScreenBookmark(
    // Default value diisi dengan data dummy kita, jadi pemanggil tidak wajib mengirim list jika belum ada data asli
    mangaList: List<DummyArchiveMangaBookmark> = dummy_manga_archive_bookmark_data,
    onMangaClick: (Int) -> Unit,
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
                    text = "Manga Archive Bookmark",
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
        if (mangaList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Belum ada manga di bookmark.")
            }
        } else {
            ArchiveMangaListBookmarkLayout(
                mangaList = mangaList,
                onMangaClick = onMangaClick,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ArchiveMangaScreenBookmarkPreview() {
    ArchiveMangaScreenBookmark(
        mangaList = dummy_manga_archive_bookmark_data, // Gunakan variable public tadi
        onMangaClick = {},
        //onNavigateBack = {}
    )
}