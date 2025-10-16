package com.raihan.anicata.data.source.network.model.manga.genres


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GenreMangaResponse(
    @SerializedName("data")
    var data: List<GenreMangaData>?
)