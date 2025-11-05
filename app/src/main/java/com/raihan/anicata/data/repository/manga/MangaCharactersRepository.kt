package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.anime.AnimeCharactersDataSource
import com.raihan.anicata.data.datasource.manga.MangaCharactersDataSource
import com.raihan.anicata.data.mapper.anime.toDetailCharactersAnime
import com.raihan.anicata.data.mapper.manga.toDetailCharactersManga
import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.data.model.manga.characters.CharacterManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaCharacterRepository {
    fun getMangaCharactersList(id : Int) : Flow<ResultWrapper<List<CharacterManga>>>
}

class MangaCharacterRepositoryImpl(private val dataSource: MangaCharactersDataSource) : MangaCharacterRepository {
    override fun getMangaCharactersList(id: Int): Flow<ResultWrapper<List<CharacterManga>>> {
        return proceedFlow{
            dataSource.getMangaCharactersList(id).data.toDetailCharactersManga()
        }
    }
}