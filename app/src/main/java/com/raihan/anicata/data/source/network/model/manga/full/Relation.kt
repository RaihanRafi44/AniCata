package com.raihan.anicata.data.source.network.model.manga.full


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Relation(
    @SerializedName("entry")
    var entry: List<Entry>?,
    @SerializedName("relation")
    var relation: String?
)