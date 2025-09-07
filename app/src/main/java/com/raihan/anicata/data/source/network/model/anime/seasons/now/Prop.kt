package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Prop(
    @SerializedName("from")
    var from: From?,
    @SerializedName("string")
    var string: String?,
    @SerializedName("to")
    var to: To?
)
