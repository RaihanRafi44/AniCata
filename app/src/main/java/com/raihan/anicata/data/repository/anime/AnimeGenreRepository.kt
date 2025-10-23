package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.AnimeGenreDataSource
import com.raihan.anicata.data.mapper.anime.toGenreAnimeList
import com.raihan.anicata.data.model.anime.genres.GenreAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeGenreRepository {
    fun getGenreAnimeList(filter: String) : Flow<ResultWrapper<List<GenreAnime>>>
}

class AnimeGenreRepositoryImpl(private val dataSource: AnimeGenreDataSource) : AnimeGenreRepository {
    override fun getGenreAnimeList(filter: String): Flow<ResultWrapper<List<GenreAnime>>> {
        return proceedFlow {
            dataSource.getAnimeGenreList(filter).data.toGenreAnimeList()
        }
    }
}