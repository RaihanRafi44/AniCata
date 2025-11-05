package com.raihan.anicata.data.source.network.model.manga.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Jpg(
    @SerializedName("image_url")
    var imageUrl: String?
)