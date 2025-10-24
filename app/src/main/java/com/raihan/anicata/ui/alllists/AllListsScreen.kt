package com.raihan.anicata.ui.alllists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.rememberPaginationState
import org.koin.androidx.compose.koinViewModel

/*
@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel()
) {
    // Ambil UI state dari ViewModel
    val uiState by viewModel.uiState.collectAsState()

    val paginationState = rememberPaginationState(
        initialPage = 1,
        totalPages = uiState.totalPages,
        visiblePages = 3 // Jumlah tombol angka yg terlihat (misal: 1, 2, 3)
    )

    LaunchedEffect(paginationState.currentPage) {
        if (paginationState.currentPage != uiState.currentPage) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    */
/*Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        // Memanggil komponen grup filter
        AllListsFilterGroup() // (Anda perlu membuat implementasi UI-nya)


        AnimeListLayout(
            animeList = uiState.animeList,
            isLoading = uiState.isLoading,
            isError = uiState.isError,
            errorMessage = uiState.errorMessage,
            paginationState = paginationState
        )
    }*//*


    // 1. GANTI root menjadi Box (mengikuti referensi TopAnimeScreen)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 2. Tambahkan Column dengan verticalScroll untuk SEMUA konten
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter
            AllListsFilterGroup()

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                // Tampilkan list anime
                AnimeListLayout(
                    animeList = uiState.animeList,
                    */
/*isLoading = uiState.isLoading,
                    isError = uiState.isError,
                    errorMessage = uiState.errorMessage,
                    paginationState = paginationState*//*

                )

                // Tampilkan Paging Controls di bawah list
                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            paginationState.onPageChange(newPage)
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            // Spacer di bagian bawah agar tidak terpotong BottomNav
            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Tambahkan Overlay untuk Loading, Error, dan Empty State
        when {
            // Tampilkan loading di tengah
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // Tampilkan error di tengah
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center) // <-- Taruh di tengah Box
                )
            }
            // Handle "Empty" state
            uiState.animeList.isEmpty() && !uiState.isLoading && !uiState.isError -> {
                Text(
                    text = "Tidak ada data.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllListsScreenPreview() { // Ganti nama preview agar sesuai
    AllListsScreen()
}*/

/*@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val paginationState = rememberPaginationState(
        initialPage = 1,
        totalPages = uiState.totalPages,
        visiblePages = 3
    )

    LaunchedEffect(paginationState.currentPage) {
        if (paginationState.currentPage != uiState.currentPage) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    // 1. Box sebagai root
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // 2. Logika if-else UTAMA
        when {
            // KASUS 1: INITIAL LOAD
            // Tampilkan HANYA loading indicator di tengah sempurna.
            uiState.isLoading && uiState.animeList.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // KASUS 2: INITIAL ERROR
            // Tampilkan HANYA error state di tengah sempurna.
            uiState.isError && uiState.animeList.isEmpty() -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // KASUS 3: KONTEN SIAP
            // (Termasuk: Sukses, Empty, atau sedang memuat halaman 2+)
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Item 1: Filter
                    AllListsFilterGroup()

                    // Item 2: List (jika tidak kosong)
                    if (uiState.animeList.isNotEmpty()) {
                        AnimeListLayout(
                            animeList = uiState.animeList
                        )
                    }
                    // Item 3: Empty State (jika kosong)
                    else if (!uiState.isLoading && !uiState.isError) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 100.dp), // Jarak dari filter
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Tidak ada data.",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Item 4: Paging (jika perlu)
                    if (paginationState.totalPages > 1 && !uiState.isError) {
                        PaginationControls(
                            currentPage = paginationState.currentPage,
                            startPage = paginationState.startPage,
                            totalPages = paginationState.totalPages,
                            onPageChange = { newPage ->
                                paginationState.onPageChange(newPage)
                            },
                            visiblePages = paginationState.visiblePages
                        )
                    }

                    // Item 5: Loading Halaman Berikutnya (di bawah)
                    if (uiState.isLoading && uiState.animeList.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    // Spacer di bagian bawah
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllListsScreenPreview() {
    AllListsScreen()
}*/

