package com.raihan.anicata.data.source.network.model.anime.seasons.lists


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("seasons")
    var seasons: List<String?>?,
    @SerializedName("year")
    var year: Int?
)