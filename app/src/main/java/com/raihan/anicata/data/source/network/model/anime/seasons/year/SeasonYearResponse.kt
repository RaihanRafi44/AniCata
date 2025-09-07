package com.raihan.anicata.data.source.network.model.anime.seasons.year


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SeasonYearResponse(
    @SerializedName("data")
    var data: List<Data?>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)