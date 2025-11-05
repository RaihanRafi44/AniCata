package com.raihan.anicata.data.datasource.manga

import com.raihan.anicata.data.source.network.model.anime.staff.StaffAnimeResponse
import com.raihan.anicata.data.source.network.model.manga.staff.StaffMangaResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface MangaStaffDataSource{
    suspend fun getMangaStaffList(id : Int) : StaffMangaResponse
}

class MangaStaffApiDataSource(private val service: AniCataApiService) : MangaStaffDataSource {
    override suspend fun getMangaStaffList(id: Int): StaffMangaResponse {
        return service.getMangaStaff(id)
    }
}