package com.raihan.anicata.ui.top.manga

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
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.rememberPaginationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopMangaScreen(
    viewModel: TopMangaViewModel = koinViewModel()
) {
    // State dari ViewModel (nama variabel tidak berubah)
    val mangaList by viewModel.topManga.collectAsState()
    val totalPagesManga by viewModel.totalPages.collectAsState()
    val isLoadingManga by viewModel.isLoading.collectAsState()
    val errorManga by viewModel.error.collectAsState()

    var selectedFilter by rememberSaveable { mutableStateOf("") }

    val paginationState = rememberPaginationState(
        totalPages = totalPagesManga,
        visiblePages = 3
    )

    // Memuat data pertama kali saat screen ditampilkan
    LaunchedEffect(Unit) {
        // GANTI FUNGSI INI
        viewModel.getMangaForUiPage(
            uiPage = paginationState.currentPage,
            filter = selectedFilter
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            FilterTopManga(
                selectedFilterApiValue = selectedFilter,
                onFilterSelected = { newApiValue ->
                    // 1. Update state filter (ini akan ditangkap oleh ViewModel)
                    selectedFilter = newApiValue
                    // 2. Reset halaman ke 1
                    paginationState.onPageChange(1)
                    // 3. GANTI FUNGSI INI
                    viewModel.getMangaForUiPage(
                        uiPage = 1,
                        filter = newApiValue
                    )
                }
            )

            // Block 'if' ini tidak berubah
            if (!isLoadingManga && errorManga == null && mangaList.isNotEmpty()) {
                MangaListTopLayout(
                    mangaList = mangaList,
                    currentPage = paginationState.currentPage, // Ini PENTING untuk ranking
                    pageSize = 25,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Block paginasi
            if (!isLoadingManga && errorManga == null) {
                PaginationControls(
                    currentPage = paginationState.currentPage,
                    startPage = paginationState.startPage,
                    totalPages = totalPagesManga,
                    visiblePages = paginationState.visiblePages,
                    onPageChange = { newPage ->
                        // 1. Update state holder-nya
                        paginationState.onPageChange(newPage)
                        // 2. GANTI FUNGSI INI
                        viewModel.getMangaForUiPage(
                            uiPage = newPage,
                            filter = selectedFilter
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // Blok 'when' untuk loading/error tidak berubah
        when {
            isLoadingManga -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center) // Center di dalam Box
                )
            }
            errorManga != null -> {
                Text(
                    text = errorManga!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp) // Beri padding agar text error tidak terlalu lebar
                )
            }
            // Handle juga kasus "Empty"
            mangaList.isEmpty() && !isLoadingManga && errorManga == null -> {
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

