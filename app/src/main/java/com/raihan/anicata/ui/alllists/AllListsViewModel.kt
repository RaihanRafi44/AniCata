package com.raihan.anicata.ui.alllists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.data.repository.anime.AnimeSearchRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
// Data class untuk menampung seluruh state UI
data class AllListsUiState(
    val animeList: List<SearchAnime> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val totalPages: Int = 1,
    val currentPage: Int = 1
)

class AllListsViewModel(private val repository: AnimeSearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AllListsUiState())
    val uiState: StateFlow<AllListsUiState> = _uiState.asStateFlow()

    private val limit = 25 // Sesuai permintaan Anda

    init {
        // Muat halaman pertama saat ViewModel dibuat
        fetchAnimePage(1)
    }

    fun fetchAnimePage(page: Int) {
        // Jangan fetch jika sedang loading
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            repository.getSearchAnimeList(
                query = null,
                page = page, // Gunakan halaman yang diminta
                limit = limit,
                type = null,
                score = null,
                genres = null,
                orderBy = "score",
                sort = "desc"
            ).onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is ResultWrapper.Success -> {
                        // Ambil data list dan total halaman dari repository
                        val newAnimeList = result.payload?.first ?: emptyList()
                        val totalPages = result.payload?.second ?: 1 // Default 1 jika null

                        val sortedList = newAnimeList.sortedWith(
                            compareByDescending<SearchAnime> { it.score ?: 0.0 } // Urutan utama: Score
                                .thenByDescending { it.members ?: 0 } // Urutan kedua: Members
                        )

                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                animeList = sortedList, // <-- MENGGANTI list lama
                                totalPages = totalPages,
                                currentPage = page,
                                isError = false,
                                errorMessage = ""
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.exception?.message ?: "Terjadi kesalahan tidak diketahui",
                                currentPage = page // Tetap update halaman meski error
                            )
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}*/

/*// Data class untuk menampung seluruh state UI
data class AllListsUiState(
    val animeList: List<SearchAnime> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val totalPages: Int = 1,
    val currentPage: Int = 1,

    // --- State Baru Untuk Filter ---
    // Menyimpan pilihan user di dropdown (sebelum ditekan "Update")
    val selectedSort: String = "Score",
    // Menyimpan filter yang benar-benar dikirim ke API
    val appliedOrderBy: String = "score",
    val appliedSort: String = "desc"
)

class AllListsViewModel(private val repository: AnimeSearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AllListsUiState())
    val uiState: StateFlow<AllListsUiState> = _uiState.asStateFlow()

    private val limit = 25 // Sesuai permintaan Anda

    init {
        // Muat halaman pertama saat ViewModel dibuat (menggunakan filter default)
        fetchAnimePage(1)
    }

    *//**
     * Memperbarui state pilihan di UI dropdown.
     * Tidak memicu fetch data.
     *//*
    fun updateSortFilter(newSort: String) {
        _uiState.update { it.copy(selectedSort = newSort) }
    }

    *//**
     * Menerapkan filter yang dipilih dan memuat ulang data dari halaman 1.
     *//*
    fun applyFilters() {
        val (newOrderBy, newSort) = when (_uiState.value.selectedSort) {
            "A - Z" -> "title" to "asc"
            "Z - A" -> "title" to "desc"
            "Popularity" -> "popularity" to "asc"
            "Score" -> "score" to "desc"
            else -> "score" to "desc" // Default case
        }

        // Set filter YANG DITERAPKAN
        _uiState.update {
            it.copy(
                appliedOrderBy = newOrderBy,
                appliedSort = newSort,
                currentPage = 1 // Reset ke halaman 1 setiap ganti filter
            )
        }

        // Fetch halaman pertama dengan filter baru
        fetchAnimePage(1)
    }

    fun fetchAnimePage(page: Int) {
        // Jangan fetch jika sedang loading
        if (_uiState.value.isLoading) return

        // Ambil filter yang SUDAH DITERAPKAN dari state
        val currentState = _uiState.value
        val currentOrderBy = currentState.appliedOrderBy
        val currentSort = currentState.appliedSort

        viewModelScope.launch {
            repository.getSearchAnimeList(
                query = null,
                page = page, // Gunakan halaman yang diminta
                limit = limit,
                type = null,
                score = null,
                genres = null,
                orderBy = currentOrderBy, // <-- Gunakan state
                sort = currentSort      // <-- Gunakan state
            ).onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is ResultWrapper.Success -> {
                        // Ambil data list dan total halaman dari repository
                        val newAnimeList = result.payload?.first ?: emptyList()
                        val totalPages = result.payload?.second ?: 1 // Default 1 jika null

                        // --- MODIFIKASI DISINI ---
                        // Terapkan sorting sekunder HANYA jika filter utama adalah "score"
                        val finalList = if (currentOrderBy == "score") {
                            newAnimeList.sortedWith(
                                compareByDescending<SearchAnime> { it.score ?: 0.0 } // Urutan utama: Score
                                    .thenByDescending { it.members ?: 0 } // Urutan kedua: Members
                            )
                        } else {
                            // Jika bukan "score" (spt "popularity" atau "title"),
                            // percaya urutan dari API
                            newAnimeList
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                animeList = finalList, // <-- Gunakan list asli dari API
                                totalPages = totalPages,
                                currentPage = page,
                                isError = false,
                                errorMessage = ""
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.exception?.message ?: "Terjadi kesalahan tidak diketahui",
                                currentPage = page // Tetap update halaman meski error
                            )
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}*/

