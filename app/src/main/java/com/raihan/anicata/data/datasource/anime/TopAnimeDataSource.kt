package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.top.anime.TopAnimeResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface TopAnimeDataSource {
    suspend fun getTopAnimeList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ) : TopAnimeResponse
}

class TopAnimeApiDataSource(private val service: AniCataApiService) : TopAnimeDataSource {
    override suspend fun getTopAnimeList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ): TopAnimeResponse {
        return service.getTopAnime(
            type = type,
            filter = filter,
            page = page,
            limit = limit
        )
    }
}