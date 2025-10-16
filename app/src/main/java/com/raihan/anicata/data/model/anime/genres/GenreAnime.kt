package com.raihan.anicata.data.model.anime.genres

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreAnime(
    val count: Int,
    val id: Int,
    val name: String,
    val url: String
) : Parcelable
