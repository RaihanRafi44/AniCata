package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.AnimeDetailFullDataSource
import com.raihan.anicata.data.mapper.anime.toDetailAnime
import com.raihan.anicata.data.mapper.anime.toDetailFullAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeDetailRepository {
    fun getAnimeDetailList(id : Int) : Flow<ResultWrapper<AnimeData>>
}

class AnimeDetailRepositoryImpl(private val dataSource: AnimeDetailFullDataSource) : AnimeDetailRepository {
    override fun getAnimeDetailList(id: Int): Flow<ResultWrapper<AnimeData>> {
        return proceedFlow{
            dataSource.getAnimeDetailFullList(id).data.toDetailFullAnime()
        }
    }
}