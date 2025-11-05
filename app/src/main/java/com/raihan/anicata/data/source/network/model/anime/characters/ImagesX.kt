package com.raihan.anicata.data.source.network.model.anime.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ImagesX(
    @SerializedName("jpg")
    var jpg: Jpg?
)