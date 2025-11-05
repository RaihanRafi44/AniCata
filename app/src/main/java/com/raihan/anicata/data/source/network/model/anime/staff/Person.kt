package com.raihan.anicata.data.source.network.model.anime.staff


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Person(
    @SerializedName("images")
    var images: Images?,
    @SerializedName("mal_id")
    var malId: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("url")
    var url: String?
)