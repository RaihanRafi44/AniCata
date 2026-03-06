package com.raihan.anicata.ui.archive

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArchiveMainScreen(
    onNavigateToAnime: () -> Unit,
    onNavigateToManga: () -> Unit
) {

    ArchiveMainLayout(
        onAnimeClicked = onNavigateToAnime,
        onMangaClicked = onNavigateToManga
    )

}

@Preview(showBackground = true)
@Composable
fun ArchiveScreenPreview() {
    ArchiveMainScreen(
        onNavigateToAnime = {},
        onNavigateToManga = {}
    )
}