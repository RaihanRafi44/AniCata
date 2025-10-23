package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SeasonYearDataSource
import com.raihan.anicata.data.mapper.anime.toSeasonYearAnimeList
import com.raihan.anicata.data.model.anime.season.year.SeasonAnimeYear
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonYearRepository {
    fun getSeasonAnimeYearList(
        year : Int,
        season: String,
        filter: String,
        continuing: Boolean
    ) : Flow<ResultWrapper<List<SeasonAnimeYear>>>
}

class AnimeSeasonYearRepositoryImpl(private val dataSource: SeasonYearDataSource) : AnimeSeasonYearRepository {
    override fun getSeasonAnimeYearList(
        year: Int,
        season: String,
        filter: String,
        continuing: Boolean
    ): Flow<ResultWrapper<List<SeasonAnimeYear>>> {
        return proceedFlow {
            dataSource.getSeasonYearList(
                year,
                season,
                filter,
                continuing
            ).data.toSeasonYearAnimeList()
        }
    }

}