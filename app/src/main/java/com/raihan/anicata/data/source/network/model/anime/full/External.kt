package com.raihan.anicata.data.source.network.model.anime.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class External(
    @SerializedName("name")
    var name: String?,
    @SerializedName("url")
    var url: String?
)