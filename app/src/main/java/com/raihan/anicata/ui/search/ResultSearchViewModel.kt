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


/*
class ResultSearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

    // State untuk menampung hasil pencarian (bukan live search)
    private val _searchState = MutableStateFlow<ResultWrapper<List<MediaItem>>>(ResultWrapper.Idle())
    val searchState: StateFlow<ResultWrapper<List<MediaItem>>> = _searchState.asStateFlow()

    // Batas 12 item per kategori (total 24)
    private val searchLimit = 12

    */
/**
     * Dipanggil oleh UI untuk memulai pencarian.
     *//*

    */
/*fun searchMedia(query: String) {
        // Hanya jalankan jika query baru (atau jika masih Idle)
        if (_searchState.value is ResultWrapper.Loading) return

        viewModelScope.launch {
            _searchState.value = ResultWrapper.Loading()

            val animeFlow = animeRepository.getSearchAnimeList(
                query = query, limit = searchLimit, page = 1,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )
            val mangaFlow = mangaRepository.getSearchMangaList(
                query = query, limit = searchLimit, page = 1,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )

            // Filter status Loading agar zip tidak ter-trigger terlalu cepat
            val filteredAnimeFlow = animeFlow.filter { it !is ResultWrapper.Loading && it !is ResultWrapper.Idle }
            val filteredMangaFlow = mangaFlow.filter { it !is ResultWrapper.Loading && it !is ResultWrapper.Idle }

            filteredAnimeFlow.zip(filteredMangaFlow) { animeResult, mangaResult ->

                // Prioritaskan Error
                if (animeResult is ResultWrapper.Error) {
                    return@zip ResultWrapper.Error<List<MediaItem>>(animeResult.exception)
                }
                if (mangaResult is ResultWrapper.Error) {
                    return@zip ResultWrapper.Error<List<MediaItem>>(mangaResult.exception)
                }

                // Gunakan MediaMapper.kt (toMediaItem)
                val animeList = when (animeResult) {
                    is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    is ResultWrapper.Empty -> emptyList() // Anggap saja kosong
                    else -> emptyList()
                }

                val mangaList = when (mangaResult) {
                    is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    is ResultWrapper.Empty -> emptyList()
                    else -> emptyList()
                }

                val combinedList = animeList + mangaList

                if (combinedList.isEmpty()) {
                    ResultWrapper.Empty<List<MediaItem>>(emptyList())
                } else {
                    ResultWrapper.Success(combinedList)
                }

            }.collect { result ->
                _searchState.value = result
            }
        }
    }*//*


    */
/**
     * Dipanggil oleh UI untuk memulai pencarian.
     *//*

    fun searchMedia(query: String) {
        // Jangan jalankan pencarian baru jika sedang loading
        if (_searchState.value is ResultWrapper.Loading) return

        viewModelScope.launch {
            _searchState.value = ResultWrapper.Loading()

            val animeFlow = animeRepository.getSearchAnimeList(
                query = query, limit = searchLimit, page = 1,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )
            val mangaFlow = mangaRepository.getSearchMangaList(
                query = query, limit = searchLimit, page = 1,
                type = null, score = null, genres = null, orderBy = null, sort = null
            )

            // --- PERBAIKAN DI SINI (SAMA SEPERTI SEARCHVIEWMODEL) ---

            // 1. JANGAN filter flow di sini

            animeFlow.zip(mangaFlow) { animeResult, mangaResult ->

                // 2. Tangani Loading/Idle DI DALAM zip dengan me-return null
                if (animeResult is ResultWrapper.Loading || mangaResult is ResultWrapper.Loading) {
                    return@zip null
                }
                if (animeResult is ResultWrapper.Idle || mangaResult is ResultWrapper.Idle) {
                    return@zip null
                }

                // 3. Prioritaskan Error
                if (animeResult is ResultWrapper.Error) {
                    return@zip ResultWrapper.Error<List<MediaItem>>(animeResult.exception)
                }
                if (mangaResult is ResultWrapper.Error) {
                    return@zip ResultWrapper.Error<List<MediaItem>>(mangaResult.exception)
                }

                // 4. Proses Success (karena Empty tidak akan pernah terjadi)
                val animeList = when (animeResult) {
                    // Gunakan toMediaItem()
                    is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    else -> emptyList() // Menangani Error (meskipun sudah dicek), Loading/Idle
                }

                val mangaList = when (mangaResult) {
                    // Gunakan toMediaItem()
                    is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toMediaItem() } ?: emptyList()
                    else -> emptyList()
                }

                val combinedList = animeList + mangaList

                if (combinedList.isEmpty()) {
                    ResultWrapper.Empty<List<MediaItem>>(emptyList())
                } else {
                    ResultWrapper.Success(combinedList)
                }

            }
                .filterNotNull() // 5. Filter semua emisi 'null' yang kita buat
                .collect { result ->
                    _searchState.value = result
                }
            // --- AKHIR PERBAIKAN ---
        }
    }
}*/

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

                /*// 4. Hitung Total Halaman
                val animePagination = (animeResult as? ResultWrapper.Success)?.payload?.second
                val mangaPagination = (mangaResult as? ResultWrapper.Success)?.payload?.second

                val animeTotal = animePagination?.last_visible_page ?: 0
                val mangaTotal = mangaPagination?.last_visible_page ?: 0*/

                // --- PERBAIKAN DI SINI ---
                // Ambil data pagination dari Success atau Empty, BUKAN cast
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
                // --- AKHIR PERBAIKAN ---

                // --- PERBAIKAN DI SINI ---
                // Variabel animePagination dan mangaPagination sudah bertipe Int?
                // (sesuai definisi repository)
                // Kita tidak perlu mengakses .last_visible_page lagi.
                val animeTotal = animePagination ?: 0
                val mangaTotal = mangaPagination ?: 0
                // --- AKHIR PERBAIKAN ---

                val totalPages = max(animeTotal, mangaTotal).coerceAtLeast(1)

                val paginationInfo = PaginationInfo(currentPage = page, totalPages = totalPages)

                // 5. Buat hasil
                val resultWrapper = if (combinedList.isEmpty()) {
                    ResultWrapper.Empty<List<MediaItem>>()
                } else {
                    ResultWrapper.Success(combinedList)
                }

                return@zip Pair(resultWrapper, paginationInfo)

            }
                .filterNotNull()
                .collect { (result, pagination) ->
                    _uiState.value = ResultSearchUiState(result, pagination)
                }
        }
    }
}
