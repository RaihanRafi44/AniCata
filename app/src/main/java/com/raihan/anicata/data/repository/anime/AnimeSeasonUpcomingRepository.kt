package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SeasonUpcomingDataSource
import com.raihan.anicata.data.mapper.anime.toSeasonUpcomingAnimeList
import com.raihan.anicata.data.model.anime.season.upcoming.SeasonAnimeUpcoming
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonUpcomingRepository {
    fun getSeasonUpcomingAnimeList(
        filter: String,
        continuing: Boolean
    ) : Flow<ResultWrapper<List<SeasonAnimeUpcoming>>>
}

class AnimeSeasonUpcomingRepositoryImpl(private val dataSource: SeasonUpcomingDataSource) : AnimeSeasonUpcomingRepository {
    override fun getSeasonUpcomingAnimeList(
        filter: String,
        continuing: Boolean
    ): Flow<ResultWrapper<List<SeasonAnimeUpcoming>>> {
        return proceedFlow {
            dataSource.getSeasonUpcomingList(filter, continuing).data.toSeasonUpcomingAnimeList()
        }
    }
}