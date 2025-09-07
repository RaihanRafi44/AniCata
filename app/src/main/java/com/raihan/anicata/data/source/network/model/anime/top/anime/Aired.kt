package com.raihan.anicata.data.source.network.model.anime.top.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Aired(
    @SerializedName("from")
    var from: String?,
    @SerializedName("prop")
    var prop: Prop?,
    @SerializedName("to")
    var to: String?
)