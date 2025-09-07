package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Images(
    @SerializedName("jpg")
    var jpg: Jpg?,
    @SerializedName("webp")
    var webp: Webp?
)
