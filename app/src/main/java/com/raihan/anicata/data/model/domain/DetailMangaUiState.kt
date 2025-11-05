package com.raihan.anicata.data.model.domain

import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.data.model.manga.characters.CharacterManga
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.manga.staff.StaffManga
import com.raihan.anicata.utils.ResultWrapper

data class DetailMangaUiState(
    // Default-nya adalah Loading, agar UI menampilkan shimmer/progress bar
    val mangaDetail: ResultWrapper<MangaDetailFull> = ResultWrapper.Loading(),
    val mangaCharacters: ResultWrapper<List<CharacterManga>> = ResultWrapper.Loading(),
    val mangaStaff: ResultWrapper<List<StaffManga>> = ResultWrapper.Loading()
)
