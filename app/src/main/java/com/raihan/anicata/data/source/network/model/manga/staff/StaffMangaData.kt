package com.raihan.anicata.data.source.network.model.manga.staff


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StaffMangaData(
    @SerializedName("person")
    var person: Person?,
    @SerializedName("positions")
    var positions: List<String>?
)