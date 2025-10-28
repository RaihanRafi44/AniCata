package com.raihan.anicata.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.mapper.ui.toAnimeInfo
import com.raihan.anicata.data.mapper.ui.toMangaInfo
import com.raihan.anicata.data.repository.anime.AnimeSearchRepository
import com.raihan.anicata.data.repository.manga.MangaSearchRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip

/*
@OptIn(FlowPreview::class)
class SearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Menggunakan ResultWrapper langsung dari library Anda, dimulai dengan Idle
    private val _searchResults = MutableStateFlow<ResultWrapper<List<AnimeInfo>>>(ResultWrapper.Idle())
    val searchResults: StateFlow<ResultWrapper<List<AnimeInfo>>> = _searchResults.asStateFlow()

    // Batas 5 anime + 5 manga = 10 total
    private val liveSearchLimit = 5

    init {
        observeSearchQuery()
    }

    */
/**
     * Dipanggil oleh UI setiap kali teks di search bar berubah
     *//*

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    */
/**
     * Mengobservasi query, melakukan debounce, dan memanggil repository
     *//*

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L) // Tunggu 500ms setelah user berhenti mengetik
                .distinctUntilChanged() // Hanya proses jika query-nya berubah
                .flatMapLatest { query ->
                    if (query.trim().isBlank()) {
                        // Jika query kosong, kembalikan state Idle
                        flowOf(ResultWrapper.Idle())
                    } else {
                        // Siapkan flow untuk anime
                        val animeFlow = animeRepository.getSearchAnimeList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )
                        // Siapkan flow untuk manga
                        val mangaFlow = mangaRepository.getSearchMangaList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )

                        // Gabungkan hasil keduanya menggunakan zip
                        animeFlow.zip(mangaFlow) { animeResult, mangaResult ->
                            // Prioritaskan Error
                            if (animeResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(animeResult.exception)
                            }
                            if (mangaResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(mangaResult.exception)
                            }

                            // Gabungkan payload jika keduanya Sukses (atau salah satunya Empty)
                            val animeList = (animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList())
                            val mangaList = (mangaResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList())

                            val combinedList = animeList + mangaList

                            if (combinedList.isEmpty()) {
                                ResultWrapper.Empty(emptyList())
                            } else {
                                ResultWrapper.Success(combinedList)
                            }
                        }
                            // Emit Loading() di awal sebelum zip dieksekusi
                            .onStart { emit(ResultWrapper.Loading()) }
                    }
                }
                .collect { state ->
                    _searchResults.value = state
                }
        }
    }
}*/

