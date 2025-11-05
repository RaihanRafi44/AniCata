package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.AnimeCharactersDataSource
import com.raihan.anicata.data.mapper.anime.toDetailCharactersAnime
import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeCharacterRepository {
    fun getAnimeCharactersList(id : Int) : Flow<ResultWrapper<List<CharacterAnime>>>
}

class AnimeCharacterRepositoryImpl(private val dataSource: AnimeCharactersDataSource) : AnimeCharacterRepository {
    override fun getAnimeCharactersList(id: Int): Flow<ResultWrapper<List<CharacterAnime>>> {
        return proceedFlow{
            dataSource.getAnimeCharactersList(id).data.toDetailCharactersAnime()
        }
    }
}