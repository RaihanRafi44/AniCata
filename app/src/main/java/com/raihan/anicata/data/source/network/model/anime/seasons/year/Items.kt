package com.raihan.anicata.data.source.network.model.anime.seasons.year


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Items(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("per_page")
    var perPage: Int?,
    @SerializedName("total")
    var total: Int?
)