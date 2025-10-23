package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.manga.MangaDetailFullDataSource
import com.raihan.anicata.data.mapper.manga.toDetailFullManga
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaDetailRepository {
    fun getMangaDetailList(id : Int) : Flow<ResultWrapper<MangaDetailFull>>
}

class MangaDetailRepositoryImpl(private val dataSource: MangaDetailFullDataSource) : MangaDetailRepository {
    override fun getMangaDetailList(id: Int): Flow<ResultWrapper<MangaDetailFull>> {
        return proceedFlow {
            dataSource.getMangaDetailList(id).data.toDetailFullManga()
        }
    }
}