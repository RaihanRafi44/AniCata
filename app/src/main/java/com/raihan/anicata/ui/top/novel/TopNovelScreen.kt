package com.raihan.anicata.ui.top.novel

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
fun TopNovelScreen(
    viewModel: TopNovelViewModel = koinViewModel()
) {
    val novelList by viewModel.topNovel.collectAsState()
    val totalPagesNovel by viewModel.totalPages.collectAsState()
    val isLoadingNovel by viewModel.isLoading.collectAsState()
    val errorNovel by viewModel.error.collectAsState()

    var selectedFilter by rememberSaveable { mutableStateOf("") }

    val paginationState = rememberPaginationState(
        totalPages = totalPagesNovel,
        visiblePages = 3
    )

    // PERUBAHAN: Panggil fungsi baru
    LaunchedEffect(Unit) {
        viewModel.getNovelForUiPage(
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

            FilterTopNovel(
                selectedFilterApiValue = selectedFilter,
                onFilterSelected = { newApiValue ->
                    // 1. Update state filter
                    selectedFilter = newApiValue
                    // 2. Reset halaman ke 1 (penting saat ganti filter)
                    paginationState.onPageChange(1) // Asumsi ini mengupdate currentPage
                    // 3. PERUBAHAN: Panggil fungsi baru
                    viewModel.getNovelForUiPage(
                        uiPage = 1,
                        filter = newApiValue
                    )
                }
            )

            // Tampilkan list (Tidak ada perubahan di sini)
            if (!isLoadingNovel && errorNovel== null && novelList.isNotEmpty()) {
                NovelListTopLayout(
                    novelList = novelList,
                    currentPage = paginationState.currentPage,
                    pageSize = 25,
                    modifier = Modifier
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tampilkan paginasi (Tidak ada perubahan di sini)
            if (!isLoadingNovel && errorNovel == null) {
                PaginationControls(
                    currentPage = paginationState.currentPage,
                    startPage = paginationState.startPage,
                    totalPages = totalPagesNovel, // Gunakan totalPages dari VM
                    visiblePages = paginationState.visiblePages,
                    onPageChange = { newPage ->
                        // 1. Update state holder-nya
                        paginationState.onPageChange(newPage)
                        // 2. PERUBAHAN: Panggil fungsi baru
                        viewModel.getNovelForUiPage(
                            uiPage = newPage,
                            filter = selectedFilter
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp)) // Beri ruang di bawah
        }

        // Tidak ada perubahan di 'when'
        when {
            isLoadingNovel-> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center) // Center di dalam Box
                )
            }
            errorNovel != null -> {
                Text(
                    text = errorNovel!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp) // Beri padding agar text error tidak terlalu lebar
                )
            }
            // Handle juga kasus "Empty"
            novelList.isEmpty() && !isLoadingNovel && errorNovel == null -> {
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
