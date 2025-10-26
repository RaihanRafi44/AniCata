package com.raihan.anicata.ui.seasonalanime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch


/*data class SeasonalUiState(
    // Hasil dari API (loading, success, atau error)
    val apiResult: ResultWrapper<List<SeasonAnimeNow>> = ResultWrapper.Loading(),

    // Opsi untuk dropdown
    val typeOptions: List<String> = emptyList(),

    // Nilai yang sedang dipilih di dropdown
    val selectedType: String = ""
)

class SeasonalViewModel(
    private val nowRepository: AnimeSeasonNowRepository
) : ViewModel() {

    // StateFlow internal yang bisa diubah
    private val _uiState = MutableStateFlow(SeasonalUiState())
    // StateFlow eksternal yang read-only untuk diobservasi oleh UI
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        // 1. Siapkan opsi filter saat ViewModel dibuat
        val baseTypes = listOf("tv", "movie", "ova", "special", "ona", "music")
        val options = baseTypes.flatMap {
            listOf("$it (New)", "$it (Continuing)")
        }

        // 2. Update state dengan opsi yang sudah dibuat dan set nilai default
        _uiState.update {
            it.copy(
                typeOptions = options,
                selectedType = options.first() // Default: "tv (New)"
            )
        }

        // 3. Langsung panggil data untuk pertama kali saat ViewModel dibuat
        onUpdateFilter()
    }

    *//**
     * Dipanggil oleh UI saat user mengubah pilihan dropdown.
     * Fungsi ini HANYA mengubah state, tidak memicu API.
     *//*
    fun onTypeChange(newType: String) {
        _uiState.update {
            it.copy(selectedType = newType)
        }
    }

    *//**
     * Dipanggil oleh UI saat user menekan tombol "Update".
     * Fungsi ini membaca state, mem-parsing-nya, dan memicu panggilan API.
     *//*
    fun onUpdateFilter() {
        // Ambil nilai filter yang terakhir dipilih dari state
        val selectedType = _uiState.value.selectedType

        // Logika parsing dipindahkan ke dalam ViewModel
        val (filter, continuing) = parseTypeParams(selectedType)

        // Panggil repository di dalam viewModelScope
        viewModelScope.launch {
            // Set state ke loading sebelum memanggil API
            _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

            // Panggil repository (yang mengembalikan Flow)
            nowRepository.getSeasonNowAnimeList(filter, continuing)
                .collect { result ->
                    // Update state dengan hasil dari repository
                    _uiState.update { it.copy(apiResult = result) }
                }
        }
    }

    *//**
     * Fungsi helper (private) untuk mem-parsing string filter dari UI
     * menjadi parameter yang dimengerti oleh API.
     *//*
    private fun parseTypeParams(typeString: String): Pair<String, Boolean> {
        val typeParts = typeString.split(" ")
        // 'filter' adalah bagian pertama (misal: "tv")
        val filter = typeParts.getOrNull(0) ?: "tv"
        // 'continuing' adalah true jika bagian kedua adalah "(Continuing)"
        val continuing = typeParts.getOrNull(1) == "(Continuing)"
        return Pair(filter, continuing)
    }
}*/

/**
 * State UI sekarang menampung 4 filter,
 * meskipun hanya 2 yang akan digunakan oleh logika.
 */
/*data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeNow>> = ResultWrapper.Loading(),

    val seasonOptions: List<String> = emptyList(),
    val selectedSeason: String = "",

    val typeOptions: List<String> = emptyList(),
    val selectedType: String = "",

    val statusOptions: List<String> = emptyList(),
    val selectedStatus: String = "",

    val genreOptions: List<String> = emptyList(),
    val selectedGenre: String = ""
)

class SeasonalViewModel(
    private val nowRepository: AnimeSeasonNowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        val seasons = listOf("Fall 2025", "Summer 2025") // Dummy
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")

        // 1. Ubah Status agar memiliki 3 opsi
        val statuses = listOf("New", "Continuing", "All")

        val genres = listOf("Action", "Comedy", "Fantasy", "Sci-Fi") // Dummy

        _uiState.update {
            it.copy(
                seasonOptions = seasons,
                selectedSeason = seasons.first(),

                typeOptions = types,
                selectedType = types.first(),

                statusOptions = statuses,
                selectedStatus = statuses.first(), // Default: "New"

                genreOptions = genres,
                selectedGenre = genres.first()
            )
        }

        onUpdateFilter()
    }

    // --- Event Handlers (Tidak berubah) ---
    fun onSeasonChange(newSeason: String) {
        _uiState.update { it.copy(selectedSeason = newSeason) }
    }
    fun onTypeChange(newType: String) {
        _uiState.update { it.copy(selectedType = newType) }
    }
    fun onStatusChange(newStatus: String) {
        _uiState.update { it.copy(selectedStatus = newStatus) }
    }
    fun onGenreChange(newGenre: String) {
        _uiState.update { it.copy(selectedGenre = newGenre) }
    }

    *//**
     * Logika filter yang diperbarui total
     *//*
    fun onUpdateFilter() {
        _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

        val filterType = _uiState.value.selectedType
        val filterStatus = _uiState.value.selectedStatus

        viewModelScope.launch {
            try {
                when (filterStatus) {
                    // KASUS 1: HANYA NEW (Panggilan API sederhana)
                    "New" -> {
                        nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = false // Hanya 'New'
                        ).collect { result ->
                            _uiState.update { it.copy(apiResult = result) }
                        }
                    }

                    // KASUS 2: NEW + CONTINUING (Panggilan API sederhana)
                    "All" -> {
                        nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = true // 'New' + 'Continuing'
                        ).collect { result ->
                            _uiState.update { it.copy(apiResult = result) }
                        }
                    }

                    // KASUS 3: HANYA CONTINUING (Logika 2 panggilan API)
                    "Continuing" -> {
                        // Siapkan 2 flow
                        val allFlow = nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = true
                        )
                        val newFlow = nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = false
                        )

                        // Gunakan 'zip' untuk menunggu kedua hasil flow selesai
                        allFlow.zip(newFlow) { allResult, newResult ->
                            // Pastikan kedua panggilan API berhasil
                            if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {

                                val allList = allResult.payload ?: emptyList()
                                val newList = newResult.payload ?: emptyList()

                                // Buat Set ID dari daftar 'New' untuk pencarian cepat
                                val newIds = newList.map { it.id }.toSet()

                                // Filter daftar 'All' untuk mengecualikan item yang ada di 'New'
                                val continuingList = allList.filter { it.id !in newIds }

                                // Kembalikan hasil 'Continuing' yang sudah bersih
                                ResultWrapper.Success(continuingList)

                            } else if (allResult is ResultWrapper.Error) {
                                allResult // Kembalikan error jika ada
                            } else if (newResult is ResultWrapper.Error) {
                                newResult // Kembalikan error jika ada
                            } else {
                                ResultWrapper.Error(Exception("Failed to get combined anime data"))
                            }
                        }.collect { combinedResult ->
                            _uiState.update { it.copy(apiResult = combinedResult) }
                        }
                    }
                }
            } catch (e: Exception) {
                // Tangkap error tak terduga
                _uiState.update { it.copy(apiResult = ResultWrapper.Error(e)) }
            }
        }
    }
}*/

