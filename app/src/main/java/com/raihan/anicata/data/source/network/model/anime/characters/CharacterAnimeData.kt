package com.raihan.anicata.data.source.network.model.anime.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CharacterAnimeData(
    @SerializedName("character")
    var character: Characters?,
    @SerializedName("favorites")
    var favorites: Int?,
    @SerializedName("role")
    var role: String?,
    @SerializedName("voice_actors")
    var voiceActors: List<VoiceActor>?
)