package com.raihan.anicata.data.source.network.model.anime.seasons.upcoming


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Theme(
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("url")
    var url: String?
)