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
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchAnimePage(paginationState.currentPage)
        }
    }

    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter
            // --- MODIFIKASI: Mengembalikan semua parameter ---
            AllListsFilterGroup(
                // Filter Sort
                selectedSort = uiState.selectedSort,
                onSortChanged = { viewModel.updateSortFilter(it) },

                // Filter Tipe
                selectedType = uiState.selectedType,
                onTypeChanged = { viewModel.updateTypeFilter(it) },

                // Filter Genre
                selectedGenre = uiState.selectedGenre,
                onGenreChanged = { viewModel.updateGenreFilter(it) },
                genreOptions = uiState.genreList.map { it.name },

                // --- Filter Tema (DIKEMBALIKAN) ---
                selectedTheme = uiState.selectedTheme,
                onThemeChanged = { viewModel.updateThemeFilter(it) },
                themeOptions = uiState.themeList.map { it.name },

                // --- Filter Target (DIKEMBALIKAN) ---
                selectedTarget = uiState.selectedTarget,
                onTargetChanged = { viewModel.updateTargetFilter(it) },
                targetOptions = uiState.demographicList.map { it.name },

                // Tombol Update
                onUpdateFilterClicked = {
                    viewModel.applyFilters()
                }
            )

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                AnimeListLayout(
                    animeList = uiState.animeList
                )

                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            paginationState.onPageChange(newPage)
                            viewModel.fetchAnimePage(newPage)
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Overlay Layer (Status Loading, Error, dan Empty)
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
        if (paginationState.currentPage != uiState.currentPage && uiState.currentPage != 1) {
            viewModel.fetchMediaPage(paginationState.currentPage) // GANTI: fetchAnimePage -> fetchMediaPage
        }
    }

    LaunchedEffect(uiState.currentPage) {
        if (uiState.currentPage == 1 && paginationState.currentPage != 1) {
            paginationState.onPageChange(1)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Item 1: Filter
            AllListsFilterGroup(
                // --- Kategori ---
                selectedCategory = uiState.selectedCategory,
                onCategoryChanged = viewModel::updateCategory, // Gunakan method reference
                categoryOptions = uiState.categoryOptions,

                // --- Sort ---
                selectedSort = uiState.selectedSort,
                onSortChanged = viewModel::updateSortFilter,
                sortOptions = uiState.sortOptions,

                // --- Tipe ---
                selectedType = uiState.selectedType,
                onTypeChanged = viewModel::updateTypeFilter,
                typeOptions = uiState.typeOptions, // Sekarang dinamis

                // --- Genre ---
                selectedGenre = uiState.selectedGenre,
                onGenreChanged = viewModel::updateGenreFilter,
                genreOptions = uiState.genreList.map { it.name },

                // --- Tema ---
                selectedTheme = uiState.selectedTheme,
                onThemeChanged = viewModel::updateThemeFilter,
                themeOptions = uiState.themeList.map { it.name },

                // --- Target ---
                selectedTarget = uiState.selectedTarget,
                onTargetChanged = viewModel::updateTargetFilter,
                targetOptions = uiState.demographicList.map { it.name },

                // Tombol Update
                onUpdateFilterClicked = viewModel::applyFilters
            )

            // 3. Tampilkan list HANYA JIKA tidak loading dan tidak error
            if (!uiState.isLoading && !uiState.isError) {
                MediaListLayout( // GANTI: AnimeListLayout -> MediaListLayout
                    mediaList = uiState.mediaList // GANTI: animeList -> mediaList
                )

                if (paginationState.totalPages > 1) {
                    PaginationControls(
                        currentPage = paginationState.currentPage,
                        startPage = paginationState.startPage,
                        totalPages = paginationState.totalPages,
                        onPageChange = { newPage ->
                            paginationState.onPageChange(newPage)
                            viewModel.fetchMediaPage(newPage) // GANTI: fetchAnimePage -> fetchMediaPage
                        },
                        visiblePages = paginationState.visiblePages
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // 4. Overlay Layer (Status Loading, Error, dan Empty)
        when {
            // KASUS 1: LOADING (ISI KEMBALI)
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
            // KASUS 2: ERROR (ISI KEMBALI)
            uiState.isError -> {
                ErrorStateContent(
                    errorMessage = uiState.errorMessage,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // KASUS 3: EMPTY (ISI KEMBALI)
            uiState.mediaList.isEmpty() && !uiState.isLoading && !uiState.isError -> {
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
