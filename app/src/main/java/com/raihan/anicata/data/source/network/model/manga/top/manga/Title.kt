package com.raihan.anicata.data.source.network.model.manga.top.manga


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Title(
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?
)