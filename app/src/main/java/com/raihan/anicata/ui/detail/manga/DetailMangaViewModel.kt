package com.raihan.anicata.ui.detail.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.domain.DetailAnimeUiState
import com.raihan.anicata.data.model.domain.DetailMangaUiState
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepository
import com.raihan.anicata.data.repository.anime.AnimeDetailRepository
import com.raihan.anicata.data.repository.anime.AnimeStaffRepository
import com.raihan.anicata.data.repository.manga.MangaCharacterRepository
import com.raihan.anicata.data.repository.manga.MangaDetailRepository
import com.raihan.anicata.data.repository.manga.MangaStaffRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailMangaViewModel (
    private val detailMangaRepository: MangaDetailRepository,
    private val characterMangaRepository: MangaCharacterRepository,
    private val staffMangaRepository: MangaStaffRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailMangaUiState())
    val uiState: StateFlow<DetailMangaUiState> = _uiState.asStateFlow()

    fun getMangaDetails(id: Int) {
        viewModelScope.launch {
            detailMangaRepository.getMangaDetailList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(mangaDetail = result)
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
}