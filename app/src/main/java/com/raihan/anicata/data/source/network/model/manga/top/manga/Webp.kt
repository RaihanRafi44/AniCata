package com.raihan.anicata.data.source.network.model.manga.top.manga


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Webp(
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("large_image_url")
    var largeImageUrl: String?,
    @SerializedName("small_image_url")
    var smallImageUrl: String?
)