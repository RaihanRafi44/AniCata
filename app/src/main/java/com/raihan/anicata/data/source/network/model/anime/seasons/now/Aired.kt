package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Aired(
    @SerializedName("from")
    var from: String?,
    @SerializedName("prop")
    var prop: Prop?,
    @SerializedName("to")
    var to: String?
)
