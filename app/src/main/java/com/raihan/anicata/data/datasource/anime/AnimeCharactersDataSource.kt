package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.characters.CharactersAnimeResponse
import com.raihan.anicata.data.source.network.model.anime.full.AnimeDetailFullResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface AnimeCharactersDataSource{
    suspend fun getAnimeCharactersList(id : Int) : CharactersAnimeResponse
}

class AnimeCharactersApiDataSource(private val service: AniCataApiService) : AnimeCharactersDataSource {
    override suspend fun getAnimeCharactersList(id: Int): CharactersAnimeResponse {
        return service.getAnimeCharacters(id)
    }
}