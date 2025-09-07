package com.raihan.anicata.data.source.network.model.anime.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class To(
    @SerializedName("day")
    var day: Int?,
    @SerializedName("month")
    var month: Int?,
    @SerializedName("year")
    var year: Int?
)