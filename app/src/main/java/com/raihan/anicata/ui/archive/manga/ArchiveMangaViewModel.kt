package com.raihan.anicata.ui.archive.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.storage.UserBookmarkManga
import com.raihan.anicata.data.model.storage.UserFavoriteManga
import com.raihan.anicata.data.repository.user.BookmarkMangaRepository
import com.raihan.anicata.data.repository.user.FavoriteMangaRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArchiveMangaViewModel(
    private val bookmarkMangaRepository: BookmarkMangaRepository,
    private val favoriteMangaRepository: FavoriteMangaRepository
) : ViewModel() {

    private val _bookmarkMangaState = MutableStateFlow<ResultWrapper<List<UserBookmarkManga>>>(
        ResultWrapper.Idle()
    )
    private val _favoriteMangaState = MutableStateFlow<ResultWrapper<List<UserFavoriteManga>>>(
        ResultWrapper.Idle()
    )

    val bookmarkMangaState: StateFlow<ResultWrapper<List<UserBookmarkManga>>> = _bookmarkMangaState
    val favoriteMangaState: StateFlow<ResultWrapper<List<UserFavoriteManga>>> = _favoriteMangaState

    private val _selectedId = MutableStateFlow<Set<String>>(emptySet())
    val selectedId: StateFlow<Set<String>> = _selectedId.asStateFlow()

    private val _isSelectionModeManga = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionModeManga.asStateFlow()

    fun getBookmarksManga() {
        viewModelScope.launch {
            bookmarkMangaRepository.getMangaBookmarks().collect {
                _bookmarkMangaState.value = it
            }
        }
    }

    fun getFavoritesManga() {
        viewModelScope.launch {
            favoriteMangaRepository.getMangaFavorite().collect {
                _favoriteMangaState.value = it
            }
        }
    }

    fun toggleSelection(id: String) {
        val currentSelection = _selectedId.value.toMutableSet()
        if (currentSelection.contains(id)) {
            currentSelection.remove(id)
        } else {
            currentSelection.add(id)
        }
        _selectedId.value = currentSelection

        if (currentSelection.isEmpty()) {
            _isSelectionModeManga.value = false
        }
    }

    fun startSelection(id: String) {
        _isSelectionModeManga.value = true
        _selectedId.value = setOf(id)
    }

    fun clearSelection() {
        _isSelectionModeManga.value = false
        _selectedId.value = emptySet()
    }

    fun deleteSelectedBookmarks() {
        viewModelScope.launch {
            val idToDelete = _selectedId.value.toList()

            idToDelete.forEach { id ->
                bookmarkMangaRepository.removeMangaFromBookmark(id).collect {

                }
            }

            clearSelection()
            getBookmarksManga()
        }
    }

    fun deleteSelectedFavorites() {
        viewModelScope.launch {
            val idsToDelete = _selectedId.value.toList()

            idsToDelete.forEach { id ->
                favoriteMangaRepository.removeMangaFromFavorite(id).collect {

                }
            }

            clearSelection()
            getFavoritesManga()
        }
    }

    fun refreshBookmarks() {
        getBookmarksManga()
        }
}