package com.raihan.anicata.ui.top.anime

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.rememberPaginationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopAnimeScreen(
    viewModel: TopAnimeViewModel = koinViewModel()
) {
    // Mengumpulkan state dari ViewModel
    val animeList by viewModel.topAnime.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // == STATE UNTUK FILTER ==
    // State untuk filter yang dipilih (menggunakan nilai API)
    // "" = "All Anime"
    var selectedFilter by rememberSaveable { mutableStateOf("") }

    val paginationState = rememberPaginationState(
        totalPages = totalPages, // Kirim totalPages dari ViewModel
        visiblePages = 3
    )

    // Memuat data pertama kali saat screen ditampilkan
    LaunchedEffect(Unit) {
        viewModel.getTopAnimeData(
            page = paginationState.currentPage,// Gunakan page dari state
            filter = selectedFilter // Untuk filter
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 2. KONTEN (YANG BISA DI-SCROLL)
        // Kolom ini berisi semua kontenmu
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            FilterTopAnime(
                selectedFilterApiValue = selectedFilter,
                onFilterSelected = { newApiValue ->
                    // 1. Update state filter
                    selectedFilter = newApiValue
                    // 2. Reset halaman ke 1 (penting saat ganti filter)
                    paginationState.onPageChange(1) // Asumsi ini mengupdate currentPage
                    // 3. Panggil ViewModel dengan filter baru dan halaman 1
                    viewModel.getTopAnimeData(
                        page = 1,
                        filter = newApiValue
                    )
                }
            )

            // 3. Tampilkan list HANYA JIKA TIDAK LOADING, TIDAK ERROR, DAN ADA ISINYA
            if (!isLoading && error == null && animeList.isNotEmpty()) {
                AnimeListTopLayout(
                    animeList = animeList,
                    currentPage = paginationState.currentPage,
                    pageSize = 25,
                    modifier = Modifier // padding sudah dihandle di dalam AnimeListTopLayout
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 4. Tampilkan paginasi HANYA JIKA TIDAK LOADING DAN TIDAK ERROR
            if (!isLoading && error == null) {
                PaginationControls(
                    currentPage = paginationState.currentPage,
                    startPage = paginationState.startPage,
                    totalPages = paginationState.totalPages,
                    visiblePages = paginationState.visiblePages,
                    onPageChange = { newPage ->
                        // 1. Update state holder-nya
                        paginationState.onPageChange(newPage)
                        // 2. Panggil ViewModel dengan halaman baru
                        viewModel.getTopAnimeData(
                            page = newPage,
                            filter = selectedFilter // <-- TAMBAHKAN INI
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp)) // Beri ruang di bawah
        }

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center) // Center di dalam Box
                )
            }
            error != null -> {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp) // Beri padding agar text error tidak terlalu lebar
                )
            }
            // Handle juga kasus "Empty"
            animeList.isEmpty() && !isLoading && error == null -> {
                Text(
                    text = "Tidak ada data.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAnimeScreenPreview() {
    TopAnimeScreen()
}