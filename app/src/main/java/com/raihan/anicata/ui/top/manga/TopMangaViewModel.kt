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

/*
class TopMangaViewModel(private val repository: MangaTopRepository) : ViewModel(){
    // State untuk daftar anime
    private val _topManga = MutableStateFlow<List<TopManga>>(emptyList())
    val topManga: StateFlow<List<TopManga>> = _topManga.asStateFlow()

    // State untuk total halaman
    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages.asStateFlow()

    // State untuk loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk pesan error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // MODIFIKASI: Tambahkan "light novel" (dengan spasi) ke daftar pengecualian
    private val excludedTypes = setOf("novel", "lightnovel", "light novel")

    fun getTopMangaData(
        page: Int,
        type: String = "",
        filter: String = "",
        limit: Int = 25
    ) {
        viewModelScope.launch {
            repository.getTopMangaList(type, filter, page, limit).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _error.value = null
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false

                        _topManga.value = result.payload?.first ?: emptyList()
                        result.payload?.second?.let {
                            _totalPages.value = it
                        }
                        // MODIFIKASI: Filter daftarnya di sini (client-side)
                        val fullList = result.payload?.first ?: emptyList()
                        val filteredList = fullList.filter { manga ->
                            // Cek apakah 'manga.type' (contoh: "Manga", "Novel")
                            // ada di dalam daftar 'excludedTypes' setelah di-lowercase.
                            // Jika TIDAK ADA (!), maka itemnya lolos filter.
                            !excludedTypes.contains(manga.type?.lowercase(Locale.ROOT))
                        }

                        // Kirim list yang sudah difilter ke UI
                        _topManga.value = filteredList

                        result.payload?.second?.let {
                            _totalPages.value = it
                        }
                        */
/*_isLoading.value = false
                        _topManga.value = result.payload?.first ?: emptyList()
                        result.payload?.second?.let {
                            _totalPages.value = it
                        }*//*

                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _error.value = result.exception?.message ?: "An unknown error occurred"
                    }
                    is ResultWrapper.Empty -> {
                        _isLoading.value = false
                        _topManga.value = emptyList()
                    }

                    else -> {}
                }
            }
        }
    }
}*/

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

    // Ini adalah StateFlow final yang akan diobservasi oleh UI
    // Ia akan mengambil data dari cache berdasarkan halaman UI saat ini
    val topManga: StateFlow<List<TopManga>> = _currentUiPage
        .combine(pageCache) { uiPage, cache ->
            cache[uiPage] ?: emptyList() // Ambil list dari cache, atau list kosong
        }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())


    /**
     * Dipanggil oleh UI saat ingin memuat data untuk halaman tertentu.
     */
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

            // --- PERBAIKAN LOGIKA SORTING ---
            // Blok ini sekarang menggunakan 'when' untuk menentukan cara sorting
            // berdasarkan filter yang aktif.

            val itemsToProcess: List<TopManga>

            when (currentFilter) {
                // "All Manga" (kosong) dan "Publishing":
                // Urutkan berdasarkan Skor (tertinggi), lalu Member (terbanyak)
                "", "publishing", "upcoming" -> {
                    val comparator = compareByDescending<TopManga> { it.score ?: 0.0 }
                        .thenByDescending { it.members ?: 0 }
                    itemsToProcess = itemsForThisPage.sortedWith(comparator)
                }

                // "Most Popular":
                // Urutkan murni berdasarkan Member (terbanyak)
                "bypopularity" -> {
                    val comparator = compareByDescending<TopManga> { it.members ?: 0 }
                    itemsToProcess = itemsForThisPage.sortedWith(comparator)
                }

                // "Upcoming" (dan filter lain):
                // Percaya urutan asli dari API (yang sudah berurutan berdasarkan tanggal)
                // Kita tidak melakukan sort agar urutan dari buffer + fetch baru tetap terjaga.
                else -> {
                    itemsToProcess = itemsForThisPage
                }
            }
            // --- AKHIR PERBAIKAN ---
            // Item yang akan ditampilkan di halaman ini (ambil dari list yang sudah diurutkan)
            val itemsToShow = itemsToProcess.take(uiPageSize)

            // Sisanya, simpan di buffer untuk halaman berikutnya (juga dari list yang sudah diurutkan)
            itemBuffer.clear() // Hapus sisa buffer lama
            itemBuffer.addAll(itemsToProcess.drop(uiPageSize)) // Tambahkan buffer baru

            /*// --- PERBAIKAN LOGIKA SORTING ---

            val itemsToProcess: List<TopManga>

            // HANYA jika filter "All Novel" (kosong), kita urutkan manual
            // berdasarkan score, lalu members.
            if (currentFilter == "") {
                val comparator = compareByDescending<TopManga> { it.score ?: 0.0 }
                    .thenByDescending { it.members ?: 0 }
                itemsToProcess = itemsForThisPage.sortedWith(comparator)
            } else {
                // Untuk filter "bypopularity", "upcoming", "publishing",
                // kita percaya urutan asli dari API (karena itemsForThisPage
                // diisi secara berurutan dari API).
                itemsToProcess = itemsForThisPage
            }
            // --- AKHIR PERBAIKAN ---

            // Item yang akan ditampilkan di halaman ini (ambil dari list yang sudah diurutkan)
            val itemsToShow = itemsToProcess.take(uiPageSize)

            // Sisanya, simpan di buffer untuk halaman berikutnya (juga dari list yang sudah diurutkan)
            itemBuffer.clear() // Hapus sisa buffer lama
            itemBuffer.addAll(itemsToProcess.drop(uiPageSize)) // Tambahkan buffer baru*/

            /*// --- PERUBAHAN BARU DI SINI ---
            // Buat comparator untuk mengurutkan:
            // 1. Berdasarkan score (tertinggi ke terendah)
            // 2. Jika score sama, berdasarkan members (terbanyak ke tersedikit)
            val comparator = compareByDescending<TopManga> { it.score ?: 0.0 }
                .thenByDescending { it.members ?: 0 }

            // Urutkan list yang sudah dikumpulkan sebelum dipisah
            val sortedItems = itemsForThisPage.sortedWith(comparator)
            // --- AKHIR DARI PERUBAHAN BARU ---
            // Item yang akan ditampilkan di halaman ini (ambil dari list yang sudah diurutkan)
            val itemsToShow = sortedItems.take(uiPageSize)

            // Sisanya, simpan di buffer untuk halaman berikutnya (juga dari list yang sudah diurutkan)
            itemBuffer.addAll(sortedItems.drop(uiPageSize))*/

            /*// 3. Pisahkan data: 25 untuk UI, sisanya untuk buffer

            // Item yang akan ditampilkan di halaman ini
            val itemsToShow = itemsForThisPage.take(uiPageSize)

            // Sisanya, simpan di buffer untuk halaman berikutnya
            itemBuffer.addAll(itemsForThisPage.drop(uiPageSize))*/

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

    /**
     * Helper function untuk mereset state saat filter diubah.
     */
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
