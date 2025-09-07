package com.raihan.anicata.data.source.network.model.manga.top.manga


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Prop(
    @SerializedName("from")
    var from: From?,
    @SerializedName("string")
    var string: String?,
    @SerializedName("to")
    var to: To?
)