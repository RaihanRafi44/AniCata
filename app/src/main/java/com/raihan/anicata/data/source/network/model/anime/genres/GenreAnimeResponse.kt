package com.raihan.anicata.data.source.network.model.anime.genres


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GenreAnimeResponse(
    @SerializedName("data")
    var data: List<GenreAnimeData>?
)