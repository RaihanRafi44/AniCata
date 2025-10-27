package com.raihan.anicata.ui.seasonalanime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.usecase.AnimeFilterParams
import com.raihan.anicata.data.usecase.GetSeasonalUseCase
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeYear>> = ResultWrapper.Loading(),
    val seasonListData: List<SeasonAnimeList> = emptyList(),
    val isLoadingFilters: Boolean = true,
    val yearOptions: List<String> = emptyList(),
    val selectedYear: String = "",
    val seasonOptions: List<String> = emptyList(),
    val selectedSeason: String = "",
    val typeOptions: List<String> = emptyList(),
    val selectedType: String = "",
    val statusOptions: List<String> = emptyList(),
    val selectedStatus: String = "",
)

class SeasonalViewModel(
    // 1. Ubah constructor! Hanya butuh SATU UseCase
    private val getSeasonalDataUseCase: GetSeasonalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        // Data hardcoded (Tipe & Status) tetap di sini
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")
        val statuses = listOf("New", "Continuing", "All")

        _uiState.update {
            it.copy(
                typeOptions = types,
                selectedType = types.first(),
                statusOptions = statuses,
                selectedStatus = statuses.first(),
            )
        }

        // Panggil fungsi load filter yang baru
        loadSeasonFilters()
    }

    /**
     * 2. Fungsi init (loadSeasonFilters) menjadi lebih sederhana
     */
    private fun loadSeasonFilters() {
        viewModelScope.launch {
            // Panggil UseCase, bukan repository
            getSeasonalDataUseCase.getSeasonFilters().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val filterData = result.payload!! // Kita tahu ini tidak null dari logika UseCase

                        _uiState.update {
                            it.copy(
                                isLoadingFilters = false,
                                seasonListData = filterData.seasonListData,
                                yearOptions = filterData.yearOptions,
                                selectedYear = filterData.selectedYear,
                                seasonOptions = filterData.seasonOptions,
                                selectedSeason = filterData.selectedSeason
                            )
                        }

                        // Panggil onUpdateFilter HANYA setelah filter berhasil dimuat
                        onUpdateFilter()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(isLoadingFilters = false) }
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoadingFilters = true) }
                    }
                    else -> { /* Handle Empty/Idle jika perlu */ }
                }
            }
        }
    }

    // --- Event Handlers ---
    // onYearChange tidak berubah, karena ini murni logika UI-State
    fun onYearChange(newYear: String) {
        val newSeasons = _uiState.value.seasonListData
            .find { it.year.toString() == newYear }
            ?.seasons ?: emptyList()
        val newSelectedSeason = newSeasons.firstOrNull() ?: ""
        _uiState.update {
            it.copy(
                selectedYear = newYear,
                seasonOptions = newSeasons,
                selectedSeason = newSelectedSeason
            )
        }
    }

    // Handler lain tidak berubah
    fun onSeasonChange(newSeason: String) { _uiState.update { it.copy(selectedSeason = newSeason) } }
    fun onTypeChange(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun onStatusChange(newStatus: String) { _uiState.update { it.copy(selectedStatus = newStatus) } }

    /**
     * 3. Fungsi onUpdateFilter menjadi SANGAT sederhana
     */
    fun onUpdateFilter() {
        _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

        // Ambil nilai state saat ini
        val currentState = _uiState.value

        // Vqlidasi: Pastikan year dan season tidak kosong sebelum memanggil API
        val year = currentState.selectedYear.toIntOrNull()
        val season = currentState.selectedSeason
        val filterType = currentState.selectedType

        if (year == null || season.isBlank()) {
            // Jika filter belum siap (masih loading), jangan panggil API
            // Cukup update state ke Error atau biarkan Loading
            if (!_uiState.value.isLoadingFilters) {
                _uiState.update { it.copy(apiResult = ResultWrapper.Error(Exception("Invalid year or season."))) }
            }
            return
        }

        // Buat parameter untuk UseCase
        val params = AnimeFilterParams(
            year = year,
            season = season.lowercase(),
            filterType = filterType.lowercase(),
            filterStatus = currentState.selectedStatus
        )

        viewModelScope.launch {
            // Panggil UseCase, bukan repository
            getSeasonalDataUseCase.getSeasonalAnime(params)
                .collect { result ->
                    // Logika 'zip' dan 'when' sudah tidak ada lagi di sini!
                    if (result !is ResultWrapper.Loading) {
                        // Tipe 'result' sekarang adalah ResultWrapper<List<SeasonAnimeYear>>
                        _uiState.update { it.copy(apiResult = result) }
                    }
                }
        }
    }
}
