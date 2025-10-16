package com.raihan.anicata.data.source.network.model.anime.search


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SearchAnimeResponse(
    @SerializedName("data")
    var data: List<SearchAnimeData>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)