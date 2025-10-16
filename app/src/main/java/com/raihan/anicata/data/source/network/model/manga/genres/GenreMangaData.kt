package com.raihan.anicata.data.source.network.model.manga.genres


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GenreMangaData(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("url")
    var url: String?
)