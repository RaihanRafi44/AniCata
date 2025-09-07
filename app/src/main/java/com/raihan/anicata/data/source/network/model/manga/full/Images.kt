package com.raihan.anicata.data.source.network.model.manga.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Images(
    @SerializedName("jpg")
    var jpg: Jpg?,
    @SerializedName("webp")
    var webp: Webp?
)