// Data class untuk menampung seluruh state UI
data class AllListsUiState(
    val animeList: List<SearchAnime> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val totalPages: Int = 1,
    val currentPage: Int = 1,

    // --- State Filter Sort ---
    val selectedSort: String = "Score",
    val appliedOrderBy: String = "score",
    val appliedSort: String = "desc",

    // --- State Baru Untuk Filter Tipe ---
    val selectedType: String = "All", // Nilai default di UI
    val appliedType: String? = null // Nilai yang dikirim ke API (null jika "All")
)

class AllListsViewModel(private val repository: AnimeSearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AllListsUiState())
    val uiState: StateFlow<AllListsUiState> = _uiState.asStateFlow()

    private val limit = 25

    init {
        // Muat halaman pertama saat ViewModel dibuat (menggunakan filter default)
        fetchAnimePage(1)
    }

    /**
     * Memperbarui state pilihan di UI dropdown Sort.
     */
    fun updateSortFilter(newSort: String) {
        _uiState.update { it.copy(selectedSort = newSort) }
    }

    /**
     * Memperbarui state pilihan di UI dropdown Tipe.
     */
    fun updateTypeFilter(newType: String) {
        _uiState.update { it.copy(selectedType = newType) }
    }

    /**
     * Menerapkan filter yang dipilih dan memuat ulang data dari halaman 1.
     */
    fun applyFilters() {
        // Logika untuk Sort
        val (newOrderBy, newSort) = when (_uiState.value.selectedSort) {
            "A - Z" -> "title" to "asc"
            "Z - A" -> "title" to "desc"
            "Popularity" -> "popularity" to "asc"
            "Score" -> "score" to "desc"
            else -> "score" to "desc" // Default case
        }

        // --- Logika Baru untuk Tipe ---
        val newAppliedType = when (val selected = _uiState.value.selectedType) {
            "All" -> null // Kirim null jika "All"
            "TV Special" -> "tv_special" // Mapping nama khusus
            else -> selected.lowercase() // "TV" -> "tv", "Movie" -> "movie", dst.
        }

        // Set filter YANG DITERAPKAN
        _uiState.update {
            it.copy(
                appliedOrderBy = newOrderBy,
                appliedSort = newSort,
                appliedType = newAppliedType, // <-- Terapkan filter tipe
                currentPage = 1 // Reset ke halaman 1 setiap ganti filter
            )
        }

        // Fetch halaman pertama dengan filter baru
        fetchAnimePage(1)
    }

    fun fetchAnimePage(page: Int) {
        // Jangan fetch jika sedang loading
        if (_uiState.value.isLoading) return

        // Ambil filter yang SUDAH DITERAPKAN dari state
        val currentState = _uiState.value
        val currentOrderBy = currentState.appliedOrderBy
        val currentSort = currentState.appliedSort
        val currentType = currentState.appliedType // <-- Ambil state tipe

        viewModelScope.launch {
            repository.getSearchAnimeList(
                query = null,
                page = page, // Gunakan halaman yang diminta
                limit = limit,
                type = currentType, // <-- Gunakan state tipe
                score = null,
                genres = null,
                orderBy = currentOrderBy, // <-- Gunakan state
                sort = currentSort      // <-- Gunakan state
            ).onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is ResultWrapper.Success -> {
                        // Ambil data list dan total halaman dari repository
                        val newAnimeList = result.payload?.first ?: emptyList()
                        val totalPages = result.payload?.second ?: 1 // Default 1 jika null

                        // --- PERBAIKAN BUG SORTING ---
                        // Logika sorting di sisi client DIHAPUS.
                        // Alasan: API Jikan tidak mendukung sorting sekunder.
                        // Melakukan sorting di client pada data yang sudah dipaginasi
                        // menyebabkan bug kontinuitas antar halaman (seperti yg Anda laporkan).
                        // Sekarang kita 100% percaya urutan dari API.
                        val finalList = newAnimeList

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                animeList = finalList, // <-- Gunakan list asli dari API
                                totalPages = totalPages,
                                currentPage = page,
                                isError = false,
                                errorMessage = ""
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = true,
                                errorMessage = result.exception?.message ?: "Terjadi kesalahan tidak diketahui",
                                currentPage = page // Tetap update halaman meski error
                            )
                        }
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}
