package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SeasonListDataSource
import com.raihan.anicata.data.mapper.anime.toSeasonAnimeList
import com.raihan.anicata.data.model.anime.season.list.SeasonAnimeList
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonListRepository {
    fun getSeasonListAnime(): Flow<ResultWrapper<List<SeasonAnimeList>>>
}

class AnimeSeasonListRepositoryImpl(private val dataSource: SeasonListDataSource) : AnimeSeasonListRepository {
    override fun getSeasonListAnime(): Flow<ResultWrapper<List<SeasonAnimeList>>> {
        return proceedFlow {
            dataSource.getSeasonList().data.toSeasonAnimeList()
        }
    }
}