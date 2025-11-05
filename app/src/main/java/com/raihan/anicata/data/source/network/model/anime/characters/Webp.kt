package com.raihan.anicata.data.source.network.model.anime.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Webp(
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("small_image_url")
    var smallImageUrl: String?
)