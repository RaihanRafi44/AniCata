package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.manga.MangaGenreDataSource
import com.raihan.anicata.data.mapper.manga.toGenreMangaList
import com.raihan.anicata.data.model.manga.genres.GenreManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaGenreRepository {
    fun getGenreMangaList(filter: String) : Flow<ResultWrapper<List<GenreManga>>>
}

class MangaGenreRepositoryImpl(private val dataSource: MangaGenreDataSource) : MangaGenreRepository{
    override fun getGenreMangaList(filter: String): Flow<ResultWrapper<List<GenreManga>>> {
        return proceedFlow {
            dataSource.getMangaGenreList(filter).data.toGenreMangaList()
        }
    }
}