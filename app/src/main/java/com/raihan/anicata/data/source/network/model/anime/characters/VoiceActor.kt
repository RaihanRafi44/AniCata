package com.raihan.anicata.data.source.network.model.anime.characters


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VoiceActor(
    @SerializedName("language")
    var language: String?,
    @SerializedName("person")
    var person: Person?
)