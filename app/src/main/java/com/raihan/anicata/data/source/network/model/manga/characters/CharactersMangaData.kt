package com.raihan.anicata.data.source.network.model.manga.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CharactersMangaData(
    @SerializedName("character")
    var character: Characters?,
    @SerializedName("favorites")
    var favorites: Int?,
    @SerializedName("role")
    var role: String?
)