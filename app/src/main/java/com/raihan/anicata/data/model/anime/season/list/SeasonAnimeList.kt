package com.raihan.anicata.data.model.anime.season.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonAnimeList(
    val year: Int,
    val seasons: List<String>
) : Parcelable
