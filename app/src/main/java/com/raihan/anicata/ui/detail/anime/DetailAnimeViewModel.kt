package com.raihan.anicata.ui.detail.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.domain.DetailAnimeUiState
import com.raihan.anicata.data.repository.anime.AnimeCharacterRepository
import com.raihan.anicata.data.repository.anime.AnimeDetailRepository
import com.raihan.anicata.data.repository.anime.AnimeStaffRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailAnimeViewModel(
    private val detailRepository: AnimeDetailRepository,
    private val characterRepository: AnimeCharacterRepository,
    private val staffRepository: AnimeStaffRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailAnimeUiState())
    val uiState: StateFlow<DetailAnimeUiState> = _uiState.asStateFlow()

    fun getAnimeDetails(id: Int) {
        viewModelScope.launch {
            detailRepository.getAnimeDetailList(id).collect { result ->
                _uiState.update { currentState ->
                    currentState.copy(animeDetail = result)
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
}