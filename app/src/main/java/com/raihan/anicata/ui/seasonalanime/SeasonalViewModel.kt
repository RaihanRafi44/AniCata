package com.raihan.anicata.ui.seasonalanime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.data.usecase.AnimeFilterParams
import com.raihan.anicata.data.usecase.GetSeasonalUseCase
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeYear>> = ResultWrapper.Loading(),
    val rawAnimeList: List<SeasonAnimeYear> = emptyList(),
    val totalShowsCount: Int = 0,
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
    val sortOptions: List<String> = emptyList(),
    val selectedSort: String = "",
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
    val isNextPageLoading: Boolean = false
)

class SeasonalViewModel(
    private val getSeasonalDataUseCase: GetSeasonalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")
        val statuses = listOf("New", "Continuing", "All")
        val sortOptions = listOf("Score", "Popularity", "Title (A-Z)", "Episodes")

        _uiState.update {
            it.copy(
                typeOptions = types,
                selectedType = types.first(),
                statusOptions = statuses,
                selectedStatus = statuses.first(),
                sortOptions = sortOptions,
                selectedSort = sortOptions.first()
            )
        }

        loadSeasonFilters()
    }

    private fun loadSeasonFilters() {
        viewModelScope.launch {
            getSeasonalDataUseCase.getSeasonFilters().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val filterData = result.payload!!

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

                        onUpdateFilter()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(isLoadingFilters = false) }
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoadingFilters = true) }
                    }
                    else -> {}
                }
            }
        }
    }

    // --- Event Handlers ---
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

    fun onSeasonChange(newSeason: String) { _uiState.update { it.copy(selectedSeason = newSeason) } }
    fun onTypeChange(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun onStatusChange(newStatus: String) { _uiState.update { it.copy(selectedStatus = newStatus) } }

    fun onSortChange(newSort: String) {
        _uiState.update { it.copy(selectedSort = newSort) }
        onUpdateFilter()
    }

    fun onUpdateFilter() {
        fetchJob?.cancel()

        _uiState.update {
            it.copy(
                apiResult = ResultWrapper.Loading(),
                rawAnimeList = emptyList(),
                totalShowsCount = 0,
                currentPage = 1,
                hasNextPage = false,
                isNextPageLoading = false
            )
        }

        val currentState = _uiState.value
        val year = currentState.selectedYear.toIntOrNull()
        val season = currentState.selectedSeason
        val filterType = currentState.selectedType

        if (year == null || season.isBlank()) {
            if (!_uiState.value.isLoadingFilters) {
                _uiState.update { it.copy(apiResult = ResultWrapper.Error(Exception("Invalid year or season."))) }
            }
            return
        }

        val (orderBy, sort) = mapSortToOrderByAndSort(currentState.selectedSort)

        val params = AnimeFilterParams(
            year = year,
            season = season.lowercase(),
            filterType = filterType.lowercase(),
            filterStatus = currentState.selectedStatus,
            page = 1,
            orderBy = orderBy,
            sort = sort
        )

        fetchJob = viewModelScope.launch {
            getSeasonalDataUseCase.getSeasonalAnime(params)
                .collect { result ->
                    if (result !is ResultWrapper.Loading) {
                        if (result is ResultWrapper.Success) {
                            val seasonalData = result.payload
                            val rawList = seasonalData?.animeList ?: emptyList()
                            val totalCount = seasonalData?.totalItems ?: rawList.size
                            val hasNext = seasonalData?.hasNextPage ?: false

                            _uiState.update {
                                it.copy(
                                    rawAnimeList = rawList,
                                    totalShowsCount = totalCount,
                                    apiResult = ResultWrapper.Success(rawList),
                                    currentPage = 1,
                                    hasNextPage = hasNext,
                                    isNextPageLoading = false
                                )
                            }
                        } else if (result is ResultWrapper.Error) {
                            _uiState.update { it.copy(apiResult = ResultWrapper.Error(result.exception)) }
                        } else if (result is ResultWrapper.Empty) {
                            _uiState.update { it.copy(apiResult = ResultWrapper.Empty()) }
                        }
                    }
                }
        }
    }

    fun loadNextPage() {
        val currentState = _uiState.value
        if (!currentState.hasNextPage || currentState.isNextPageLoading || currentState.apiResult !is ResultWrapper.Success) return

        val year = currentState.selectedYear.toIntOrNull() ?: return
        val season = currentState.selectedSeason
        if (season.isBlank()) return

        val nextPage = currentState.currentPage + 1
        _uiState.update { it.copy(isNextPageLoading = true) }

        val (orderBy, sort) = mapSortToOrderByAndSort(currentState.selectedSort)

        val params = AnimeFilterParams(
            year = year,
            season = season.lowercase(),
            filterType = currentState.selectedType.lowercase(),
            filterStatus = currentState.selectedStatus,
            page = nextPage,
            orderBy = orderBy,
            sort = sort
        )

        viewModelScope.launch {
            getSeasonalDataUseCase.getSeasonalAnime(params)
                .collect { result ->
                    if (result !is ResultWrapper.Loading) {
                        if (result is ResultWrapper.Success) {
                            val seasonalData = result.payload
                            val newItems = seasonalData?.animeList ?: emptyList()
                            val existingIds = _uiState.value.rawAnimeList.map { it.id }.toSet()
                            val uniqueNewItems = newItems.filter { it.id !in existingIds }

                            val combinedList = _uiState.value.rawAnimeList + uniqueNewItems
                            val hasMore = seasonalData?.hasNextPage ?: false

                            _uiState.update {
                                it.copy(
                                    rawAnimeList = combinedList,
                                    apiResult = ResultWrapper.Success(combinedList),
                                    currentPage = nextPage,
                                    hasNextPage = hasMore,
                                    isNextPageLoading = false
                                )
                            }
                        } else {
                            _uiState.update { it.copy(isNextPageLoading = false) }
                        }
                    }
                }
        }
    }

    private fun mapSortToOrderByAndSort(sortBy: String): Pair<String?, String?> {
        return when (sortBy) {
            "Score" -> Pair("score", "desc")
            "Popularity" -> Pair("members", "desc")
            "Title (A-Z)" -> Pair("title", "asc")
            "Episodes" -> Pair("episodes", "desc")
            else -> Pair("score", "desc")
        }
    }
}
