package com.raihan.anicata.ui.seasonalanime

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

/*@Composable
fun SeasonalScreen(
    viewModel: SeasonalViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Panggilan ke AnimeFilterGroup diperbarui untuk mencocokkan parameter baru
        AnimeFilterGroup(
            // Baris 1
            selectedYear = uiState.selectedYear,
            onYearChange = viewModel::onYearChange,
            yearOptions = uiState.yearOptions,
            selectedSeason = uiState.selectedSeason,
            onSeasonChange = viewModel::onSeasonChange,
            seasonOptions = uiState.seasonOptions,

            // Baris 2
            selectedType = uiState.selectedType,
            onTypeChange = viewModel::onTypeChange,
            typeOptions = uiState.typeOptions,
            selectedStatus = uiState.selectedStatus,
            onStatusChange = viewModel::onStatusChange,
            statusOptions = uiState.statusOptions,

            onUpdateClick = viewModel::onUpdateFilter
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Blok 'when' untuk menampilkan hasil (tidak berubah)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            when (val apiResult = uiState.apiResult) {
                is ResultWrapper.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp)
                    )
                }
                is ResultWrapper.Success -> {
                    val animeList = apiResult.payload as? List<SeasonAnimeYear> ?: emptyList()
                    if (animeList.isNotEmpty()) {
                        AnimeListLayout(
                            animeList = animeList,
                            onAnimeClick = onAnimeClick
                        )
                    } else {
                        Text(
                            text = "No results found for this filter.",
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 32.dp)
                        )
                    }
                }
                // ... (sisa error handling tidak berubah)
                is ResultWrapper.Error -> {
                    val errorMessage = apiResult.message ?: apiResult.exception?.message ?: "Unknown error"
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    )
                }
                is ResultWrapper.Empty -> {
                    Text(
                        text = "No results found.",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 32.dp)
                    )
                }
                is ResultWrapper.Idle -> {}
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}*/

@Composable
fun SeasonalScreen(
    viewModel: SeasonalViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // --- 2. LAPISAN 1: Konten Scrollable ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AnimeFilterGroup(
                // Baris 1
                selectedYear = uiState.selectedYear,
                onYearChange = viewModel::onYearChange,
                yearOptions = uiState.yearOptions,
                selectedSeason = uiState.selectedSeason,
                onSeasonChange = viewModel::onSeasonChange,
                seasonOptions = uiState.seasonOptions,

                // Baris 2
                selectedType = uiState.selectedType,
                onTypeChange = viewModel::onTypeChange,
                typeOptions = uiState.typeOptions,
                selectedStatus = uiState.selectedStatus,
                onStatusChange = viewModel::onStatusChange,
                statusOptions = uiState.statusOptions,

                onUpdateClick = viewModel::onUpdateFilter
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- 3. HAPUS Box pembungkus 'when' ---
            // 'when' sekarang akan meletakkan Composable-nya
            // langsung di dalam Column.
            when (val apiResult = uiState.apiResult) {
                is ResultWrapper.Loading -> {
                    // Jangan tampilkan apa-apa di sini,
                    // 'when' di lapisan overlay akan menanganinya.
                }
                is ResultWrapper.Success -> {
                    val animeList = apiResult.payload as? List<SeasonAnimeYear> ?: emptyList()
                    if (animeList.isNotEmpty()) {
                        AnimeListLayout(
                            animeList = animeList,
                            onAnimeClick = onAnimeClick
                        )
                    } else {
                        // Tampilkan pesan "No results" di dalam Column
                        Text(
                            text = "No results found for this filter.",
                            modifier = Modifier
                                .fillMaxWidth() // <-- Ubah modifier
                                .padding(top = 32.dp),
                            textAlign = TextAlign.Center // <-- Tambahkan ini
                        )
                    }
                }
                is ResultWrapper.Error -> {
                    // Tampilkan error di dalam Column
                    val errorMessage = apiResult.message ?: apiResult.exception?.message ?: "Unknown error"
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth() // <-- Ubah modifier
                            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                        textAlign = TextAlign.Center // <-- Tambahkan ini
                    )
                }
                is ResultWrapper.Empty -> {
                    // Tampilkan pesan empty di dalam Column
                    Text(
                        text = "No results found.",
                        modifier = Modifier
                            .fillMaxWidth() // <-- Ubah modifier
                            .padding(top = 32.dp),
                        textAlign = TextAlign.Center // <-- Tambahkan ini
                    )
                }
                is ResultWrapper.Idle -> {}
            }
            Spacer(modifier = Modifier.height(80.dp)) //
        }

        // --- 4. LAPISAN 2: Overlay (Loading, Error, Empty) ---
        // (Logika ini meniru AllListsScreen)
        when (val apiResult = uiState.apiResult) {
            is ResultWrapper.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Error -> {
                // Tampilkan error di overlay HANYA jika list kosong
                // (Jika list ada, error ditampilkan di atas)
                if (uiState.apiResult.payload == null) {
                    val errorMessage = apiResult.message ?: apiResult.exception?.message ?: "Unknown error"
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 32.dp)
                    )
                }
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "No results found.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 32.dp)
                )
            }
            // Untuk Success dan Idle, tidak perlu tampilkan apa-apa di overlay
            else -> {}
        }
    }
}
