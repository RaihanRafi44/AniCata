package com.raihan.anicata.ui.alllists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.media.MediaGenre
import com.raihan.anicata.data.model.media.MediaItem
import com.raihan.anicata.data.usecase.GetGenreListUseCase
import com.raihan.anicata.data.usecase.GetMediaListUseCase
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// --- Definisikan opsi filter "Type" secara terpisah ---
private val animeTypeOptions = listOf(
    "All", "TV", "Movie", "OVA", "Special", "ONA",
    "Music", "CM", "PV", "TV Special"
)

private val mangaTypeOptions = listOf(
    "All", "Manga", "Novel", "Light Novel", "One-shot",
    "Doujin", "Manhwa", "Manhua"
)

// Opsi "All" default untuk dropdown genre
private val allGenreOption = MediaGenre(id = 0, name = "All")

// Data class untuk menampung seluruh state UI
data class AllListsUiState(
    val mediaState: ResultWrapper<Pair<List<MediaItem>, Int>> = ResultWrapper.Idle(),
    val currentPage: Int = 1,

    // --- State KATEGORI ---
    val selectedCategory: String = "Anime",
    val categoryOptions: List<String> = listOf("Anime", "Manga"),

    // --- State Filter Sort ---
    val selectedSort: String = "Score",
    val appliedOrderBy: String = "score",
    val appliedSort: String = "desc",
    val sortOptions: List<String> = listOf("Score", "Popularity", "A - Z", "Z - A"),

    // --- State Filter Tipe ---
    val selectedType: String = "All",
    val appliedType: String? = null,
    val typeOptions: List<String> = animeTypeOptions, // Default Anime

    // --- State Filter (Genre, Tema, Target/Demographics) ---
    val genreList: List<MediaGenre> = listOf(allGenreOption),
    val themeList: List<MediaGenre> = listOf(allGenreOption),
    val demographicList: List<MediaGenre> = listOf(allGenreOption),

    val selectedGenre: String = "All",
    val selectedTheme: String = "All",
    val selectedTarget: String = "All",

    val appliedGenreId: Int? = null,
    val appliedThemeId: Int? = null,
    val appliedTargetId: Int? = null
)

