package com.raihan.anicata.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.mapper.media.toMediaItem
import com.raihan.anicata.data.model.media.MediaItem
import com.raihan.anicata.data.repository.anime.AnimeSearchRepository
import com.raihan.anicata.data.repository.manga.MangaSearchRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlin.collections.emptyList
import kotlin.math.max

// --- Data class (ResultSearchUiState dan PaginationInfo) tidak berubah ---
data class ResultSearchUiState(
    val result: ResultWrapper<List<MediaItem>> = ResultWrapper.Idle(),
    val paginationInfo: PaginationInfo = PaginationInfo()
)

data class PaginationInfo(
    val currentPage: Int = 1,
    val totalPages: Int = 1
)
// --- Akhir Data class ---

class ResultSearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultSearchUiState())
    val uiState: StateFlow<ResultSearchUiState> = _uiState.asStateFlow()

    private val _currentQuery = MutableStateFlow("")

    // --- PERUBAHAN DI SINI ---
    // Batas 16 item per page (10 anime + 6 manga)
    private val animeLimit = 10
    private val mangaLimit = 10
    // --- AKHIR PERUBAHAN ---

    // --- 1. TAMBAHKAN VARIABEL CACHE ---
    // Cache key akan berupa: "query=naruto&page=1"
    private val mediaCache = mutableMapOf<String, ResultWrapper<List<MediaItem>>>()
    private val paginationCache = mutableMapOf<String, PaginationInfo>()

    // --- 2. TAMBAHKAN FUNGSI CACHE KEY ---
    private fun getCacheKey(query: String, page: Int): String {
        return "query=$query&page=$page"
    }

    /**
     * Dipanggil oleh UI saat pertama kali mencari (dari SearchScreen).
     * Akan me-reset halaman ke 1.
     */
    fun searchMedia(query: String) {
        if (query == _currentQuery.value && _uiState.value.result !is ResultWrapper.Idle) return

        _currentQuery.value = query
        fetchPage(query, 1)
    }

    /**
     * Dipanggil oleh UI saat tombol pagination diklik.
     */
    fun onPageChange(page: Int) {
        if (page == _uiState.value.paginationInfo.currentPage && _uiState.value.result !is ResultWrapper.Loading) return

        fetchPage(_currentQuery.value, page)
    }

    /**
     * Logika internal untuk mengambil data dari repository.
     */
    private fun fetchPage(query: String, page: Int) {
        if (_uiState.value.result is ResultWrapper.Loading) return

        // --- 3. CEK CACHE DI AWAL ---
        val cacheKey = getCacheKey(query, page)
        if (mediaCache.containsKey(cacheKey)) {
            _uiState.value = ResultSearchUiState(
                result = mediaCache[cacheKey]!!,
                paginationInfo = paginationCache[cacheKey]!!
            )
            return // Hentikan, data sudah ada di cache
        }
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                result = ResultWrapper.Loading(),
                paginationInfo = _uiState.value.paginationInfo.copy(currentPage = page)
            )

            // --- PERUBAHAN DI SINI ---
            // Menggunakan limit 10 dan 10
            val animeFlow = animeRepository.getSearchAnimeList(
                query = query, limit = animeLimit, page = page,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )
            val mangaFlow = mangaRepository.getSearchMangaList(
                query = query, limit = mangaLimit, page = page,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )
            // --- AKHIR PERUBAHAN ---

            // Logika zip yang sudah diperbaiki
            animeFlow.zip(mangaFlow) { animeResult, mangaResult ->

                // 1. Tangani Loading/Idle
                if (animeResult is ResultWrapper.Loading || mangaResult is ResultWrapper.Loading) {
                    return@zip null
                }
                if (animeResult is ResultWrapper.Idle || mangaResult is ResultWrapper.Idle) {
                    return@zip null
                }

                // 2. Prioritaskan Error
                if (animeResult is ResultWrapper.Error) {
                    return@zip Pair(
                        ResultWrapper.Error<List<MediaItem>>(animeResult.exception),
                        PaginationInfo(page, 1)
                    )
                }
                if (mangaResult is ResultWrapper.Error) {
                    return@zip Pair(
                        ResultWrapper.Error<List<MediaItem>>(mangaResult.exception),
                        PaginationInfo(page, 1)
                    )
                }

                // 3. Proses Success/Empty
                val animeList = when (animeResult) {
                    is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    else -> emptyList()
                }

                val mangaList = when (mangaResult) {
                    is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    else -> emptyList()
                }

                // Total gabungan akan SELALU <= 16
                //val combinedList = animeList + mangaList

                val combinedList = (animeList + mangaList).distinctBy { it.id to it.itemType }
                val animePagination = when (animeResult) {
                    is ResultWrapper.Success -> animeResult.payload?.second
                    is ResultWrapper.Empty -> animeResult.payload?.second
                    else -> null
                }
                val mangaPagination = when (mangaResult) {
                    is ResultWrapper.Success -> mangaResult.payload?.second
                    is ResultWrapper.Empty -> mangaResult.payload?.second
                    else -> null
                }
                val animeTotal = animePagination ?: 0
                val mangaTotal = mangaPagination ?: 0
                val totalPages = max(animeTotal, mangaTotal).coerceAtLeast(1)
                val paginationInfo = PaginationInfo(currentPage = page, totalPages = totalPages)
                val resultWrapper = if (combinedList.isEmpty()) {
                    ResultWrapper.Empty<List<MediaItem>>()
                } else {
                    ResultWrapper.Success(combinedList)
                }
                return@zip Pair(resultWrapper, paginationInfo)

            }
                .filterNotNull() //
                .collect { (result, pagination) ->
                    // --- 5. SIMPAN KE CACHE ---
                    mediaCache[cacheKey] = result
                    paginationCache[cacheKey] = pagination

                    // 6. Update UI
                    _uiState.value = ResultSearchUiState(result, pagination) //
                }
        }
    }
}
