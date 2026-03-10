package com.raihan.anicata.ui.detail.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.domain.DetailAnimeUiState
import com.raihan.anicata.data.model.domain.DetailMangaUiState
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepository
import com.raihan.anicata.data.repository.anime.AnimeDetailRepository
import com.raihan.anicata.data.repository.anime.AnimeStaffRepository
import com.raihan.anicata.data.repository.manga.MangaCharacterRepository
import com.raihan.anicata.data.repository.manga.MangaDetailRepository
import com.raihan.anicata.data.repository.manga.MangaStaffRepository
import com.raihan.anicata.data.repository.user.BookmarkMangaRepository
import com.raihan.anicata.data.repository.user.FavoriteMangaRepository
import com.raihan.anicata.data.repository.user.RecentlyViewedRepository
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailMangaViewModel (
    private val detailMangaRepository: MangaDetailRepository,
    private val characterMangaRepository: MangaCharacterRepository,
    private val staffMangaRepository: MangaStaffRepository,
    private val bookmarkMangaRepository: BookmarkMangaRepository,
    private val favoriteMangaRepository: FavoriteMangaRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailMangaUiState())
    val uiState: StateFlow<DetailMangaUiState> = _uiState.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun getMangaDetails(id: Int) {
        checkIsBookmarked(id.toString())
        checkIsFavorite(id.toString())
        viewModelScope.launch {
            detailMangaRepository.getMangaDetailList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(mangaDetail = result)
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
            characterMangaRepository.getMangaCharactersList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(mangaCharacters = result)
                }
            }
        }

        viewModelScope.launch {
            delay(1000L)
            staffMangaRepository.getMangaStaffList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(mangaStaff = result)
                }
            }
        }
    }

    private fun checkIsBookmarked(id: String) {
        viewModelScope.launch {
            bookmarkMangaRepository.isMangaBookmarked(id).collect { result ->
                if (result is ResultWrapper.Success) {
                    _isBookmarked.value = result.payload ?: false
                }
            }
        }
    }

    private fun checkIsFavorite(id: String) {
        viewModelScope.launch {
            favoriteMangaRepository.isMangaFavorites(id).collect { result ->
                if (result is ResultWrapper.Success) {
                    _isFavorite.value = result.payload ?: false
                }
            }
        }
    }

    fun saveToBookmark(currentManga: MangaDetailFull) {

        _isBookmarked.value = true

        viewModelScope.launch {

            bookmarkMangaRepository.saveMangaToBookmark(currentManga).collect { result ->
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

    fun saveToFavorite(currentManga: MangaDetailFull) {

        _isFavorite.value = true

        viewModelScope.launch {

            favoriteMangaRepository.saveMangaToFavorite(currentManga).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                    println("Berhasil disimpan ke Manga Favorite")
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

    fun removeFromBookmark(mangaId: String) {
        _isBookmarked.value = false
        viewModelScope.launch {
            bookmarkMangaRepository.removeMangaFromBookmark(mangaId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil dihapus dari bookmark manga")
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

    fun removeFromFavorite(mangaId: String) {
        _isFavorite.value = false
        viewModelScope.launch {
            favoriteMangaRepository.removeMangaFromFavorite(mangaId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        println("Berhasil dihapus dari bookmark manga")
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

    private fun saveToRecentlyViewed(manga: MangaDetailFull) {
        viewModelScope.launch(Dispatchers.IO) {
            val historyItem = RecentlyViewed(
                id = manga.id,
                title = manga.title,
                imageUrl = manga.images.jpg.largeImageUrl,
                type = manga.type
            )
            recentlyViewedRepository.createRecentlyViewed(historyItem).collect()
        }
    }
}