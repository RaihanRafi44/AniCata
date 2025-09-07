package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.seasons.now.SeasonNowResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SeasonNowDataSource {
    suspend fun getSeasonNowList(filter: String, continuing: Boolean): SeasonNowResponse
}

class SeasonNowApiDataSource(private val service: AniCataApiService) : SeasonNowDataSource {
    override suspend fun getSeasonNowList(filter: String, continuing: Boolean): SeasonNowResponse {
        return service.getSeasonNow(filter, continuing)
    }
}