/*@OptIn(FlowPreview::class)
class SearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Menggunakan ResultWrapper langsung dari library Anda, dimulai dengan Idle
    private val _searchResults = MutableStateFlow<ResultWrapper<List<AnimeInfo>>>(ResultWrapper.Idle())
    val searchResults: StateFlow<ResultWrapper<List<AnimeInfo>>> = _searchResults.asStateFlow()

    // Batas 5 anime + 5 manga = 10 total
    private val liveSearchLimit = 5

    init {
        observeSearchQuery()
    }

    *//**
     * Dipanggil oleh UI setiap kali teks di search bar berubah
     *//*
    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    *//**
     * Mengobservasi query, melakukan debounce, dan memanggil repository
     *//*
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.trim().isBlank()) {
                        // PERBAIKAN 1: Tentukan tipe generic secara eksplisit
                        flowOf(ResultWrapper.Idle<List<AnimeInfo>>())
                    } else {
                        val animeFlow = animeRepository.getSearchAnimeList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )
                        val mangaFlow = mangaRepository.getSearchMangaList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )

                        animeFlow.zip(mangaFlow) { animeResult, mangaResult ->

                            // Prioritaskan Error (dengan tipe generic)
                            if (animeResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(animeResult.exception)
                            }
                            if (mangaResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(mangaResult.exception)
                            }

                            // PERBAIKAN 2: Akses payload dengan DUA kali safe call (?. ?. map)
                            // Ini karena 'payload' bisa null, dan 'payload.first' juga bisa null

                            val animeList = when (animeResult) {
                                is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                else -> emptyList() // Menangani Loading/Idle
                            }

                            val mangaList = when (mangaResult) {
                                is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toMangaInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> mangaResult.payload?.first?.map { it.toMangaInfo() } ?: emptyList()
                                else -> emptyList() // Menangani Loading/Idle
                            }

                            val combinedList = animeList + mangaList

                            if (combinedList.isEmpty()) {
                                // Tentukan tipe generic
                                ResultWrapper.Empty<List<AnimeInfo>>(emptyList())
                            } else {
                                ResultWrapper.Success(combinedList)
                            }
                        }
                            .onStart {
                                // PERBAIKAN 1 (lagi): Tentukan tipe generic
                                emit(ResultWrapper.Loading<List<AnimeInfo>>())
                            }
                    }
                }
                .collect { state ->
                    // Sekarang 'state' dijamin bertipe ResultWrapper<List<AnimeInfo>>
                    _searchResults.value = state
                }
        }
    }
}*/

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

    // ... (property _searchQuery, searchResults, liveSearchLimit tidak berubah)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    private val _searchResults = MutableStateFlow<ResultWrapper<List<AnimeInfo>>>(ResultWrapper.Idle())
    val searchResults: StateFlow<ResultWrapper<List<AnimeInfo>>> = _searchResults.asStateFlow()
    private val liveSearchLimit = 5

    init {
        observeSearchQuery()
    }

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    /*@OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.trim().isBlank()) {
                        flowOf(ResultWrapper.Idle<List<AnimeInfo>>())
                    } else {
                        val animeFlow = animeRepository.getSearchAnimeList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )
                        val mangaFlow = mangaRepository.getSearchMangaList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )

                        // --- PERBAIKAN BUG "NO RESULTS FOUND" ---
                        // Filter state Loading/Idle agar zip tidak ter-trigger
                        val filteredAnimeFlow = animeFlow.filter { it !is ResultWrapper.Loading && it !is ResultWrapper.Idle }
                        val filteredMangaFlow = mangaFlow.filter { it !is ResultWrapper.Loading && it !is ResultWrapper.Idle }

                        // Gunakan flow yang sudah difilter
                        filteredAnimeFlow.zip(filteredMangaFlow) { animeResult, mangaResult ->
                            // --- AKHIR PERBAIKAN BUG ---

                            if (animeResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(animeResult.exception)
                            }
                            if (mangaResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(mangaResult.exception)
                            }

                            val animeList = when (animeResult) {
                                is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                else -> emptyList()
                            }

                            val mangaList = when (mangaResult) {
                                // --- PERBAIKAN TYPO 'toMangaInfo' ---
                                is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> mangaResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                // --- AKHIR PERBAIKAN TYPO ---
                                else -> emptyList()
                            }

                            val combinedList = animeList + mangaList

                            if (combinedList.isEmpty()) {
                                ResultWrapper.Empty<List<AnimeInfo>>(emptyList())
                            } else {
                                ResultWrapper.Success(combinedList)
                            }
                        }
                            .onStart {
                                // onStart ini akan emit Loading SEBELUM zip menunggu
                                emit(ResultWrapper.Loading<List<AnimeInfo>>())
                            }
                    }
                }
                .collect { state ->
                    _searchResults.value = state
                }
        }
    }*/

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    if (query.trim().isBlank()) {
                        flowOf(ResultWrapper.Idle<List<AnimeInfo>>())
                    } else {
                        val animeFlow = animeRepository.getSearchAnimeList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )
                        val mangaFlow = mangaRepository.getSearchMangaList(
                            query = query, limit = liveSearchLimit, page = 1,
                            type = null, score = null, genres = null, orderBy = null, sort = null
                        )

                        // --- INI ADALAH PERBAIKAN FINAL ---
                        animeFlow.zip(mangaFlow) { animeResult, mangaResult ->

                            // 1. PERBAIKAN BUG:
                            // Jika salah satu flow masih Loading atau Idle,
                            // jangan proses lebih lanjut. Return null.
                            if (animeResult is ResultWrapper.Loading || mangaResult is ResultWrapper.Loading) {
                                return@zip null
                            }
                            if (animeResult is ResultWrapper.Idle || mangaResult is ResultWrapper.Idle) {
                                return@zip null
                            }

                            // 2. Prioritaskan Error
                            if (animeResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(animeResult.exception)
                            }
                            if (mangaResult is ResultWrapper.Error) {
                                return@zip ResultWrapper.Error<List<AnimeInfo>>(mangaResult.exception)
                            }

                            // 3. Proses Success atau Empty (karena Loading/Idle/Error sudah ditangani)
                            val animeList = when (animeResult) {
                                is ResultWrapper.Success -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> animeResult.payload?.first?.map { it.toAnimeInfo() } ?: emptyList()
                                else -> emptyList() // Harusnya tidak akan pernah ke sini
                            }

                            val mangaList = when (mangaResult) {
                                is ResultWrapper.Success -> mangaResult.payload?.first?.map { it.toMangaInfo() } ?: emptyList()
                                is ResultWrapper.Empty -> mangaResult.payload?.first?.map { it.toMangaInfo() } ?: emptyList()
                                else -> emptyList() // Harusnya tidak akan pernah ke sini
                            }

                            //val combinedList = animeList + mangaList

                            // De-duplikasi berdasarkan Judul dan Tahun
                            val combinedList = (animeList + mangaList).distinctBy { it.mainTitle to it.typeAndYear }

                            if (combinedList.isEmpty()) {
                                ResultWrapper.Empty<List<AnimeInfo>>(emptyList())
                            } else {
                                ResultWrapper.Success(combinedList)
                            }
                        }
                            .filterNotNull() // 4. Abaikan/filter hasil 'null' yang kita return dari 'Loading'
                            .onStart {
                                // onStart tetap emit Loading di awal
                                emit(ResultWrapper.Loading<List<AnimeInfo>>())
                            }
                        // --- AKHIR PERBAIKAN ---
                    }
                }
                .collect { state ->
                    _searchResults.value = state
                }
        }
    }
}
