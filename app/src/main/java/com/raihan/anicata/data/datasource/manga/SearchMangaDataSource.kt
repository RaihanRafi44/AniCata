package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.manga.search.SearchMangaResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SearchMangaDataSource {
    suspend fun getSearchMangaList(
        query: String?,
        page: Int?,
        limit: Int?,
        type: String?,
        score: Int?,
        genres: String?,
        orderBy: String?,
        sort: String?
    ): SearchMangaResponse
}

class SearchMangaApiDataSource(private val service: AniCataApiService): SearchMangaDataSource{
    override suspend fun getSearchMangaList(
        query: String?,
        page: Int?,
        limit: Int?,
        type: String?,
        score: Int?,
        genres: String?,
        orderBy: String?,
        sort: String?
    ): SearchMangaResponse {
        return service.getSearchManga(
            q = query,
            page = page,
            limit = limit,
            type = type,
            score = score,
            genres = genres,
            orderBy = orderBy,
            sort = sort
        )
    }
}