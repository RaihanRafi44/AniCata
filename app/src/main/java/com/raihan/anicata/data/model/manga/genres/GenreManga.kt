package com.raihan.anicata.data.model.manga.genres

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreManga(
    val id: Int,
    val name: String,
    val url: String,
    val count: Int
) : Parcelable
