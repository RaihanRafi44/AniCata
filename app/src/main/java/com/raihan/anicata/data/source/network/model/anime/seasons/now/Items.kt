package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Items(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("per_page")
    var perPage: Int?,
    @SerializedName("total")
    var total: Int?
)
