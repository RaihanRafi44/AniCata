package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.seasons.year.SeasonYearResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SeasonYearDataSource {
    suspend fun getSeasonYearList(
        year: Int,
        season: String,
        filter: String,
        continuing: Boolean,
        page: Int = 1,
        orderBy: String? = null,
        sort: String? = null
    ): SeasonYearResponse
}

class SeasonYearApiDataSource(private val service: AniCataApiService): SeasonYearDataSource {
    override suspend fun getSeasonYearList(
        year: Int,
        season: String,
        filter: String,
        continuing: Boolean,
        page: Int,
        orderBy: String?,
        sort: String?
    ): SeasonYearResponse {
        return service.getSeasonYear(
            year,
            season,
            filter,
            continuing,
            page,
            orderBy,
            sort
        )
    }
}
