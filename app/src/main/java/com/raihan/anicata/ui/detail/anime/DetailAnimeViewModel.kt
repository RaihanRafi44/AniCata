package com.raihan.anicata.ui.detail.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.domain.DetailAnimeUiState
import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepository
import com.raihan.anicata.data.repository.anime.AnimeDetailRepository
import com.raihan.anicata.data.repository.anime.AnimeStaffRepository
import com.raihan.anicata.data.repository.user.FavoriteAnimeRepository
import com.raihan.anicata.data.repository.user.LibraryRepository
import com.raihan.anicata.data.repository.user.RecentlyViewedRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailAnimeViewModel(
    private val detailRepository: AnimeDetailRepository,
    private val characterRepository: AnimeCharacterRepository,
    private val staffRepository: AnimeStaffRepository,
    private val libraryRepository: LibraryRepository,
    private val favoriteRepository: FavoriteAnimeRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailAnimeUiState())
    val uiState: StateFlow<DetailAnimeUiState> = _uiState.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun getAnimeDetails(id: Int) {
        checkIsBookmarked(id.toString())
        checkIsFavorite(id.toString())

        viewModelScope.launch {
            detailRepository.getAnimeDetailList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(animeDetail = result)
                }

                if (result is ResultWrapper.Success) {
                    result.payload?.let { detail ->
                        saveToRecentlyViewed(detail)
                    }
                }
            }
        }

        viewModelScope.launch {
            delay(500L)
            characterRepository.getAnimeCharactersList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(animeCharacters = result)
                }
            }
        }

        viewModelScope.launch {
            delay(1000L)
            staffRepository.getAnimeStaffList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(animeStaff = result)
                }
            }
        }
    }

    private fun checkIsBookmarked(id: String) {
        viewModelScope.launch {
            libraryRepository.isAnimeBookmarked(id).collect { result ->
                if (result is ResultWrapper.Success) {
                    _isBookmarked.value = result.payload ?: false
                }
            }
        }
    }

    private fun checkIsFavorite(id: String) {
        viewModelScope.launch {
            favoriteRepository.isAnimeFavorites(id).collect { result ->
                if (result is ResultWrapper.Success) {
                    _isFavorite.value = result.payload ?: false
                }
            }
        }
    }

    fun saveToBookmark(currentAnime: AnimeData) {

        _isBookmarked.value = true

        viewModelScope.launch {

            libraryRepository.saveAnimeToBookmark(currentAnime).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil disimpan ke Anime Bookmark")

                        //_isBookmarked.value = true

                    }
                    is ResultWrapper.Error -> {
                        println("Gagal menyimpan: ${result.exception?.message}")

                        _isBookmarked.value = false
                    }
                    else -> {}
                }
            }
        }
    }

    fun saveToFavorite(currentAnime: AnimeData) {

        _isFavorite.value = true

        viewModelScope.launch {

            favoriteRepository.saveAnimeToFavorite(currentAnime).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil disimpan ke Anime Favorite")

                        //_isBookmarked.value = true

                    }
                    is ResultWrapper.Error -> {
                        println("Gagal menyimpan: ${result.exception?.message}")

                        _isFavorite.value = false
                    }
                    else -> {}
                }
            }
        }
    }

    fun removeFromBookmark(animeId: String) {
        _isBookmarked.value = false
        viewModelScope.launch {
            // Panggil fungsi remove dari Repository (pastikan fungsi ini ada di Repo)
            libraryRepository.removeAnimeFromBookmark(animeId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil dihapus dari Bookmark")
                        // Update UI jadi false (Belum Bookmarked) -> Tombol & Ikon berubah otomatis
                        //_isBookmarked.value = false
                    }
                    is ResultWrapper.Error -> {
                        println("Gagal menghapus: ${result.exception?.message}")

                        _isBookmarked.value = true
                    }
                    else -> {}
                }
            }
        }
    }

    fun removeFromFavorite(animeId: String) {
        _isFavorite.value = false
        viewModelScope.launch {
            // Panggil fungsi remove dari Repository (pastikan fungsi ini ada di Repo)
            favoriteRepository.removeAnimeFromFavorite(animeId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil dihapus dari Favorite")
                        // Update UI jadi false (Belum Bookmarked) -> Tombol & Ikon berubah otomatis
                        //_isBookmarked.value = false
                    }
                    is ResultWrapper.Error -> {
                        println("Gagal menghapus: ${result.exception?.message}")

                        _isFavorite.value = true
                    }
                    else -> {}
                }
            }
        }
    }

    private fun saveToRecentlyViewed(anime: AnimeData) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyItem = RecentlyViewed(
                id = anime.id, // Sesuaikan dengan ID dari model Jikan API Anda
                title = anime.title ?: "Unknown Title",
                imageUrl = anime.images?.jpg?.largeImageUrl ?: "",
                type = anime.type ?: "Anime"
            )
            // Panggil fungsi create yang sudah kita buat sebelumnya
            recentlyViewedRepository.createRecentlyViewed(historyItem).collect()
        }
    }
}