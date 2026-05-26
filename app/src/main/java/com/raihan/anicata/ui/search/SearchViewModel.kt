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

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val animeRepository: AnimeSearchRepository,
    private val mangaRepository: MangaSearchRepository
) : ViewModel() {

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
