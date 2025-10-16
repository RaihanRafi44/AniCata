package com.raihan.anicata.data.source.network.model.manga.search


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SearchMangaResponse(
    @SerializedName("data")
    var data: List<SearchMangaData>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)