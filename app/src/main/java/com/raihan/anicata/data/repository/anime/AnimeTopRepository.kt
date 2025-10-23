package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.TopAnimeDataSource
import com.raihan.anicata.data.mapper.anime.toTopAnimeList
import com.raihan.anicata.data.model.anime.top.TopAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeTopRepository {
    fun getTopAnimeList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ) : Flow<ResultWrapper<Pair<List<TopAnime>, Int?>>>
}

class AnimeTopRepositoryImpl(private val dataSource: TopAnimeDataSource) : AnimeTopRepository {
    override fun getTopAnimeList(
        type: String,
        filter: String,
        page: Int,
        limit: Int
    ): Flow<ResultWrapper<Pair<List<TopAnime>, Int?>>> {
        return proceedFlow {
            val response = dataSource.getTopAnimeList(
                type = type,
                filter = filter,
                page = page,
                limit = limit
            )
            // Kembalikan Pair: daftar anime dan total halaman
            val animeList = response.data.toTopAnimeList()
            val totalPages = response.pagination?.lastVisiblePage
            Pair(animeList, totalPages)
        }
    }

}