package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.manga.SearchMangaDataSource
import com.raihan.anicata.data.mapper.anime.toSearchAnimeList
import com.raihan.anicata.data.mapper.manga.toSearchMangaList
import com.raihan.anicata.data.model.manga.search.SearchManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaSearchRepository {
    fun getSearchMangaList(
        query: String,
        page: Int,
        limit: Int,
        type: String,
        score: Int,
        genres: String,
        orderBy: String,
        sort: String
    ) : Flow<ResultWrapper<Pair<List<SearchManga>, Int?>>>
}

class MangaSearchRepositoryImpl(private val dataSource: SearchMangaDataSource) : MangaSearchRepository {
    override fun getSearchMangaList(
        query: String,
        page: Int,
        limit: Int,
        type: String,
        score: Int,
        genres: String,
        orderBy: String,
        sort: String
    ): Flow<ResultWrapper<Pair<List<SearchManga>, Int?>>> {
        return proceedFlow {
            val response =
                dataSource.getSearchMangaList(
                    query = query,
                    page = page,
                    limit = limit,
                    type = type,
                    score = score,
                    genres = genres,
                    orderBy = orderBy,
                    sort = sort
            )
            // Kembalikan Pair: daftar anime dan total halaman
            val animeList = response.data.toSearchMangaList()
            val totalPages = response.pagination?.lastVisiblePage
            Pair(animeList, totalPages)
        }
    }
}