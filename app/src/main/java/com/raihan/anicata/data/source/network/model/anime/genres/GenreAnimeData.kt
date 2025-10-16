package com.raihan.anicata.data.source.network.model.anime.genres


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GenreAnimeData(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("url")
    var url: String?
)