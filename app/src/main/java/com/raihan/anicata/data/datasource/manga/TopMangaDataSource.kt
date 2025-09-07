package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.manga.top.manga.TopMangaResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface TopMangaDataSource {
    suspend fun getTopMangaList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ): TopMangaResponse
}

class TopMangaApiDataSource(private val service: AniCataApiService): TopMangaDataSource {
    override suspend fun getTopMangaList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ): TopMangaResponse {
        return service.getTopManga(
            type,
            filter,
            page,
            limit
        )
    }
}