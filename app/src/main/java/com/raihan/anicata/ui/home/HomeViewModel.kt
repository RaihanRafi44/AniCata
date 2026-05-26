package com.raihan.anicata.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.data.repository.anime.AnimeSeasonNowRepository
import com.raihan.anicata.data.repository.anime.AnimeSeasonUpcomingRepository
import com.raihan.anicata.data.repository.anime.AnimeTopRepository
import com.raihan.anicata.data.repository.user.RecentlyViewedRepository
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val topAnimeRepository: AnimeTopRepository,
    private val seasonNowRepository: AnimeSeasonNowRepository,
    private val seasonUpcomingRepository: AnimeSeasonUpcomingRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository
) : ViewModel() {

    val recentlyViewed = recentlyViewedRepository.getRecentlyViewed().asLiveData(Dispatchers.IO)

    private val refreshTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val topRatedState: StateFlow<ResultWrapper<List<TopAnime>>> = refreshTrigger
        .flatMapLatest { topAnimeRepository.getTopAnimeList(type = "", filter = "", page = 1, limit = 10) }
        .map { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    val animeList = result.payload?.first ?: emptyList()
                    val sortedList = animeList.sortedWith(
                        compareByDescending<TopAnime> { it.score }.thenByDescending { it.members }
                    )
                    if (sortedList.isEmpty()) ResultWrapper.Empty() else ResultWrapper.Success(sortedList)
                }
                is ResultWrapper.Loading -> ResultWrapper.Loading()
                is ResultWrapper.Error -> ResultWrapper.Error(result.exception)
                is ResultWrapper.Empty -> ResultWrapper.Empty()
                is ResultWrapper.Idle -> ResultWrapper.Idle()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ResultWrapper.Loading()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val nowAiringState: StateFlow<ResultWrapper<List<SeasonAnimeNow>>> = refreshTrigger
        .flatMapLatest { seasonNowRepository.getSeasonNowAnimeList(filter = "tv", continuing = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ResultWrapper.Loading()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val upcomingState: StateFlow<ResultWrapper<List<SeasonAnimeUpcoming>>> = refreshTrigger
        .flatMapLatest { seasonUpcomingRepository.getSeasonUpcomingAnimeList(filter = "tv", continuing = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ResultWrapper.Loading()
        )

    fun refreshData() {
        refreshTrigger.value++
    }
}