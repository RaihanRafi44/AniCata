package com.raihan.anicata.ui.archive.manga

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raihan.anicata.ui.archive.UnifiedArchiveScreen

@Composable
fun ArchiveMangaScreen(
    onNavigateBack: () -> Unit = {},
    onBookmarkClicked: () -> Unit = {},
    onFavoriteClicked: () -> Unit = {},
    onMangaClick: (Int) -> Unit = {}
) {
    UnifiedArchiveScreen(
        onItemClick = { id, _ -> onMangaClick(id) },
        initialIsManga = true,
        initialCategoryIndex = 0
    )
}

@Preview(showBackground = true)
@Composable
fun ArchiveMangaScreenPreview() {
    ArchiveMangaScreen()
}
