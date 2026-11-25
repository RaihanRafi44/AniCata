package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.seasons.upcoming.SeasonUpcomingResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SeasonUpcomingDataSource {
    suspend fun getSeasonUpcomingList(filter: String, continuing: Boolean, page: Int = 1): SeasonUpcomingResponse
}

class SeasonUpcomingApiDataSource(private val service: AniCataApiService) : SeasonUpcomingDataSource {
    override suspend fun getSeasonUpcomingList(filter: String, continuing: Boolean, page: Int): SeasonUpcomingResponse {
        return service.getSeasonUpcoming(filter, continuing, page)
    }
}