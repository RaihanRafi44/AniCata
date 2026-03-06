package com.raihan.anicata.data.model.storage

data class UserBookmarkManga(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val score: Double = 0.0,
    val members: Int = 0,
    val type: String = "",
    val chapters: Int? = 0,
    val publishedFrom: String? = null,
    val publishedTo: String? = null,
    val savedAt: Long = System.currentTimeMillis()
)
