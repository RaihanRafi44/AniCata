package com.raihan.anicata.ui.top.novel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.top.manga.FilterTopManga
import com.raihan.anicata.ui.top.manga.MangaListTopLayout

@Composable
fun TopNovelScreen() {
    // Menambahkan kemampuan scroll pada Column terluar
    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi seluruh layar
            .verticalScroll(rememberScrollState()) // Membuat konten bisa di-scroll
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        // Memanggil komponen grup filter yang sudah dibuat sebelumnya
        FilterTopNovel()

        NovelListTopLayout()

    }
}

@Preview(showBackground = true)
@Composable
fun TopNovelScreenPreview() {
    TopNovelScreen()
}