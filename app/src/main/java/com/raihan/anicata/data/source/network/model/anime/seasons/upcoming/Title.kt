package com.raihan.anicata.data.source.network.model.anime.seasons.upcoming


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Title(
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?
)