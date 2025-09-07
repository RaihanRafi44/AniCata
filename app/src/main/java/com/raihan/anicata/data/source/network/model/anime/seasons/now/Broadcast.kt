package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Broadcast(
    @SerializedName("day")
    var day: String?,
    @SerializedName("string")
    var string: String?,
    @SerializedName("time")
    var time: String?,
    @SerializedName("timezone")
    var timezone: String?
)
