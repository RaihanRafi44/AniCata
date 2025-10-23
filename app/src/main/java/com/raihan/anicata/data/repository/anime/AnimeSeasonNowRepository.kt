package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SeasonNowDataSource
import com.raihan.anicata.data.mapper.anime.toSeasonNowAnimeList
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.season.now.SeasonAnimeNow
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonNowRepository {
    fun getSeasonNowAnimeList(filter: String, continuing: Boolean): Flow<ResultWrapper<List<SeasonAnimeNow>>>
}

class AnimeSeasonNowRepositoryImpl(private val dataSource: SeasonNowDataSource) : AnimeSeasonNowRepository {
    override fun getSeasonNowAnimeList(
        filter: String,
        continuing: Boolean
    ): Flow<ResultWrapper<List<SeasonAnimeNow>>> {
        return proceedFlow {
            dataSource.getSeasonNowList(filter, continuing).data.toSeasonNowAnimeList()
        }
    }
}