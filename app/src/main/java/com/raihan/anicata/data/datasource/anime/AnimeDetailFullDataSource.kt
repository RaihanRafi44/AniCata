package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.full.AnimeDetailFullResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface AnimeDetailFullDataSource {
    suspend fun getAnimeDetailFullList(id : Int) : AnimeDetailFullResponse
}

class AnimeDetailFullApiDataSource(private val service: AniCataApiService) : AnimeDetailFullDataSource {
    override suspend fun getAnimeDetailFullList(id: Int): AnimeDetailFullResponse {
        return service.getAnimeDetailFull(id)
    }
}