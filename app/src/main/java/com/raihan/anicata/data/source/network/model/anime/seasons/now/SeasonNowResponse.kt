package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SeasonNowResponse(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)









































