package com.raihan.anicata.ui.seasonalanime

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeasonalScreen(
    viewModel: SeasonalViewModel = koinViewModel()
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
                        AnimeListLayout(animeList = animeList)
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
    }
}
