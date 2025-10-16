package com.raihan.anicata.data.source.network.model.manga.top.manga


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TopMangaResponse(
    @SerializedName("data")
    var data: List<TopMangaData>?,
    @SerializedName("pagination")
    var pagination: Pagination?
)