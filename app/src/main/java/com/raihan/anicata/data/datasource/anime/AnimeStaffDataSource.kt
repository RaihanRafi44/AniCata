package com.raihan.anicata.data.datasource.anime

import com.raihan.anicata.data.source.network.model.anime.characters.CharactersAnimeResponse
import com.raihan.anicata.data.source.network.model.anime.staff.StaffAnimeResponse
import com.raihan.anicata.data.source.network.service.AniCataApiService

interface AnimeStaffDataSource{
    suspend fun getAnimeStaffList(id : Int) : StaffAnimeResponse
}

class AnimeStaffApiDataSource(private val service: AniCataApiService) : AnimeStaffDataSource {
    override suspend fun getAnimeStaffList(id: Int): StaffAnimeResponse {
        return service.getAnimeStaff(id)
    }
}