class AllListsViewModel(
    private val getMediaListUseCase: GetMediaListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllListsUiState())
    val uiState: StateFlow<AllListsUiState> = _uiState.asStateFlow()

    private val limit = 25

    private data class FilterCache(
        val genres: List<MediaGenre>,
        val themes: List<MediaGenre>,
        val demographics: List<MediaGenre>
    )

    private var animeFilterCache: FilterCache? = null
    private var mangaFilterCache: FilterCache? = null


    // --- 1. TAMBAHKAN VARIABEL CACHE (meniru TopAnimeViewModel) ---
    private val mediaCache = mutableMapOf<String, List<MediaItem>>()
    private val totalPagesCache = mutableMapOf<String, Int>()

    init {
        applyFilters()
    }

    // --- 2. TAMBAHKAN FUNGSI CACHE KEY ---
    /**
     * Membuat cache key unik berdasarkan semua filter yang sedang diterapkan.
     */
    private fun getCacheKey(state: AllListsUiState, page: Int): String {
        val category = state.selectedCategory
        val orderBy = state.appliedOrderBy
        val sort = state.appliedSort
        val type = state.appliedType
        val genre = state.appliedGenreId
        val theme = state.appliedThemeId
        val target = state.appliedTargetId
        return "cat=$category&order=$orderBy&sort=$sort&type=$type&genre=$genre&theme=$theme&target=$target&page=$page"
    }

    /**
     * Mengambil filter DENGAN CACHE dan DELAY.
     */
    private suspend fun fetchFiltersSequentially(category: String): Boolean {
        val categoryLower = category.lowercase()

        // 1. Cek Cache
        val existingCache = if (categoryLower == "anime") animeFilterCache else mangaFilterCache

        if (existingCache != null) {
            // Cache hit!
            _uiState.update {
                it.copy(
                    // FIX: Gunakan listOf(item) + list
                    genreList = listOf(allGenreOption) + existingCache.genres,
                    themeList = listOf(allGenreOption) + existingCache.themes,
                    demographicList = listOf(allGenreOption) + existingCache.demographics,
                )
            }
            return true // Sukses dari cache
        }

        // 2. Cache miss. Reset list filter di UI sebelum fetch baru
        _uiState.update {
            it.copy(
                genreList = listOf(allGenreOption),
                themeList = listOf(allGenreOption),
                demographicList = listOf(allGenreOption)
            )
        }

        var success = true
        var fetchedGenres: List<MediaGenre>? = null
        var fetchedThemes: List<MediaGenre>? = null
        var fetchedDemographics: List<MediaGenre>? = null

        // 1. Ambil Genres
        getGenreListUseCase.execute(category, "genres").collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    fetchedGenres = result.payload ?: emptyList()
                    // FIX: Gunakan listOf(item) + list
                    _uiState.update { it.copy(genreList = listOf(allGenreOption) + fetchedGenres) }
                }
                is ResultWrapper.Error -> {
                    success = false
                    _uiState.update { it.copy(mediaState = ResultWrapper.Error(result.exception ?: Exception("Gagal memuat genre"))) }
                }
                else -> {}
            }
        }
        if (!success) return false

        delay(350)

        // 2. Ambil Themes
        getGenreListUseCase.execute(category, "themes").collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    fetchedThemes = result.payload ?: emptyList()
                    // FIX: Gunakan listOf(item) + list
                    _uiState.update { it.copy(themeList = listOf(allGenreOption) + fetchedThemes) }
                }
                is ResultWrapper.Error -> {
                    success = false
                    _uiState.update { it.copy(mediaState = ResultWrapper.Error(result.exception ?: Exception("Gagal memuat tema"))) }                }
                else -> {}
            }
        }
        if (!success) return false

        delay(350)

        // 3. Ambil Demographics
        getGenreListUseCase.execute(category, "demographics").collect { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    fetchedDemographics = result.payload ?: emptyList()
                    // FIX: Gunakan listOf(item) + list
                    _uiState.update { it.copy(demographicList = listOf(allGenreOption) + fetchedDemographics!!) }
                }
                is ResultWrapper.Error -> {
                    success = false
                    _uiState.update { it.copy(mediaState = ResultWrapper.Error(result.exception ?: Exception("Gagal memuat demografi"))) }                }
                else -> {}
            }
        }

        // 4. Jika semua berhasil, simpan ke cache
        if (success) {
            val newCache = FilterCache(
                genres = fetchedGenres!!,
                themes = fetchedThemes!!,
                demographics = fetchedDemographics!!
            )
            if (categoryLower == "anime") {
                animeFilterCache = newCache
            } else {
                mangaFilterCache = newCache
            }
        }

        return success
    }

    /**
     * Memperbarui kategori SAJA.
     */
    fun updateCategory(newCategory: String) {
        if (_uiState.value.selectedCategory == newCategory) return

        val newTypeOptions = when (newCategory.lowercase()) {
            "anime" -> animeTypeOptions
            "manga", "novel" -> mangaTypeOptions
            else -> animeTypeOptions
        }

        _uiState.update {
            it.copy(
                selectedCategory = newCategory,
                typeOptions = newTypeOptions,
                selectedType = "All",
                selectedGenre = "All",
                selectedTheme = "All",
                selectedTarget = "All",
                selectedSort = "Score",
                mediaState = ResultWrapper.Idle()
            )
        }

        viewModelScope.launch {
            fetchFiltersSequentially(newCategory)
        }
    }

    // --- Fungsi update filter individu (tidak berubah) ---
    fun updateSortFilter(newSort: String) { _uiState.update { it.copy(selectedSort = newSort) } }
    fun updateTypeFilter(newType: String) { _uiState.update { it.copy(selectedType = newType) } }
    fun updateGenreFilter(newGenre: String) { _uiState.update { it.copy(selectedGenre = newGenre) } }
    fun updateThemeFilter(newTheme: String) { _uiState.update { it.copy(selectedTheme = newTheme) } }
    fun updateTargetFilter(newTarget: String) { _uiState.update { it.copy(selectedTarget = newTarget) } }
    // ---

    /**
     * Fungsi publik untuk menerapkan filter (Tombol "Update Filter").
     */
    fun applyFilters() {
        _uiState.update { it.copy(mediaState = ResultWrapper.Loading()) }
        viewModelScope.launch {
            val filterSuccess = fetchFiltersSequentially(_uiState.value.selectedCategory)

            if (filterSuccess) {
                applyFiltersInternal()
                fetchMediaPageInternal(1)
            } else {
                _uiState.update { it.copy(mediaState = ResultWrapper.Error(Exception("Gagal memuat filter"))) }            }
        }
    }

    /**
     * Versi internal untuk menerapkan filter.
     */
    private fun applyFiltersInternal() {
        val currentState = _uiState.value
        val (newOrderBy, newSort) = when (currentState.selectedSort) {
            "A - Z" -> "title" to "asc"
            "Z - A" -> "title" to "desc"
            "Popularity" -> "members" to "desc"
            "Score" -> "score" to "desc"
            else -> "score" to "desc"
        }
        val newAppliedType = when (val selected = currentState.selectedType) {
            "All" -> null
            "TV Special" -> "tv_special"
            "Light Novel" -> "lightnovel"
            "One-shot" -> "oneshot"
            else -> selected.lowercase()
        }
        val newGenreId = currentState.genreList.find { it.name == currentState.selectedGenre }?.id
        val newThemeId = currentState.themeList.find { it.name == currentState.selectedTheme }?.id
        val newTargetId = currentState.demographicList.find { it.name == currentState.selectedTarget }?.id

        _uiState.update {
            it.copy(
                appliedOrderBy = newOrderBy,
                appliedSort = newSort,
                appliedType = newAppliedType,
                appliedGenreId = if (newGenreId == 0) null else newGenreId,
                appliedThemeId = if (newThemeId == 0) null else newThemeId,
                appliedTargetId = if (newTargetId == 0) null else newTargetId,
                currentPage = 1,
                mediaState = ResultWrapper.Idle()
            )
        }
    }

    /**
     * Fungsi publik untuk mengambil halaman media (Paging).
     */
    fun fetchMediaPage(page: Int) {
        if (_uiState.value.mediaState is ResultWrapper.Loading && _uiState.value.currentPage == page && page != 1) return
        viewModelScope.launch {
            _uiState.update { it.copy(mediaState = ResultWrapper.Loading()) }
            fetchMediaPageInternal(page)
        }
    }


    /**
     * Mengambil data media untuk halaman yang diberikan.
     */
    private suspend fun fetchMediaPageInternal(page: Int) {
        val currentState = _uiState.value

        // --- 3a. CEK CACHE DI AWAL ---
        val cacheKey = getCacheKey(currentState, page)

        if (mediaCache.containsKey(cacheKey)) {
            _uiState.update {
                it.copy(
                    mediaState = ResultWrapper.Success(Pair(mediaCache[cacheKey]!!, totalPagesCache[cacheKey] ?: 1)),
                    currentPage = page
                )
            }
            return // Hentikan eksekusi, data sudah ada di cache
        }

        // --- 3b. JIKA CACHE MISS, LANJUTKAN FETCH DATA ---
        _uiState.update { it.copy(mediaState = ResultWrapper.Loading())}

        val category = currentState.selectedCategory
        val currentOrderBy = currentState.appliedOrderBy
        val currentSort = currentState.appliedSort
        val currentType = currentState.appliedType
        val allAppliedIds = listOfNotNull(
            currentState.appliedGenreId,
            currentState.appliedThemeId,
            currentState.appliedTargetId
        )
        val combinedGenresParam = if (allAppliedIds.isEmpty()) null else allAppliedIds.joinToString(",")

        getMediaListUseCase.execute(
            category = category,
            page = page,
            limit = limit,
            type = currentType,
            genres = combinedGenresParam,
            orderBy = currentOrderBy,
            sort = currentSort
        ).collect { result ->
            when (result) {
                is ResultWrapper.Loading -> {
                    if (_uiState.value.mediaState !is ResultWrapper.Loading) {
                        _uiState.update { it.copy(mediaState = ResultWrapper.Loading()) }
                    }
                }
                is ResultWrapper.Success, is ResultWrapper.Empty -> {
                    val payload = result.payload
                    val fetchedList = payload?.first ?: emptyList()
                    val totalPages = payload?.second ?: 1
                    // --- 3c. SIMPAN KE CACHE SAAT SUKSES ---
                    mediaCache[cacheKey] = fetchedList
                    totalPagesCache[cacheKey] = totalPages

                    val finalState = ResultWrapper.Success(Pair(fetchedList, totalPages))

                    _uiState.update {
                        it.copy(
                            mediaState = finalState,
                            currentPage = if (result is ResultWrapper.Empty) 1 else page
                        )
                    }
                }
                is ResultWrapper.Error -> _uiState.update {
                    it.copy(mediaState = ResultWrapper.Error(result.exception), currentPage = page)
                }
                is ResultWrapper.Idle -> {
                }
            }
        }
    }
}
