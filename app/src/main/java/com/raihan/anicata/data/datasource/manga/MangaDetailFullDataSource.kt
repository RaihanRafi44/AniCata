package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.manga.full.MangaDetailFullResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface MangaDetailFullDataSource {
    suspend fun getMangaDetailList(id : Int) : MangaDetailFullResponse
}

class MangaDetailFullApiDataSource(private val service: AniCataApiService) : MangaDetailFullDataSource {
    override suspend fun getMangaDetailList(id: Int): MangaDetailFullResponse {
        return service.getMangaDetailFull(id)
    }
}