package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.anime.characters.CharactersAnimeResponse
import com.raihan.anicata.data.source.network.model.manga.characters.CharactersMangaResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface MangaCharactersDataSource{
    suspend fun getMangaCharactersList(id : Int) : CharactersMangaResponse
}

class MangaCharactersApiDataSource(private val service: AniCataApiService) : MangaCharactersDataSource {
    override suspend fun getMangaCharactersList(id: Int): CharactersMangaResponse {
        return service.getMangaCharacters(id)
    }
}