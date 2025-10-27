package com.raihan.anicata.data.usecase

import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.data.repository.anime.AnimeSeasonListRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonYearRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import java.util.Calendar

/*
class GetSeasonalUseCase(
    private val nowRepository: AnimeSeasonNowRepository,
    private val seasonListRepository: AnimeSeasonListRepository
) {

    */
/**
     * Tugas 1: Mengambil data untuk filter (Tahun & Musim).
     * Logika ini dipindahkan dari init ViewModel.
     *//*

    fun getSeasonFilters(): Flow<ResultWrapper<SeasonFilterData>> {
        return seasonListRepository.getSeasonListAnime().map { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    // 1. Ambil data asli (mungkin huruf kecil)
                    val originalSeasonData = result.payload ?: emptyList()

                    if (originalSeasonData.isNotEmpty()) {
                        // 2. Buat list BARU di mana semua string 'seasons' di dalamnya sudah dikapitalisasi
                        val capitalizedSeasonData = originalSeasonData.map { seasonList ->
                            // 2a. Ubah list string ["fall", "summer"]
                            val capitalizedSeasons = seasonList.seasons.map { seasonName ->
                                seasonName.replaceFirstChar { it.uppercase() }
                            }
                            // 2b. Kembalikan data class yang disalin dengan list seasons yang baru
                            seasonList.copy(seasons = capitalizedSeasons)
                        }

                        // 3. Mulai sekarang, gunakan 'capitalizedSeasonData' UNTUK SEMUANYA
                        val years = capitalizedSeasonData.map { it.year.toString() }
                        val defaultYearData = capitalizedSeasonData.first()
                        val defaultYear = defaultYearData.year.toString()

                        // 4. Data ini ('defaultSeasons') sekarang sudah ["Fall", "Summer"], dsb.
                        val defaultSeasons = defaultYearData.seasons
                        val defaultSeason = defaultSeasons.firstOrNull() ?: ""

                        ResultWrapper.Success(
                            SeasonFilterData(
                                // 5. Simpan data yang SUDAH BERSIH (kapital)
                                seasonListData = capitalizedSeasonData,
                                yearOptions = years,
                                selectedYear = defaultYear,
                                seasonOptions = defaultSeasons,
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

    */
/**
     * Tugas 2: Mengambil daftar anime berdasarkan filter.
     * Logika ini dipindahkan dari onUpdateFilter ViewModel.
     *//*

    fun getSeasonalAnime(params: AnimeFilterParams): Flow<ResultWrapper<List<SeasonAnimeNow>>> {
        val filterType = params.filterType
        val filterStatus = params.filterStatus

        // Seluruh logika 'when' dan 'zip' dipindahkan ke sini
        return when (filterStatus) {
            "New" -> {
                nowRepository.getSeasonNowAnimeList(
                    filter = filterType,
                    continuing = false
                )
            }
            "All" -> {
                nowRepository.getSeasonNowAnimeList(
                    filter = filterType,
                    continuing = true
                )
            }
            "Continuing" -> {
                val allFlow = nowRepository.getSeasonNowAnimeList(
                    filter = filterType,
                    continuing = true
                )
                val newFlow = nowRepository.getSeasonNowAnimeList(
                    filter = filterType,
                    continuing = false
                )

                allFlow.zip(newFlow) { allResult, newResult ->
                    if (allResult is ResultWrapper.Loading || newResult is ResultWrapper.Loading) {
                        ResultWrapper.Loading()
                    } else if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {
                        val allList = allResult.payload ?: emptyList()
                        val newList = newResult.payload ?: emptyList()
                        val newIds = newList.map { it.id }.toSet()
                        val continuingList = allList.filter { it.id !in newIds }
                        ResultWrapper.Success(continuingList)
                    } else if (allResult is ResultWrapper.Error) {
                        allResult
                    } else if (newResult is ResultWrapper.Error) {
                        newResult
                    } else {
                        ResultWrapper.Error(Exception("Failed to zip flows (Idle/Empty state)"))
                    }
                }
            }
            // Fallback jika status tidak dikenali
            else -> nowRepository.getSeasonNowAnimeList(filterType, false)
        }
    }
}

*/
/**
 * Data class helper untuk membawa parameter filter dari ViewModel ke UseCase.
 *//*

data class AnimeFilterParams(
    val filterType: String,
    val filterStatus: String
    // Anda bisa tambahkan year dan season di sini jika API-nya nanti mendukung
)

*/
/**
 * Data class helper untuk membawa hasil filter dari UseCase ke ViewModel.
 *//*

data class SeasonFilterData(
    val seasonListData: List<SeasonAnimeList>,
    val yearOptions: List<String>,
    val selectedYear: String,
    val seasonOptions: List<String>,
    val selectedSeason: String
)*/

