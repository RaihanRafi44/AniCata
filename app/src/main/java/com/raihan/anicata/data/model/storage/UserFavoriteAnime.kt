package com.raihan.anicata.data.model.storage

data class UserFavoriteAnime(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val score: Double = 0.0,
    val members: Int = 0,
    val type: String = "",
    val episodes: Int? = 0,
    val airedFrom: String? = null,
    val airedTo: String? = null,
    val savedAt: Long = System.currentTimeMillis()
)
