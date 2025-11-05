package com.raihan.anicata.data.source.network.model.anime.staff


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StaffAnimeResponse(
    @SerializedName("data")
    var data: List<StaffAnimeData>?
)