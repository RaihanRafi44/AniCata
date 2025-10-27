package com.raihan.anicata.ui.seasonalanime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.usecase.AnimeFilterParams
import com.raihan.anicata.data.usecase.GetSeasonalUseCase
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

/*// ... (Data class SeasonalUiState tidak berubah) ...
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

    *//**
     * Logika filter dengan perbaikan di blok 'zip'
     *//*
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
}*/

/*data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeNow>> = ResultWrapper.Loading(),

    // Baris 1: Tahun dan Season
    val yearOptions: List<String> = emptyList(),
    val selectedYear: String = "",
    val seasonOptions: List<String> = emptyList(),
    val selectedSeason: String = "",

    // Baris 2: Tipe dan Status
    val typeOptions: List<String> = emptyList(),
    val selectedType: String = "",
    val statusOptions: List<String> = emptyList(),
    val selectedStatus: String = "",
)

class SeasonalViewModel(
    private val nowRepository: AnimeSeasonNowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        // Data baru untuk filter
        val years = listOf("2025", "2024", "2023", "2022") // Contoh data tahun
        val seasons = listOf("Fall", "Summer", "Winter", "Spring")
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")
        val statuses = listOf("New", "Continuing", "All")
        // Genre dihapus

        _uiState.update {
            it.copy(
                // Baris 1
                yearOptions = years,
                selectedYear = years.first(), // Default: "2025"
                seasonOptions = seasons,
                selectedSeason = seasons.first(), // Default: "Fall"

                // Baris 2
                typeOptions = types,
                selectedType = types.first(),
                statusOptions = statuses,
                selectedStatus = statuses.first(),

                // Opsi Genre dihapus
            )
        }
        onUpdateFilter()
    }

    // --- Event Handlers Diperbarui ---
    fun onYearChange(newYear: String) { _uiState.update { it.copy(selectedYear = newYear) } }
    fun onSeasonChange(newSeason: String) { _uiState.update { it.copy(selectedSeason = newSeason) } }
    fun onTypeChange(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun onStatusChange(newStatus: String) { _uiState.update { it.copy(selectedStatus = newStatus) } }
    // onGenreChange dihapus

    *//**
     * Logika filter tidak berubah, karena masih bergantung pada 'Type' dan 'Status'.
     * Filter 'Year' dan 'Season' saat ini hanya untuk UI (logic API belum mendukungnya).
     *//*
    fun onUpdateFilter() {
        _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

        // Logic masih menggunakan filterType dan filterStatus
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

                        // ... (logika zip tidak berubah)
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

                        }.collect { combinedResult ->
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
}*/

// ... (Data class SeasonalUiState tidak berubah) ...
data class SeasonalUiState(
    val apiResult: ResultWrapper<List<SeasonAnimeNow>> = ResultWrapper.Loading(),
    val seasonListData: List<SeasonAnimeList> = emptyList(),
    val isLoadingFilters: Boolean = true,
    val yearOptions: List<String> = emptyList(),
    val selectedYear: String = "",
    val seasonOptions: List<String> = emptyList(),
    val selectedSeason: String = "",
    val typeOptions: List<String> = emptyList(),
    val selectedType: String = "",
    val statusOptions: List<String> = emptyList(),
    val selectedStatus: String = "",
)

class SeasonalViewModel(
    // 1. Ubah constructor! Hanya butuh SATU UseCase
    private val getSeasonalDataUseCase: GetSeasonalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeasonalUiState())
    val uiState: StateFlow<SeasonalUiState> = _uiState.asStateFlow()

    init {
        // Data hardcoded (Tipe & Status) tetap di sini
        val types = listOf("TV", "Movie", "OVA", "Special", "ONA", "Music")
        val statuses = listOf("New", "Continuing", "All")

        _uiState.update {
            it.copy(
                typeOptions = types,
                selectedType = types.first(),
                statusOptions = statuses,
                selectedStatus = statuses.first(),
            )
        }

        // Panggil fungsi load filter yang baru
        loadSeasonFilters()
    }

    /**
     * 2. Fungsi init (loadSeasonFilters) menjadi lebih sederhana
     */
    private fun loadSeasonFilters() {
        viewModelScope.launch {
            // Panggil UseCase, bukan repository
            getSeasonalDataUseCase.getSeasonFilters().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val filterData = result.payload!! // Kita tahu ini tidak null dari logika UseCase

                        _uiState.update {
                            it.copy(
                                isLoadingFilters = false,
                                seasonListData = filterData.seasonListData,
                                yearOptions = filterData.yearOptions,
                                selectedYear = filterData.selectedYear,
                                seasonOptions = filterData.seasonOptions,
                                selectedSeason = filterData.selectedSeason
                            )
                        }

                        // Panggil onUpdateFilter HANYA setelah filter berhasil dimuat
                        onUpdateFilter()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(isLoadingFilters = false) }
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoadingFilters = true) }
                    }
                    else -> { /* Handle Empty/Idle jika perlu */ }
                }
            }
        }
    }

    // --- Event Handlers ---
    // onYearChange tidak berubah, karena ini murni logika UI-State
    fun onYearChange(newYear: String) {
        val newSeasons = _uiState.value.seasonListData
            .find { it.year.toString() == newYear }
            ?.seasons ?: emptyList()
        val newSelectedSeason = newSeasons.firstOrNull() ?: ""
        _uiState.update {
            it.copy(
                selectedYear = newYear,
                seasonOptions = newSeasons,
                selectedSeason = newSelectedSeason
            )
        }
    }

    // Handler lain tidak berubah
    fun onSeasonChange(newSeason: String) { _uiState.update { it.copy(selectedSeason = newSeason) } }
    fun onTypeChange(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun onStatusChange(newStatus: String) { _uiState.update { it.copy(selectedStatus = newStatus) } }

    /**
     * 3. Fungsi onUpdateFilter menjadi SANGAT sederhana
     */
    fun onUpdateFilter() {
        _uiState.update { it.copy(apiResult = ResultWrapper.Loading()) }

        // Buat parameter untuk UseCase
        val params = AnimeFilterParams(
            filterType = _uiState.value.selectedType,
            filterStatus = _uiState.value.selectedStatus
        )

        viewModelScope.launch {
            // Panggil UseCase, bukan repository
            getSeasonalDataUseCase.getSeasonalAnime(params)
                .collect { result ->
                    // Logika 'zip' dan 'when' sudah tidak ada lagi di sini!
                    if (result !is ResultWrapper.Loading) {
                        _uiState.update { it.copy(apiResult = result) }
                    }
                }
        }
    }
}