// ... (Data class SeasonalUiState tidak berubah) ...
data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeNow>> = ResultWrapper.Loading(),

    val seasonOptions: List<String> = emptyList(),
    val selectedSeason: String = "",

    val typeOptions: List<String> = emptyList(),
    val selectedType: String = "",

    val statusOptions: List<String> = emptyList(),
    val selectedStatus: String = "",

    val genreOptions: List<String> = emptyList(),
    val selectedGenre: String = ""
)

class SeasonalViewModel(
    private val nowRepository: AnimeSeasonNowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        // ... (init block tidak berubah) ...
        val seasons = listOf("Fall 2025", "Summer 2025")
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")
        val statuses = listOf("New", "Continuing", "All")
        val genres = listOf("Action", "Comedy", "Fantasy", "Sci-Fi")

        _uiState.update {
            it.copy(
                seasonOptions = seasons,
                selectedSeason = seasons.first(),
                typeOptions = types,
                selectedType = types.first(),
                statusOptions = statuses,
                selectedStatus = statuses.first(),
                genreOptions = genres,
                selectedGenre = genres.first()
            )
        }
        onUpdateFilter()
    }

    // ... (Semua event handler: onSeasonChange, onTypeChange, dll. tidak berubah) ...
    fun onSeasonChange(newSeason: String) { _uiState.update { it.copy(selectedSeason = newSeason) } }
    fun onTypeChange(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun onStatusChange(newStatus: String) { _uiState.update { it.copy(selectedStatus = newStatus) } }
    fun onGenreChange(newGenre: String) { _uiState.update { it.copy(selectedGenre = newGenre) } }

    /**
     * Logika filter dengan perbaikan di blok 'zip'
     */
    fun onUpdateFilter() {
        _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

        val filterType = _uiState.value.selectedType
        val filterStatus = _uiState.value.selectedStatus

        viewModelScope.launch {
            try {
                when (filterStatus) {
                    "New" -> {
                        // ... (blok ini tidak berubah)
                        nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = false
                        ).collect { result ->
                            _uiState.update { it.copy(apiResult = result) }
                        }
                    }

                    "All" -> {
                        // ... (blok ini tidak berubah)
                        nowRepository.getSeasonNowAnimeList(
                            filter = filterType,
                            continuing = true
                        ).collect { result ->
                            _uiState.update { it.copy(apiResult = result) }
                        }
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

                        // Gunakan 'zip' untuk menunggu kedua hasil flow selesai
                        allFlow.zip(newFlow) { allResult, newResult ->

                            // --- AWAL PERBAIKAN ---

                            // 1. Cek state Loading (ini yang utama)
                            if (allResult is ResultWrapper.Loading || newResult is ResultWrapper.Loading) {
                                ResultWrapper.Loading()

                                // 2. Cek state Success (tidak berubah)
                            } else if (allResult is ResultWrapper.Success && newResult is ResultWrapper.Success) {

                                val allList = allResult.payload ?: emptyList()
                                val newList = newResult.payload ?: emptyList()

                                val newIds = newList.map { it.id }.toSet()
                                val continuingList = allList.filter { it.id !in newIds }

                                ResultWrapper.Success(continuingList)

                                // 3. Cek state Error (tidak berubah)
                            } else if (allResult is ResultWrapper.Error) {
                                allResult
                            } else if (newResult is ResultWrapper.Error) {
                                newResult

                                // 4. Blok 'else' yang menyebabkan error
                            } else {
                                // Kita ubah errornya agar lebih jelas jika terjadi lagi
                                ResultWrapper.Error(Exception("Failed to zip flows (Idle/Empty state)"))
                            }
                            // --- AKHIR PERBAIKAN ---

                        }.collect { combinedResult ->
                            // Hanya update UI jika hasilnya BUKAN Loading,
                            // karena UI sudah di-set ke Loading di awal.
                            // Ini mencegah "kedipan" error yang tidak perlu.
                            if (combinedResult !is ResultWrapper.Loading) {
                                _uiState.update { it.copy(apiResult = combinedResult) }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(apiResult = ResultWrapper.Error(e)) }
            }
        }
    }
}
