package com.raihan.anicata.data.source.network.model.anime.seasons.upcoming


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SeasonUpcomingResponse(
    @SerializedName("data")
    var data: List<Data?>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)