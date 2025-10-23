package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.manga.TopMangaDataSource
import com.raihan.anicata.data.mapper.anime.toTopAnimeList
import com.raihan.anicata.data.mapper.manga.toTopMangaList
import com.raihan.anicata.data.model.manga.top.TopManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaTopRepository {
    fun getTopMangaList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ) : Flow<ResultWrapper<Pair<List<TopManga>, Int?>>>
}

class MangaTopRepositoryImpl(private val dataSource: TopMangaDataSource) : MangaTopRepository {
    override fun getTopMangaList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ): Flow<ResultWrapper<Pair<List<TopManga>, Int?>>> {
        return proceedFlow {
            val response = dataSource.getTopMangaList(
                type = type,
                filter = filter,
                page = page,
                limit = limit
            )
            // Kembalikan Pair: daftar anime dan total halaman
            val mangaList = response.data.toTopMangaList()
            val totalPages = response.pagination?.lastVisiblePage
            Pair(mangaList, totalPages)
        }
    }
}