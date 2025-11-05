package com.raihan.anicata.data.model.domain

import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.utils.ResultWrapper

// Data class ini akan menampung semua data yang dibutuhkan oleh screen
data class DetailAnimeUiState(
    // Default-nya adalah Loading, agar UI menampilkan shimmer/progress bar
    val animeDetail: ResultWrapper<AnimeData> = ResultWrapper.Loading(),
    val animeCharacters: ResultWrapper<List<CharacterAnime>> = ResultWrapper.Loading(),
    val animeStaff: ResultWrapper<List<StaffAnime>> = ResultWrapper.Loading()
)
