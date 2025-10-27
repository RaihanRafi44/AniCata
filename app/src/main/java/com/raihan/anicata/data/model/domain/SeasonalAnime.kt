package com.raihan.anicata.data.model.domain

// Model data umum yang akan digunakan oleh UI
data class SeasonalAnime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val aired: AiredInfo?, // Model Aired yang umum
    val members: Int?,
    val episodes: Int?,
    val score: Double?,
    val type: String?
)

// Model AiredInfo umum
data class AiredInfo(
    val prop: PropInfo?
)

// Model PropInfo umum
data class PropInfo(
    val from: DateInfo?,
    val to: DateInfo?,
    val string: String
)

// Model DateInfo umum
data class DateInfo(
    val day: Int?,
    val month: Int?,
    val year: Int?
)
