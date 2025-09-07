package com.raihan.anicata.data.source.network.model.anime.seasons.year


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Genre(
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("url")
    var url: String?
)