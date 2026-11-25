package com.raihan.anicata.ui.archive.anime

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raihan.anicata.ui.archive.UnifiedArchiveScreen

@Composable
fun ArchiveAnimeScreen(
    onNavigateBack: () -> Unit = {},
    onBookmarkClicked: () -> Unit = {},
    onFavoriteClicked: () -> Unit = {},
    onAnimeClick: (Int) -> Unit = {}
) {
    UnifiedArchiveScreen(
        onItemClick = { id, _ -> onAnimeClick(id) },
        initialIsManga = false,
        initialCategoryIndex = 0
    )
}

@Preview(showBackground = true)
@Composable
fun ArchiveAnimeScreenPreview() {
    ArchiveAnimeScreen()
}
