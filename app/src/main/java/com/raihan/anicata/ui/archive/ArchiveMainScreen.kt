package com.raihan.anicata.ui.archive

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArchiveMainScreen(
    onNavigateToAnime: () -> Unit = {},
    onNavigateToManga: () -> Unit = {},
    onItemClick: (id: Int, isManga: Boolean) -> Unit = { _, _ -> },
    initialIsManga: Boolean = false,
    initialCategoryIndex: Int = 0
) {
    UnifiedArchiveScreen(
        onItemClick = onItemClick,
        initialIsManga = initialIsManga,
        initialCategoryIndex = initialCategoryIndex
    )
}

@Preview(showBackground = true)
@Composable
fun ArchiveScreenPreview() {
    ArchiveMainScreen()
}
