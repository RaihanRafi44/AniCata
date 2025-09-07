package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.genres.GenreAnimeResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface AnimeGenreDataSource {
    suspend fun getAnimeGenreList(filter: String): GenreAnimeResponse
}

class AnimeGenreApiDataSource(private val service: AniCataApiService) : AnimeGenreDataSource {
    override suspend fun getAnimeGenreList(filter: String): GenreAnimeResponse {
        return service.getGenreAnime(filter)
    }
}