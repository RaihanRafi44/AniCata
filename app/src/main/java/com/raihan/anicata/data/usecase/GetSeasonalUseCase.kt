package com.raihan.anicata.data.usecase

import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.year.SeasonalResultData
import com.raihan.anicata.data.repository.anime.AnimeSeasonListRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonUpcomingRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonYearRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import java.util.Calendar

class GetSeasonalUseCase(
    private val yearRepository: AnimeSeasonYearRepository,
    private val seasonListRepository: AnimeSeasonListRepository,
    private val upcomingRepository: AnimeSeasonUpcomingRepository
) {

    /**
     * Mengambil data untuk filter (Tahun & Musim).
     */
    fun getSeasonFilters(): Flow<ResultWrapper<SeasonFilterData>> {
        return seasonListRepository.getSeasonListAnime().map { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    val originalSeasonData = result.payload ?: emptyList()

                    if (originalSeasonData.isNotEmpty()) {
                        val calendar = Calendar.getInstance()
                        val currentYear = calendar.get(Calendar.YEAR)
                        val currentMonth = calendar.get(Calendar.MONTH)

                        val currentSeasonString = when (currentMonth) {
                            Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> "Winter"
                            Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> "Spring"
                            Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> "Summer"
                            Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> "Fall"
                            else -> "Unknown"
                        }

                        val capitalizedSeasonData = originalSeasonData.map { seasonList ->
                            val capitalizedSeasons = seasonList.seasons.map { seasonName ->
                                seasonName.replaceFirstChar { it.uppercase() }
                            }
                            seasonList.copy(seasons = capitalizedSeasons)
                        }

                        val years = capitalizedSeasonData.map { it.year.toString() }

                        val defaultYearData = capitalizedSeasonData.find { it.year == currentYear }
                            ?: capitalizedSeasonData.first()

                        val defaultYear = defaultYearData.year.toString()
                        val defaultSeasons = defaultYearData.seasons
                        val seasonsWithUpcoming = if (defaultSeasons.none { it.equals("Upcoming", ignoreCase = true) }) {
                            defaultSeasons + "Upcoming"
                        } else {
                            defaultSeasons
                        }

                        val defaultSeason = seasonsWithUpcoming.find { it.equals(currentSeasonString, ignoreCase = true) }
                            ?: seasonsWithUpcoming.firstOrNull()
                            ?: ""

                        ResultWrapper.Success(
                            SeasonFilterData(
                                seasonListData = capitalizedSeasonData,
                                yearOptions = years,
                                selectedYear = defaultYear,
                                seasonOptions = seasonsWithUpcoming,
                                selectedSeason = defaultSeason
                            )
                        )
                    } else {
                        ResultWrapper.Empty()
                    }
                }
                is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                is ResultWrapper.Loading -> ResultWrapper.Loading()
                is ResultWrapper.Empty -> ResultWrapper.Empty()
                is ResultWrapper.Idle -> ResultWrapper.Idle()
            }
        }
    }

    /**
     * Mengambil daftar anime berdasarkan filter, halaman, & order_by/sort Tenrai API.
     */
    fun getSeasonalAnime(params: AnimeFilterParams): Flow<ResultWrapper<SeasonalResultData>> {

        val year = params.year
        val season = params.season
        val filterType = params.filterType
        val filterStatus = params.filterStatus
        val page = params.page
        val orderBy = params.orderBy
        val sort = params.sort

        if (season.equals("upcoming", ignoreCase = true)) {
            val isContinuing = filterStatus.equals("Continuing", ignoreCase = true) || filterStatus.equals("All", ignoreCase = true)
            return upcomingRepository.getSeasonUpcomingSeasonalData(
                filter = filterType,
                continuing = isContinuing,
                page = page
            )
        }

        return when (filterStatus) {
            "New" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false,
                    page = page,
                    orderBy = orderBy,
                    sort = sort
                )
            }
            "All" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true,
                    page = page,
                    orderBy = orderBy,
                    sort = sort
                )
            }
            "Continuing" -> {
                val allFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true,
                    page = page,
                    orderBy = orderBy,
                    sort = sort
                )
                val newFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false,
                    page = page,
                    orderBy = orderBy,
                    sort = sort
                )

                allFlow.zip(newFlow) { allResult, newResult ->
                    if (allResult is ResultWrapper.Loading || newResult is ResultWrapper.Loading) {
                        ResultWrapper.Loading()
                    } else if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {
                        val allData = allResult.payload ?: SeasonalResultData(emptyList())
                        val newData = newResult.payload ?: SeasonalResultData(emptyList())
                        val newIds = newData.animeList.map { it.id }.toSet()
                        val continuingList = allData.animeList.filter { it.id !in newIds }
                        ResultWrapper.Success(
                            SeasonalResultData(
                                animeList = continuingList,
                                totalItems = allData.totalItems,
                                hasNextPage = allData.hasNextPage
                            )
                        )
                    } else if (allResult is ResultWrapper.Error) {
                        allResult
                    } else if (newResult is ResultWrapper.Error) {
                        newResult
                    } else {
                        ResultWrapper.Error(Exception("Failed to zip flows (Idle/Empty state)"))
                    }
                }
            }
            else -> yearRepository.getSeasonAnimeYearList(
                year = year,
                season = season,
                filter = filterType,
                continuing = false,
                page = page,
                orderBy = orderBy,
                sort = sort
            )
        }
    }
}

/**
 * Data class helper untuk membawa parameter filter dari ViewModel ke UseCase.
 */
data class AnimeFilterParams(
    val year: Int,
    val season: String,
    val filterType: String,
    val filterStatus: String,
    val page: Int = 1,
    val orderBy: String? = null,
    val sort: String? = null
)

/**
 * Data class helper untuk membawa hasil filter dari UseCase ke ViewModel.
 */
data class SeasonFilterData(
    val seasonListData: List<SeasonAnimeList>,
    val yearOptions: List<String>,
    val selectedYear: String,
    val seasonOptions: List<String>,
    val selectedSeason: String
)
