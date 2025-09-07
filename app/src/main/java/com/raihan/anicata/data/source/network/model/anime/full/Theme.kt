package com.raihan.anicata.data.source.network.model.anime.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Theme(
    @SerializedName("endings")
    var endings: List<String?>?,
    @SerializedName("openings")
    var openings: List<String?>?
)