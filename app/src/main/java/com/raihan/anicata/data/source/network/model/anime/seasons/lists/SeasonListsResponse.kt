package com.raihan.anicata.data.source.network.model.anime.seasons.lists


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SeasonListsResponse(
    @SerializedName("data")
    var data: List<SeasonListsData?>?
)