package com.raihan.anicata.ui.archive.manga

import androidx.compose.runtime.Composable
import com.raihan.anicata.ui.archive.UnifiedArchiveScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchiveMangaScreenBookmark(
    viewModel: ArchiveMangaViewModel = koinViewModel(),
    onMangaClick: (Int) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    UnifiedArchiveScreen(
        mangaViewModel = viewModel,
        onItemClick = { id, _ -> onMangaClick(id) },
        initialIsManga = true,
        initialCategoryIndex = 1
    )
}
