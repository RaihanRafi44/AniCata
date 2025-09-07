package com.raihan.anicata.data.source.network.model.anime.seasons.now

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Pagination(
    @SerializedName("current_page")
    var currentPage: Int?,
    @SerializedName("has_next_page")
    var hasNextPage: Boolean?,
    @SerializedName("items")
    var items: Items?,
    @SerializedName("last_visible_page")
    var lastVisiblePage: Int?
)

