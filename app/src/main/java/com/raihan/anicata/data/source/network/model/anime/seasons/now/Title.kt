package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Title(
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?
)
