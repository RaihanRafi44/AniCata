package com.raihan.anicata.ui.top.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.model.manga.top.TopManga
import com.raihan.anicata.data.repository.manga.MangaTopRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class TopMangaViewModel(private val repository: MangaTopRepository) : ViewModel() {

    // Ukuran halaman yang diinginkan UI
    private val uiPageSize = 25

    // Tipe yang akan kita buang
    private val excludedTypes = setOf("novel", "lightnovel", "light novel")

    // --- State Internal ViewModel ---
    private var currentApiPage = 1 // Halaman API mana yang sedang kita ambil
    private var totalApiPages = 1 // Total halaman dari API
    private var currentFilter = "" // Filter yang sedang aktif ("publishing", dll)
    private var isFetching = false // Mencegah fetch ganda

    // Buffer untuk menyimpan item valid yang "tersisa" dari fetch sebelumnya
    private val itemBuffer = mutableListOf<TopManga>()

    // Cache untuk menyimpan halaman yang sudah jadi (Key: Nomor Halaman UI, Value: Daftar item)
    private val pageCache = MutableStateFlow<Map<Int, List<TopManga>>>(emptyMap())

    // State untuk UI
    private val _currentUiPage = MutableStateFlow(1)
    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages // Tetap gunakan totalPages dari API untuk PagingControls
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Ia akan mengambil data dari cache berdasarkan halaman UI saat ini
    val topManga: StateFlow<List<TopManga>> = _currentUiPage
        .combine(pageCache) { uiPage, cache ->
            cache[uiPage] ?: emptyList() // Ambil list dari cache, atau list kosong
        }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    fun getMangaForUiPage(uiPage: Int, filter: String = currentFilter) {
        viewModelScope.launch {
            // Jika filter berubah, reset semuanya
            if (filter != currentFilter) {
                resetData(filter)
            }

            _currentUiPage.value = uiPage // Set halaman UI yang aktif

            // Jika halaman sudah ada di cache, tidak perlu fetch
            if (pageCache.value.containsKey(uiPage)) {
                return@launch
            }

            // Jika sudah ada proses fetch, jangan fetch lagi
            if (isFetching) return@launch

            _isLoading.value = true
            isFetching = true
            _error.value = null

            val itemsForThisPage = mutableListOf<TopManga>()

            // 1. Ambil item dari buffer (sisa fetch sebelumnya)
            itemsForThisPage.addAll(itemBuffer)
            itemBuffer.clear()

            // 2. Terus fetch dari API sampai kita punya cukup item (25)
            while (itemsForThisPage.size < uiPageSize) {
                // Cek jika kita sudah mencapai akhir data API
                if (currentApiPage > totalApiPages && totalApiPages > 1) {
                    break // Berhenti, tidak ada data lagi
                }

                var fetchSuccess = false

                // Ambil data dari repository
                repository.getTopMangaList(
                    type = "", // KOSONGKAN type agar dapat semua
                    filter = currentFilter,
                    page = currentApiPage,
                    limit = 25 // Ambil 25 item (standar API) per fetch
                ).collectLatest { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            val apiList = result.payload?.first ?: emptyList()
                            _totalPages.value = result.payload?.second ?: 1
                            totalApiPages = _totalPages.value // Simpan total halaman API

                            // Filter novel/lightnovel
                            val validItems = apiList.filter { manga ->
                                !excludedTypes.contains(manga.type?.lowercase(Locale.ROOT))
                            }

                            itemsForThisPage.addAll(validItems) // Tambahkan ke daftar
                            currentApiPage++ // Siapkan untuk fetch halaman API berikutnya
                            fetchSuccess = true
                        }
                        is ResultWrapper.Error -> {
                            _error.value = result.exception?.message ?: "Unknown error"
                            isFetching = false
                            _isLoading.value = false
                            return@collectLatest // Hentikan loop jika error
                        }
                        is ResultWrapper.Loading -> { /* Biarkan loading */ }
                        is ResultWrapper.Empty -> {
                            totalApiPages = currentApiPage // Tandai sudah habis
                            fetchSuccess = true // Anggap sukses (tapi kosong)
                        }
                        else -> {}
                    }
                }

                // Jika fetch gagal atau sudah di akhir, hentikan loop
                if (!fetchSuccess || (currentApiPage > totalApiPages && totalApiPages > 1)) {
                    break
                }

                delay(1000L)
            }

            val itemsToProcess: List<TopManga>

            when (currentFilter) {
                "", "publishing", "upcoming" -> {
                    val comparator = compareByDescending<TopManga> { it.score ?: 0.0 }
                        .thenByDescending { it.members ?: 0 }
                    itemsToProcess = itemsForThisPage.sortedWith(comparator)
                }

                "bypopularity" -> {
                    val comparator = compareByDescending<TopManga> { it.members ?: 0 }
                    itemsToProcess = itemsForThisPage.sortedWith(comparator)
                }

                else -> {
                    itemsToProcess = itemsForThisPage
                }
            }
            // Item yang akan ditampilkan di halaman ini (ambil dari list yang sudah diurutkan)
            val itemsToShow = itemsToProcess.take(uiPageSize)

            // Sisanya, simpan di buffer untuk halaman berikutnya (juga dari list yang sudah diurutkan)
            itemBuffer.clear() // Hapus sisa buffer lama
            itemBuffer.addAll(itemsToProcess.drop(uiPageSize)) // Tambahkan buffer baru

            // 4. Simpan hasil ke cache
            if (itemsToShow.isNotEmpty()) {
                val newCache = pageCache.value.toMutableMap()
                newCache[uiPage] = itemsToShow
                pageCache.value = newCache
            }

            isFetching = false
            _isLoading.value = false
        }
    }

    private fun resetData(newFilter: String) {
        currentFilter = newFilter
        currentApiPage = 1
        totalApiPages = 1
        itemBuffer.clear()
        pageCache.value = emptyMap()
        _error.value = null
        _totalPages.value = 1
    }
}
