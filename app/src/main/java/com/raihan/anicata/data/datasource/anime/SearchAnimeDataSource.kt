package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.search.SearchAnimeResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface SearchAnimeDataSource {
    suspend fun getSearchAnimeList(
        query: String,
        page: Int,
        limit: Int,
        type: String,
        score: Int,
        genres: String,
        orderBy: String,
        sort: String
    ): SearchAnimeResponse
}

class SearchAnimeApiDataSource(private val service: AniCataApiService) : SearchAnimeDataSource {
    override suspend fun getSearchAnimeList(
        query: String,
        page: Int,
        limit: Int,
        type: String,
        score: Int,
        genres: String,
        orderBy: String,
        sort: String
    ): SearchAnimeResponse {
        return service.getSearchAnime(
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