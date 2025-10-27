package com.raihan.anicata.ui.top.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopAnimeViewModel(private val repository: AnimeTopRepository) : ViewModel() {

    // State untuk daftar anime
    private val _topAnime = MutableStateFlow<List<TopAnime>>(emptyList())
    val topAnime: StateFlow<List<TopAnime>> = _topAnime.asStateFlow()

    // State untuk total halaman
    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    // State untuk loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk pesan error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val animeCache = mutableMapOf<String, List<TopAnime>>()

    private val totalPagesCache = mutableMapOf<String, Int>()

    // Helper untuk membuat cache key
    private fun getCacheKey(filter: String, page: Int): String {
        return "filter=$filter&page=$page"
    }

    // === PERUBAHAN PADA FUNGSI getTopAnimeData ===
    fun getTopAnimeData(
        page: Int,
        type: String = "",
        filter: String = "",
        limit: Int = 25
    ) {
        val cacheKey = getCacheKey(filter, page)

        // 1. Cek apakah data sudah ada di cache
        if (animeCache.containsKey(cacheKey)) {
            _isLoading.value = false
            _error.value = null
            _topAnime.value = animeCache[cacheKey]!!
            // Ambil juga total pages dari cache-nya filter ini
            _totalPages.value = totalPagesCache[filter] ?: 1

            return
        }

        viewModelScope.launch {
            repository.getTopAnimeList(type, filter, page, limit).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _error.value = null
                    }
                    is ResultWrapper.Success -> {

                        // 1. Ambil data, ubah 'val' menjadi 'var'
                        var animeList = result.payload?.first ?: emptyList()
                        val totalPages = result.payload?.second ?: 1

                        // 2. Tambahkan logika sorting sekunder HANYA jika filter "All Anime" ("")
                        if (filter == "") {
                            animeList = animeList.sortedWith(
                                compareByDescending<TopAnime> { it.score } // 1. Skor tertinggi dulu
                                    .thenByDescending { it.members }       // 2. Jika skor sama, member terbanyak dulu
                            )
                        }

                        _isLoading.value = false
                        _topAnime.value = animeList // 3. Gunakan 'animeList' yang sudah disortir
                        _totalPages.value = totalPages

                        // 3. Simpan hasil (yang sudah disortir) ke cache
                        animeCache[cacheKey] = animeList // 4. Simpan 'animeList' yang sudah disortir ke cache
                        totalPagesCache[filter] = totalPages

                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _error.value = result.exception?.message ?: "An unknown error occurred"
                    }
                    is ResultWrapper.Empty -> {
                        _isLoading.value = false
                        _topAnime.value = emptyList()

                        // 3. Simpan hasil (kosong) ke cache
                        animeCache[cacheKey] = emptyList()
                        totalPagesCache[filter] = 1 // Asumsi halaman 1 jika kosong
                    }

                    is ResultWrapper.Idle -> {}
                }
            }
        }
    }
}
