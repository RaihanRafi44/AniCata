package com.raihan.anicata.data.source.network.model.manga.search


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Published(
    @SerializedName("from")
    var from: String?,
    @SerializedName("prop")
    var prop: Prop?,
    @SerializedName("to")
    var to: String?
)