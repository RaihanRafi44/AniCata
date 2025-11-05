package com.raihan.anicata.data.repository.anime

import com.raihan.anicata.data.datasource.anime.AnimeCharactersDataSource
import com.raihan.anicata.data.datasource.anime.AnimeStaffDataSource
import com.raihan.anicata.data.mapper.anime.toDetailCharactersAnime
import com.raihan.anicata.data.mapper.anime.toStaffAnimeList
import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AnimeStaffRepository {
    fun getAnimeStaffList(id : Int) : Flow<ResultWrapper<List<StaffAnime>>>
}

class AnimeStaffRepositoryImpl(private val dataSource: AnimeStaffDataSource) : AnimeStaffRepository {
    override fun getAnimeStaffList(id: Int): Flow<ResultWrapper<List<StaffAnime>>> {
        return proceedFlow{
            dataSource.getAnimeStaffList(id).data.toStaffAnimeList()
        }
    }
}