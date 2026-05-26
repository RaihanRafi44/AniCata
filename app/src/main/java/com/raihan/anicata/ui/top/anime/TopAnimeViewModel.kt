package com.raihan.anicata.ui.top.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopAnimeViewModel(private val repository: AnimeTopRepository) : ViewModel() {

    private val _topAnimeState = MutableStateFlow<ResultWrapper<Pair<List<TopAnime>, Int>>>(
        ResultWrapper.Idle())
    val topAnimeState: StateFlow<ResultWrapper<Pair<List<TopAnime>, Int>>> = _topAnimeState

    private val animeCache = mutableMapOf<String, Pair<List<TopAnime>, Int>>()

    private fun getCacheKey(filter: String, page: Int): String {
        return "filter=$filter&page=$page"
    }

    fun getTopAnimeData(
        page: Int,
        type: String,
        filter: String,
        limit: Int = 25,
        forceRefresh: Boolean = false
    ) {
        val cacheKey = getCacheKey(filter, page)

        if (forceRefresh) {
            animeCache.remove(cacheKey)
        } else if (animeCache.containsKey(cacheKey)) {
            _topAnimeState.value = ResultWrapper.Success(animeCache[cacheKey]!!)
            return
        }

        /*if (animeCache.containsKey(cacheKey)) {
            _topAnimeState.value = ResultWrapper.Success(animeCache[cacheKey]!!)
            return
        }*/

        viewModelScope.launch {
            _topAnimeState.value = ResultWrapper.Loading()

            repository.getTopAnimeList(type, filter, page, limit).collectLatest { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        if (_topAnimeState.value !is ResultWrapper.Loading) {
                            _topAnimeState.value = ResultWrapper.Loading()
                        }
                    }

                    is ResultWrapper.Success, is ResultWrapper.Empty -> {
                        var animeList = result.payload?.first ?: emptyList()
                        val totalPages = result.payload?.second ?: 1

                        if (filter == "") {
                            animeList = animeList.sortedWith(
                                compareByDescending<TopAnime> { it.score }
                                    .thenByDescending { it.members }
                            )
                        }

                        val finalPair = Pair(animeList, totalPages)

                        animeCache[cacheKey] = finalPair

                        if (animeList.isEmpty() || result is ResultWrapper.Empty) {
                            _topAnimeState.value = ResultWrapper.Empty(finalPair)
                        } else {
                            _topAnimeState.value = ResultWrapper.Success(finalPair)
                        }

                    }

                    is ResultWrapper.Error -> {
                        _topAnimeState.value = ResultWrapper.Error(result.exception)
                    }
                    is ResultWrapper.Idle -> {}
                }
            }
        }
    }
}