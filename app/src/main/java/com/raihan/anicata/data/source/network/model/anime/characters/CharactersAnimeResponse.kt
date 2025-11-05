package com.raihan.anicata.data.source.network.model.anime.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CharactersAnimeResponse(
    @SerializedName("data")
    var data: List<CharacterAnimeData>?
)