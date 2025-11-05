package com.raihan.anicata.data.repository.manga

import com.raihan.anicata.data.datasource.manga.MangaCharactersDataSource
import com.raihan.anicata.data.datasource.manga.MangaStaffDataSource
import com.raihan.anicata.data.mapper.manga.toDetailCharactersManga
import com.raihan.anicata.data.mapper.manga.toStaffMangaList
import com.raihan.anicata.data.model.manga.characters.CharacterManga
import com.raihan.anicata.data.model.manga.staff.StaffManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MangaStaffRepository {
    fun getMangaStaffList(id : Int) : Flow<ResultWrapper<List<StaffManga>>>
}

class MangaStaffRepositoryImpl(private val dataSource: MangaStaffDataSource) : MangaStaffRepository {
    override fun getMangaStaffList(id: Int): Flow<ResultWrapper<List<StaffManga>>> {
        return proceedFlow{
            dataSource.getMangaStaffList(id).data.toStaffMangaList()
        }
    }
}