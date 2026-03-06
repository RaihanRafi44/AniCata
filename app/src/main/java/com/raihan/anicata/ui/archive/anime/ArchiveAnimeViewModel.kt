package com.raihan.anicata.ui.archive.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import com.raihan.anicata.data.model.storage.UserFavoriteAnime
import com.raihan.anicata.data.repository.user.FavoriteAnimeRepository
import com.raihan.anicata.data.repository.user.LibraryRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArchiveAnimeViewModel(
    private val libraryRepository: LibraryRepository,
    private val favoriteAnimeRepository: FavoriteAnimeRepository
) : ViewModel() {

    private val _bookmarkState = MutableStateFlow<ResultWrapper<List<UserBookmarkAnime>>>(ResultWrapper.Idle())
    val bookmarkState: StateFlow<ResultWrapper<List<UserBookmarkAnime>>> = _bookmarkState.asStateFlow()

    private val _favoriteState = MutableStateFlow<ResultWrapper<List<UserFavoriteAnime>>>(ResultWrapper.Idle())
    val favoriteState: StateFlow<ResultWrapper<List<UserFavoriteAnime>>> = _favoriteState.asStateFlow()


    // 1. STATE UNTUK ITEM YANG DIPILIH
    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    // 2. STATE UNTUK MODE SELEKSI (Apakah sedang memilih?)
    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()

    fun getBookmarks() {
        viewModelScope.launch {
            libraryRepository.getAnimeBookmarks().collect {
                _bookmarkState. value = it
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            favoriteAnimeRepository.getAnimeFavorite().collect {
                _favoriteState.value = it
            }
        }
    }

    // 3. FUNGSI TOGGLE SELEKSI (Dipanggil saat item diklik di mode seleksi)
    fun toggleSelection(id: String) {
        val currentSelection = _selectedIds.value.toMutableSet()
        if (currentSelection.contains(id)) {
            currentSelection.remove(id)
        } else {
            currentSelection.add(id)
        }
        _selectedIds.value = currentSelection

        // Jika tidak ada yang dipilih, keluar dari mode seleksi
        if (currentSelection.isEmpty()) {
            _isSelectionMode.value = false
        }
    }

    // 4. MASUK MODE SELEKSI (Dipanggil saat Long Press)
    fun startSelection(id: String) {
        _isSelectionMode.value = true
        _selectedIds.value = setOf(id)
    }

    // 5. BATALKAN SELEKSI (Tombol Back atau setelah hapus)
    fun clearSelection() {
        _isSelectionMode.value = false
        _selectedIds.value = emptySet()
    }

    // 6. EKSEKUSI HAPUS DATA
    fun deleteSelectedBookmarks() {
        viewModelScope.launch {
            val idsToDelete = _selectedIds.value.toList()

            // Loop hapus satu per satu (bisa dioptimalkan dengan batch delete di repo nanti)
            idsToDelete.forEach { id ->
                libraryRepository.removeAnimeFromBookmark(id).collect {
                    // Handle result per item if needed
                }
            }

            // Refresh data dan reset seleksi
            clearSelection()
            getBookmarks()
        }
    }

    fun deleteSelectedFavorites() {
        viewModelScope.launch {
            val idsToDelete = _selectedIds.value.toList()

            // Loop hapus satu per satu (bisa dioptimalkan dengan batch delete di repo nanti)
            idsToDelete.forEach { id ->
                favoriteAnimeRepository.removeAnimeFromFavorite(id).collect {

                }
            }

            clearSelection()
            getFavorites()
        }
    }

    fun refreshBookmarks() {
        getBookmarks()
    }
}