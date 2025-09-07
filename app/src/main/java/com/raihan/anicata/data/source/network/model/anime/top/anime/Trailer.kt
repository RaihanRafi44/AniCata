package com.raihan.anicata.data.source.network.model.anime.top.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Trailer(
    @SerializedName("embed_url")
    var embedUrl: String?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("youtube_id")
    var youtubeId: String?
)