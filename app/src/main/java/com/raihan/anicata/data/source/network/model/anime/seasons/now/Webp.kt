package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Webp(
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("large_image_url")
    var largeImageUrl: String?,
    @SerializedName("small_image_url")
    var smallImageUrl: String?
)
