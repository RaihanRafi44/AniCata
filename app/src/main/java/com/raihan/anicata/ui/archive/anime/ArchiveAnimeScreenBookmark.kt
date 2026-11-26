package com.raihan.anicata.ui.archive.anime

import androidx.compose.runtime.Composable
import com.raihan.anicata.ui.archive.UnifiedArchiveScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArchiveAnimeScreenBookmark(
    viewModel: ArchiveAnimeViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    UnifiedArchiveScreen(
        animeViewModel = viewModel,
        onItemClick = { id, _ -> onAnimeClick(id) },
        initialIsManga = false,
        initialCategoryIndex = 1
    )
}
