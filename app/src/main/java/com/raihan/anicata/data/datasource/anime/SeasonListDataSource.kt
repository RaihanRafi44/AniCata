package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.seasons.lists.SeasonListsResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SeasonListDataSource {
    suspend fun getSeasonList(): SeasonListsResponse
}

class SeasonListApiDataSource(private val service: AniCataApiService) : SeasonListDataSource {
    override suspend fun getSeasonList(): SeasonListsResponse {
        return service.getSeasonList()
    }
}