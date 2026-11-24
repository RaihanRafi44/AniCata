package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SeasonYearDataSource
import com.raihan.anicata.data.mapper.anime.toSeasonalResultData
import com.raihan.anicata.data.model.anime.season.year.SeasonalResultData
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSeasonYearRepository {
    fun getSeasonAnimeYearList(
        year: Int,
        season: String,
        filter: String,
        continuing: Boolean,
        page: Int = 1,
        orderBy: String? = null,
        sort: String? = null
    ): Flow<ResultWrapper<SeasonalResultData>>
}

class AnimeSeasonYearRepositoryImpl(private val dataSource: SeasonYearDataSource) : AnimeSeasonYearRepository {
    override fun getSeasonAnimeYearList(
        year: Int,
        season: String,
        filter: String,
        continuing: Boolean,
        page: Int,
        orderBy: String?,
        sort: String?
    ): Flow<ResultWrapper<SeasonalResultData>> {
        return proceedFlow {
            dataSource.getSeasonYearList(
                year,
                season,
                filter,
                continuing,
                page,
                orderBy,
                sort
            ).toSeasonalResultData()
        }
    }
}
