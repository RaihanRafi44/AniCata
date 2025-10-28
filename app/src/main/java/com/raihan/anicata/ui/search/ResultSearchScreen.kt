package com.raihan.anicata.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.rememberPaginationState
import org.koin.androidx.compose.koinViewModel

/*
@Composable
fun ResultSearchScreen(
    searchQuery: String
) {
    // Wadah utama dengan latar belakang hijau mint seperti pada gambar
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFE0F2F1)) // Warna latar belakang hijau mint
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Memanggil Composable header dari file terpisah
        //ResultSearchHeader(searchQuery = "Fullmetal Alchemist: Brotherhood The Movie - The Sacred Star of Milos")
        ResultSearchHeader(searchQuery = searchQuery)

        Spacer(modifier = Modifier.height(16.dp))

        // Memanggil layout daftar hasil pencarian dari file ResultList.kt
        SearchResultListLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSearchScreenPreview() {
    MaterialTheme {
        ResultSearchScreen(searchQuery = "Preview Search Query")
    }
}*/

/*@Composable
fun ResultSearchScreen(
    searchQuery: String,
    // Inject ViewModel
    viewModel: ResultSearchViewModel = koinViewModel()
) {
    // Ambil state dari ViewModel
    val uiState by viewModel.searchState.collectAsState()

    // Panggil searchMedia saat composable pertama kali dimuat
    // atau saat searchQuery berubah
    LaunchedEffect(key1 = searchQuery) {
        viewModel.searchMedia(searchQuery)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ResultSearchHeader(searchQuery = searchQuery)

        Spacer(modifier = Modifier.height(16.dp))

        // Kirim uiState ke layout
        SearchResultListLayout(uiState = uiState)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSearchScreenPreview() {
    MaterialTheme {
        ResultSearchScreen(searchQuery = "Preview Search Query")
    }
}*/

@Composable
fun ResultSearchScreen(
    searchQuery: String,
    viewModel: ResultSearchViewModel = koinViewModel()
) {
    // --- AMBIL STATE BARU (UI STATE) ---
    val uiState by viewModel.uiState.collectAsState()
    val paginationInfo = uiState.paginationInfo
    // --- AKHIR STATE BARU ---

    // Ambil state result-nya langsung
    val result = uiState.result

    // Ingat pagination state dari PagingControls.kt
    val paginationState = rememberPaginationState(
        totalPages = paginationInfo.totalPages
    )

    // Panggil searchMedia saat searchQuery berubah (hanya 1x saat masuk)
    LaunchedEffect(key1 = searchQuery) {
        viewModel.searchMedia(searchQuery)
    }

    // Sinkronkan state VM ke state Composable
    // Jika VM memuat halaman baru (misal: halaman 1), update state lokal
    LaunchedEffect(key1 = paginationInfo.currentPage) {
        paginationState.onPageChange(paginationInfo.currentPage)
    }

    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ResultSearchHeader(searchQuery = searchQuery)

        Spacer(modifier = Modifier.height(16.dp))

        // Kirim HANYA result-nya ke layout list
        SearchResultListLayout(uiState = uiState.result)

        // --- TAMBAHKAN KONTROL PAGINATION ---
        // Tampilkan pagination jika total halaman > 1
        // dan state BUKAN Idle atau Loading
        val showPagination = paginationInfo.totalPages > 1 &&
                (uiState.result is ResultWrapper.Success || uiState.result is ResultWrapper.Empty)

        if (showPagination) {
            Spacer(modifier = Modifier.height(16.dp))
            PaginationControls(
                currentPage = paginationState.currentPage,
                startPage = paginationState.startPage,
                totalPages = paginationState.totalPages,
                onPageChange = { newPage ->
                    // Panggil ViewModel untuk fetch data halaman baru
                    viewModel.onPageChange(newPage)
                },
                visiblePages = 3 // Sesuai default di PaginationState
            )
        }
        // --- AKHIR KONTROL PAGINATION ---

        Spacer(modifier = Modifier.height(80.dp)) // Beri ruang di bawah
    }*/

    // Gunakan Box sebagai root (seperti TopAnimeScreen)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // --- LAPISAN 1: KONTEN SCROLLABLE ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()), // Scrollable
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ResultSearchHeader(searchQuery = searchQuery)

            Spacer(modifier = Modifier.height(16.dp))

            // Hanya tampilkan list jika Success dan tidak kosong
            if (result is ResultWrapper.Success && result.payload!!.isNotEmpty()) {
                SearchResultListLayout(items = result.payload)
            }

            // Tampilkan pagination jika total halaman > 1
            // dan state BUKAN Loading
            val showPagination = paginationInfo.totalPages > 1 &&
                    (result !is ResultWrapper.Loading)

            if (showPagination) {
                Spacer(modifier = Modifier.height(16.dp))
                PaginationControls(
                    currentPage = paginationState.currentPage,
                    startPage = paginationState.startPage,
                    totalPages = paginationState.totalPages,
                    onPageChange = { newPage ->
                        viewModel.onPageChange(newPage)
                    },
                    visiblePages = 3
                )
            }

            Spacer(modifier = Modifier.height(80.dp)) // Beri ruang di bawah
        }

        // --- LAPISAN 2: OVERLAY (Loading, Error, Empty) ---
        // (Sama seperti logika when di TopAnimeScreen)
        when (result) {
            is ResultWrapper.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center) // Center di dalam Box
                )
            }
            is ResultWrapper.Error -> {
                Text(
                    text = result.exception?.message ?: "An unknown error occurred.",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp)
                )
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "No results found for this query.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center) // Center di dalam Box
                        .padding(horizontal = 32.dp)
                )
            }
            // Untuk Success dan Idle, tidak perlu tampilkan apa-apa di overlay
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSearchScreenPreview() {
    MaterialTheme {
        ResultSearchScreen(searchQuery = "Preview Search Query")
    }
}
