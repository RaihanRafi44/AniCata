package com.raihan.anicata.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonUpcomingRepository
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.data.repository.user.RecentlyViewedRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val topAnimeRepository: AnimeTopRepository,
    private val seasonNowRepository: AnimeSeasonNowRepository,
    private val seasonUpcomingRepository: AnimeSeasonUpcomingRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository
) : ViewModel() {

    // State untuk daftar top rated anime
    private val _topRatedAnime = MutableStateFlow<List<TopAnime>>(emptyList())
    val topRatedAnime: StateFlow<List<TopAnime>> = _topRatedAnime.asStateFlow()

    // State untuk loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State untuk pesan error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // == 2. State BARU untuk Now Airing Anime ==
    private val _nowAiringAnime = MutableStateFlow<List<SeasonAnimeNow>>(emptyList())
    val nowAiringAnime: StateFlow<List<SeasonAnimeNow>> = _nowAiringAnime.asStateFlow()

    private val _isLoadingNowAiring = MutableStateFlow(false)
    val isLoadingNowAiring: StateFlow<Boolean> = _isLoadingNowAiring.asStateFlow()

    private val _errorNowAiring = MutableStateFlow<String?>(null)
    val errorNowAiring: StateFlow<String?> = _errorNowAiring.asStateFlow()

    // == 2. State BARU untuk Upcoming Anime ==
    private val _upcomingAnime = MutableStateFlow<List<SeasonAnimeUpcoming>>(emptyList())
    val upcomingAnime: StateFlow<List<SeasonAnimeUpcoming>> = _upcomingAnime.asStateFlow()

    private val _isLoadingUpcoming = MutableStateFlow(false)
    val isLoadingUpcoming: StateFlow<Boolean> = _isLoadingUpcoming.asStateFlow()

    private val _errorUpcoming = MutableStateFlow<String?>(null)
    val errorUpcoming: StateFlow<String?> = _errorUpcoming.asStateFlow()

    val recentlyViewed = recentlyViewedRepository.getRecentlyViewed().asLiveData(Dispatchers.IO)

    init {
        // Langsung panggil saat ViewModel dibuat
        getTopRatedAnime()
        getNowAiringAnime()
        getUpcomingAnime()
    }

    // Fungsi untuk mengambil data (halaman 1, limit 10 untuk home)
    fun getTopRatedAnime() {
        viewModelScope.launch {
            // Kita gunakan filter default ("") karena itu sudah di-sort berdasarkan score
            // di TopAnimeViewModel Anda. Kita hanya ambil 10 data teratas.
            topAnimeRepository.getTopAnimeList(type = "", filter = "", page = 1, limit = 10).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _error.value = null
                    }
                    is ResultWrapper.Success -> {
                        var animeList = result.payload?.first ?: emptyList()

                        // Terapkan logika sorting yang sama seperti di TopAnimeViewModel
                        // untuk memastikan ini adalah "Top Rated"
                        animeList = animeList.sortedWith(
                            compareByDescending<TopAnime> { it.score } // 1. Skor tertinggi
                                .thenByDescending { it.members }       // 2. Member terbanyak
                        )

                        _isLoading.value = false
                        _topRatedAnime.value = animeList
                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _error.value = result.exception?.message ?: "An unknown error occurred"
                    }
                    is ResultWrapper.Empty -> {
                        _isLoading.value = false
                        _topRatedAnime.value = emptyList()
                    }
                    is ResultWrapper.Idle -> {}
                }
            }
        }
    }

    // == 3. Fungsi BARU untuk mengambil data Now Airing ==
    fun getNowAiringAnime() {
        viewModelScope.launch {
            // Kita hanya ambil 10 data terpopuler untuk ditampilkan di home
            seasonNowRepository.getSeasonNowAnimeList(filter = "tv", continuing = false).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoadingNowAiring.value = true
                        _errorNowAiring.value = null
                    }
                    is ResultWrapper.Success -> {
                        // Sortir berdasarkan jumlah members/popularity untuk menampilkan yang paling relevan
                        /*val animeList = result.payload?.sortedByDescending { it.members } ?: emptyList()
                        _isLoadingNowAiring.value = false
                        _nowAiringAnime.value = animeList*/
                        val animeList = result.payload
                            ?.sortedByDescending { it.members }
                            ?.take(10)
                            ?: emptyList()
                        _isLoadingNowAiring.value = false
                        _nowAiringAnime.value = animeList

                    }
                    is ResultWrapper.Error -> {
                        _isLoadingNowAiring.value = false
                        _errorNowAiring.value = result.exception?.message ?: "An unknown error occurred"
                    }
                    is ResultWrapper.Empty -> {
                        _isLoadingNowAiring.value = false
                        _nowAiringAnime.value = emptyList()
                    }
                    is ResultWrapper.Idle -> {}
                }
            }
        }
    }

    fun getUpcomingAnime() {
        viewModelScope.launch {
            seasonUpcomingRepository.getSeasonUpcomingAnimeList(filter = "tv", continuing = false).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoadingUpcoming.value = true
                        _errorUpcoming.value = null
                    }
                    is ResultWrapper.Success -> {
                        val animeList = result.payload
                            ?.sortedByDescending { it.members }
                            ?.take(10)
                            ?: emptyList()
                        _isLoadingUpcoming.value = false
                        _upcomingAnime.value = animeList
                    }
                    is ResultWrapper.Error -> {
                        _isLoadingUpcoming.value = false
                        _errorUpcoming.value = result.exception?.message ?: "An unknown error occurred"
                    }
                    is ResultWrapper.Empty -> {
                        _isLoadingUpcoming.value = false
                        _upcomingAnime.value = emptyList()
                    }
                    is ResultWrapper.Idle -> {}
                }
            }
        }
    }
}