/*class GetSeasonalUseCase(
    private val yearRepository: AnimeSeasonYearRepository,
    private val seasonListRepository: AnimeSeasonListRepository
) {

    *//**
     * Tugas 1: Mengambil data untuk filter (Tahun & Musim).
     * Logika ini dipindahkan dari init ViewModel.
     *//*
    fun getSeasonFilters(): Flow<ResultWrapper<SeasonFilterData>> {
        return seasonListRepository.getSeasonListAnime().map { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    // 1. Ambil data asli (mungkin huruf kecil)
                    val originalSeasonData = result.payload ?: emptyList()

                    if (originalSeasonData.isNotEmpty()) {
                        // 2. Buat list BARU di mana semua string 'seasons' di dalamnya sudah dikapitalisasi
                        val capitalizedSeasonData = originalSeasonData.map { seasonList ->
                            // 2a. Ubah list string ["fall", "summer"]
                            val capitalizedSeasons = seasonList.seasons.map { seasonName ->
                                seasonName.replaceFirstChar { it.uppercase() }
                            }
                            // 2b. Kembalikan data class yang disalin dengan list seasons yang baru
                            seasonList.copy(seasons = capitalizedSeasons)
                        }

                        // 3. Mulai sekarang, gunakan 'capitalizedSeasonData' UNTUK SEMUANYA
                        val years = capitalizedSeasonData.map { it.year.toString() }
                        val defaultYearData = capitalizedSeasonData.first()
                        val defaultYear = defaultYearData.year.toString()

                        // 4. Data ini ('defaultSeasons') sekarang sudah ["Fall", "Summer"], dsb.
                        val defaultSeasons = defaultYearData.seasons
                        val defaultSeason = defaultSeasons.firstOrNull() ?: ""

                        ResultWrapper.Success(
                            SeasonFilterData(
                                // 5. Simpan data yang SUDAH BERSIH (kapital)
                                seasonListData = capitalizedSeasonData,
                                yearOptions = years,
                                selectedYear = defaultYear,
                                seasonOptions = defaultSeasons,
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

    *//**
     * Tugas 2: Mengambil daftar anime berdasarkan filter.
     * Logika ini dipindahkan dari onUpdateFilter ViewModel.
     *//*
    fun getSeasonalAnime(params: AnimeFilterParams): Flow<ResultWrapper<List<SeasonAnimeYear>>> {

        val year = params.year
        val season = params.season
        val filterType = params.filterType
        val filterStatus = params.filterStatus

        // Seluruh logika 'when' dan 'zip' dipindahkan ke sini
        return when (filterStatus) {
            "New" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false
                )
            }
            "All" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true
                )
            }
            "Continuing" -> {
                val allFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true
                )
                val newFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false
                )

                allFlow.zip(newFlow) { allResult, newResult ->
                    if (allResult is ResultWrapper.Loading || newResult is ResultWrapper.Loading) {
                        ResultWrapper.Loading()
                    } else if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {
                        val allList = allResult.payload ?: emptyList()
                        val newList = newResult.payload ?: emptyList()
                        val newIds = newList.map { it.id }.toSet()
                        val continuingList = allList.filter { it.id !in newIds }
                        ResultWrapper.Success(continuingList)
                    } else if (allResult is ResultWrapper.Error) {
                        allResult
                    } else if (newResult is ResultWrapper.Error) {
                        newResult
                    } else {
                        ResultWrapper.Error(Exception("Failed to zip flows (Idle/Empty state)"))
                    }
                }
            }
            // Fallback jika status tidak dikenali
            else -> yearRepository.getSeasonAnimeYearList(
                year = year,
                season = season,
                filter = filterType,
                continuing = false
            )
        }
    }
}

*//**
 * Data class helper untuk membawa parameter filter dari ViewModel ke UseCase.
 *//*
data class AnimeFilterParams(
    val year: Int,
    val season: String,
    val filterType: String,
    val filterStatus: String
    // Anda bisa tambahkan year dan season di sini jika API-nya nanti mendukung
)

*//**
 * Data class helper untuk membawa hasil filter dari UseCase ke ViewModel.
 *//*
data class SeasonFilterData(
    val seasonListData: List<SeasonAnimeList>,
    val yearOptions: List<String>,
    val selectedYear: String,
    val seasonOptions: List<String>,
    val selectedSeason: String
)*/

