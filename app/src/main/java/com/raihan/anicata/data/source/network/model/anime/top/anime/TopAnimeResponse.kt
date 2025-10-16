package com.raihan.anicata.data.source.network.model.anime.top.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TopAnimeResponse(
    @SerializedName("data")
    var data: List<TopAnimeData?>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)