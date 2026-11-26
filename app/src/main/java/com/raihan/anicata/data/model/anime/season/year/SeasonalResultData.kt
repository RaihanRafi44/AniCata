package com.raihan.anicata.data.model.anime.season.year

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonalResultData(
    val animeList: List<SeasonAnimeYear>,
    val totalItems: Int = 0,
    val hasNextPage: Boolean = false
) : Parcelable
