package com.raihan.anicata.data.model.media

// Model ini akan digunakan oleh UI, bisa berisi data Anime atau Manga
data class MediaItem(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Double?,
    val members: Int?,
    val itemType: String, // "TV", "Manga", "Movie"
    val itemDetails: String, // "12 eps" atau "120 ch."
    val airedOrPublished: String // Tgl rilis/terbit yang sudah diformat
)