/*@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val paginationState = rememberPaginationState(
        initialPage = 1,
        totalPages = uiState.totalPages,
        visiblePages = 3
    )

    LaunchedEffect(paginationState.currentPage) {
        if (paginationState.currentPage != uiState.currentPage) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    // 1. Gunakan Box sebagai root untuk menampung overlay
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 2. Konten (Filter, List, Paging) ada di dalam Column yang bisa scroll
        //    Column ini SELALU terlihat.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter (Selalu terlihat)
            AllListsFilterGroup()

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                // Tampilkan list anime
                AnimeListLayout(
                    animeList = uiState.animeList
                )

                // Tampilkan Paging Controls di bawah list
                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            paginationState.onPageChange(newPage)
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            // Spacer di bagian bawah
            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Overlay Layer (untuk status Loading, Error, dan Empty)
        //    Ini ada di dalam Box, BUKAN di dalam Column
        when {
            // KASUS 1: LOADING
            // (akan menutupi list, tapi filter tetap terlihat di atas)
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // KASUS 2: ERROR
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // KASUS 3: EMPTY (setelah load selesai dan tidak error)
            uiState.animeList.isEmpty() && !uiState.isLoading && !uiState.isError -> {
                Text(
                    text = "Tidak ada data.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllListsScreenPreview() {
    AllListsScreen()
}*/

/*@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val paginationState = rememberPaginationState(
        initialPage = 1,
        totalPages = uiState.totalPages,
        visiblePages = 3
    )

    LaunchedEffect(paginationState.currentPage) {
        // Hanya fetch jika halaman berubah BUKAN karena filter baru
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    // Sinkronkan state ViewModel ke state Paging
    // Jika filter diterapkan, reset PagingControls ke halaman 1
    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

    // 1. Gunakan Box sebagai root untuk menampung overlay
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 2. Konten (Filter, List, Paging) ada di dalam Column yang bisa scroll
        //    Column ini SELALU terlihat.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter (Selalu terlihat)
            // --- MODIFIKASI DISINI ---
            AllListsFilterGroup(
                selectedSort = uiState.selectedSort,
                onSortChanged = { newSortValue ->
                    viewModel.updateSortFilter(newSortValue)
                },
                onUpdateFilterClicked = {
                    viewModel.applyFilters()
                }
            )

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                // Tampilkan list anime
                AnimeListLayout(
                    animeList = uiState.animeList
                )

                // Tampilkan Paging Controls di bawah list
                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            // Panggil onPageChange dari PaginationState
                            paginationState.onPageChange(newPage)
                            // Panggil fetch dari ViewModel
                            viewModel.fetchAnimePage(newPage)
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            // Spacer di bagian bawah
            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Overlay Layer (untuk status Loading, Error, dan Empty)
        //    Ini ada di dalam Box, BUKAN di dalam Column
        when {
            // KASUS 1: LOADING
            // (akan menutupi list, tapi filter tetap terlihat di atas)
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp), // Beri padding agar tidak menutupi filter
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // KASUS 2: ERROR
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // KASUS 3: EMPTY (setelah load selesai dan tidak error)
            uiState.animeList.isEmpty() && !uiState.isLoading && !uiState.isError -> {
                Text(
                    text = "Tidak ada data.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}*/

@Composable
fun AllListsScreen(
    viewModel: AllListsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val paginationState = rememberPaginationState(
        initialPage = 1,
        totalPages = uiState.totalPages,
        visiblePages = 3
    )

    LaunchedEffect(paginationState.currentPage) {
        // Hanya fetch jika halaman berubah BUKAN karena filter baru
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    // Sinkronkan state ViewModel ke state Paging
    // Jika filter diterapkan, reset PagingControls ke halaman 1
    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

    // 1. Gunakan Box sebagai root untuk menampung overlay
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 2. Konten (Filter, List, Paging) ada di dalam Column yang bisa scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter
            // --- MODIFIKASI DISINI ---
            AllListsFilterGroup(
                selectedSort = uiState.selectedSort,
                onSortChanged = { newSortValue ->
                    viewModel.updateSortFilter(newSortValue)
                },
                // --- TAMBAHKAN INI ---
                selectedType = uiState.selectedType,
                onTypeChanged = { newTypeValue ->
                    viewModel.updateTypeFilter(newTypeValue)
                },
                // ---
                onUpdateFilterClicked = {
                    viewModel.applyFilters()
                }
            )

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                // Tampilkan list anime
                AnimeListLayout(
                    animeList = uiState.animeList
                )

                // Tampilkan Paging Controls di bawah list
                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            // Panggil onPageChange dari PaginationState
                            paginationState.onPageChange(newPage)
                            // Panggil fetch dari ViewModel
                            viewModel.fetchAnimePage(newPage)
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            // Spacer di bagian bawah
            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Overlay Layer (untuk status Loading, Error, dan Empty)
        when {
            // KASUS 1: LOADING
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp), // Beri padding agar tidak menutupi filter
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            // KASUS 2: ERROR
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // KASUS 3: EMPTY (setelah load selesai dan tidak error)
            uiState.animeList.isEmpty() && !uiState.isLoading && !uiState.isError -> {
                Text(
                    text = "Tidak ada data.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp)
                )
            }
        }
    }
}
