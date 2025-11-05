package com.raihan.anicata.data.source.network.model.manga.staff


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StaffMangaResponse(
    @SerializedName("data")
    var data: List<StaffMangaData>?
)