class GetSeasonalUseCase(
    private val yearRepository: AnimeSeasonYearRepository,
    private val seasonListRepository: AnimeSeasonListRepository
) {

    /**
     * Tugas 1: Mengambil data untuk filter (Tahun & Musim).
     * Logika ini dipindahkan dari init ViewModel.
     */
    fun getSeasonFilters(): Flow<ResultWrapper<SeasonFilterData>> {
        return seasonListRepository.getSeasonListAnime().map { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    // 1. Ambil data asli (mungkin huruf kecil)
                    val originalSeasonData = result.payload ?: emptyList()

                    if (originalSeasonData.isNotEmpty()) {

                        // Dapatkan tahun dan bulan saat ini
                        val calendar = Calendar.getInstance()
                        val currentYear = calendar.get(Calendar.YEAR)
                        val currentMonth = calendar.get(Calendar.MONTH)

                        // Tentukan string musim (season) berdasarkan bulan saat ini
                        // (Mengikuti standar musim anime: Winter: Jan-Mar, Spring: Apr-Jun, Summer: Jul-Sep, Fall: Okt-Des)

                        val currentSeasonString = when (currentMonth) {
                            Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> "Winter"
                            Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> "Spring"
                            Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> "Summer"
                            Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> "Fall"
                            else -> "Unknown"
                        }

                        // 2. Buat list BARU di mana semua string 'seasons' di dalamnya sudah dikapitalisasi
                        val capitalizedSeasonData = originalSeasonData.map { seasonList ->
                            // 2a. Ubah list string ["fall", "summer"]
                            val capitalizedSeasons = seasonList.seasons.map { seasonName ->
                                seasonName.replaceFirstChar { it.uppercase() }
                            }
                            // 2b. Kembalikan data class yang disalin dengan list seasons yang baru
                            seasonList.copy(seasons = capitalizedSeasons)
                        }

                        // 3. Mulai sekarang, gunakan 'capitalizedSeasonData' UNTUK SEMUANYA
                        val years = capitalizedSeasonData.map { it.year.toString() }


                        // Cari data untuk TAHUN INI.
                        // Jika tidak ada (misal API belum update), baru pakai data .first() (2026)
                        val defaultYearData = capitalizedSeasonData.find { it.year == currentYear }
                            ?: capitalizedSeasonData.first() // Fallback ke logika lama

                        val defaultYear = defaultYearData.year.toString()

                        // 6. Ambil daftar musim untuk tahun yang dipilih
                        val defaultSeasons = defaultYearData.seasons // Ini sudah dikapitalisasi

                        // 7. Cari MUSIM SAAT INI (misal "Fall") di dalam daftar musim.
                        // Jika tidak ada (misal API belum update), baru pakai .firstOrNull()
                        val defaultSeason = defaultSeasons.find { it.equals(currentSeasonString, ignoreCase = true) }
                            ?: defaultSeasons.firstOrNull() // Fallback ke logika lama
                            ?: "" // Fallback akhir

                        ResultWrapper.Success(
                            SeasonFilterData(
                                // 5. Simpan data yang SUDAH BERSIH (kapital)
                                seasonListData = capitalizedSeasonData,
                                yearOptions = years,
                                selectedYear = defaultYear,
                                seasonOptions = defaultSeasons,
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
     * Tugas 2: Mengambil daftar anime berdasarkan filter.
     * Logika ini dipindahkan dari onUpdateFilter ViewModel.
     */
    fun getSeasonalAnime(params: AnimeFilterParams): Flow<ResultWrapper<List<SeasonAnimeYear>>> {

        val year = params.year
        val season = params.season
        val filterType = params.filterType
        val filterStatus = params.filterStatus

        // Seluruh logika 'when' dan 'zip' dipindahkan ke sini
        return when (filterStatus) {
            "New" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false
                )
            }
            "All" -> {
                yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true
                )
            }
            "Continuing" -> {
                val allFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = true
                )
                val newFlow = yearRepository.getSeasonAnimeYearList(
                    year = year,
                    season = season,
                    filter = filterType,
                    continuing = false
                )

                allFlow.zip(newFlow) { allResult, newResult ->
                    if (allResult is ResultWrapper.Loading || newResult is ResultWrapper.Loading) {
                        ResultWrapper.Loading()
                    } else if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {
                        val allList = allResult.payload ?: emptyList()
                        val newList = newResult.payload ?: emptyList()
                        val newIds = newList.map { it.id }.toSet()
                        val continuingList = allList.filter { it.id !in newIds }
                        ResultWrapper.Success(continuingList)
                    } else if (allResult is ResultWrapper.Error) {
                        allResult
                    } else if (newResult is ResultWrapper.Error) {
                        newResult
                    } else {
                        ResultWrapper.Error(Exception("Failed to zip flows (Idle/Empty state)"))
                    }
                }
            }
            // Fallback jika status tidak dikenali
            else -> yearRepository.getSeasonAnimeYearList(
                year = year,
                season = season,
                filter = filterType,
                continuing = false
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
    val filterStatus: String
    // Anda bisa tambahkan year dan season di sini jika API-nya nanti mendukung
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
