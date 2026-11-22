package com.raihan.anicata.data.usecase

import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.utils.ResultWrapper

data class SeasonalUiState(
    val apiResult: ResultWrapper<Pair<List<SeasonAnimeYear>, Int>> = ResultWrapper.Loading(),
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

    val currentPage: Int = 1
)