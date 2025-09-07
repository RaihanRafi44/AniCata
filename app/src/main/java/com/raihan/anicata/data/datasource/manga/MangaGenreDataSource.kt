package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.manga.genres.GenreMangaResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface MangaGenreDataSource {
    suspend fun getMangaGenreList(filter: String): GenreMangaResponse
}

class MangaGenreApiDataSource(private val service: AniCataApiService) : MangaGenreDataSource {
    override suspend fun getMangaGenreList(filter: String): GenreMangaResponse {
        return service.getGenreManga(filter)
    }
}