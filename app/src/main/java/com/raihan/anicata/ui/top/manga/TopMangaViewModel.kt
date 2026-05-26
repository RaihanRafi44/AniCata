package com.raihan.anicata.ui.top.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.model.manga.top.TopManga
import com.raihan.anicata.data.repository.manga.MangaTopRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class TopMangaViewModel(private val repository: MangaTopRepository) : ViewModel() {

    private val _topMangaState = MutableStateFlow<ResultWrapper<Pair<List<TopManga>, Int>>>(
        ResultWrapper.Idle())

    val topMangaState: StateFlow<ResultWrapper<Pair<List<TopManga>, Int>>> = _topMangaState

    private val mangaCache = mutableMapOf<String, Pair<List<TopManga>, Int>>()

    private fun getCacheKey(filter: String, page: Int): String {
        return "filter=$filter&page=$page"
    }

    fun getTopMangaData(
        page: Int,
        type: String,
        filter: String,
        limit: Int = 25,
        forceRefreshing: Boolean = false
    ) {
        val cacheKeyManga = getCacheKey(filter, page)

        if (forceRefreshing) {
            mangaCache.remove(cacheKeyManga)
        } else if (mangaCache.containsKey(cacheKeyManga)) {
            _topMangaState.value = ResultWrapper.Success(mangaCache[cacheKeyManga]!!)
            return
        }

        /*if (mangaCache.containsKey(cacheKeyManga)) {
            _topMangaState.value = ResultWrapper.Success(mangaCache[cacheKeyManga]!!)
            return
        }*/

        viewModelScope.launch {
            _topMangaState.value = ResultWrapper.Loading()

            repository.getTopMangaList(type, filter, page, limit).collectLatest { result ->
                when (result) {
                is ResultWrapper.Loading -> {
                    if (_topMangaState.value !is ResultWrapper.Loading) {
                        _topMangaState.value = ResultWrapper.Loading()
                    }
                }

                is ResultWrapper.Success, is ResultWrapper.Empty -> {
                    var mangaList = result.payload?.first ?: emptyList()
                    val totalPages = result.payload?.second ?: 1

                    if (filter == "") {
                        mangaList = mangaList.sortedWith(
                            compareByDescending<TopManga> { it.score }
                                .thenByDescending { it.members }
                        )
                    }

                    val finalPair = Pair(mangaList, totalPages)

                    mangaCache[cacheKeyManga] = finalPair

                    if (mangaList.isEmpty() || result is ResultWrapper.Empty) {
                        _topMangaState.value = ResultWrapper.Empty(finalPair)
                    } else {
                        _topMangaState.value = ResultWrapper.Success(finalPair)
                    }
                }

                    is ResultWrapper.Error -> {
                        _topMangaState.value = ResultWrapper.Error(result.exception)
                }
                    is ResultWrapper.Idle -> {}
                }

            }
        }
    }
}
