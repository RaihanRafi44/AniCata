package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.SearchAnimeDataSource
import com.raihan.anicata.data.mapper.anime.toSearchAnimeList
import com.raihan.anicata.data.mapper.anime.toTopAnimeList
import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeSearchRepository {
    fun getSearchAnimeList(
        query: String?,
        page: Int?,
        limit: Int?,
        type: String?,
        score: Double?,
        genres: String?,
        orderBy: String?,
        sort: String?
    ) : Flow<ResultWrapper<Pair<List<SearchAnime>, Int?>>>
}

class AnimeSearchRepositoryImpl(private val dataSource: SearchAnimeDataSource) : AnimeSearchRepository {
    override fun getSearchAnimeList(
        query: String?,
        page: Int?,
        limit: Int?,
        type: String?,
        score: Double?,
        genres: String?,
        orderBy: String?,
        sort: String?
    ): Flow<ResultWrapper<Pair<List<SearchAnime>, Int?>>> {
        return proceedFlow {
            val response =
                dataSource.getSearchAnimeList(
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
            val animeList = response.data.toSearchAnimeList()
            val totalPages = response.pagination?.lastVisiblePage
            Pair(animeList, totalPages)
        }
    }
}