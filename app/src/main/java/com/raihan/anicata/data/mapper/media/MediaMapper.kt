package com.raihan.anicata.data.mapper.media

import com.raihan.anicata.data.model.anime.genres.GenreAnime
import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.data.model.manga.genres.GenreManga
import com.raihan.anicata.data.model.manga.search.SearchManga
import com.raihan.anicata.data.model.media.MediaGenre
import com.raihan.anicata.data.model.media.MediaItem

// Helper function untuk memformat tanggal (bisa menangani Anime & Manga)
private fun formatMediaDate(
    fromYear: Int?, fromMonth: Int?,
    toYear: Int?, toMonth: Int?,
    string: String?
): String {
    val isFromDateValid = fromYear != null && fromMonth != null && fromMonth in 1..12
    if (!isFromDateValid) {
        return string ?: "N/A"
    }

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Des")
    val fromMonthName = monthNames[fromMonth - 1]
    val fromString = "$fromMonthName $fromYear"

    val isToDateValid = toYear != null && toMonth != null && toMonth in 1..12
    val toString = if (isToDateValid) {
        val toMonthName = monthNames[toMonth - 1]
        " - $toMonthName $toYear"
    } else {
        ""
    }
    return fromString + toString
}

// Mapper dari SearchAnime (Data Layer) -> MediaItem (UI Layer)
fun SearchAnime.toMediaItem(): MediaItem {
    return MediaItem(
        id = this.id,
        title = this.title,
        imageUrl = this.images.jpg.largeImageUrl ?: "",
        score = this.score,
        members = this.members,
        itemType = this.type ?: "N/A",
        itemDetails = "${this.episodes ?: '?'} eps",
        airedOrPublished = formatMediaDate(
            this.aired?.prop?.from?.year, this.aired?.prop?.from?.month,
            this.aired?.prop?.to?.year, this.aired?.prop?.to?.month,
            this.aired?.prop?.string
        )
    )
}

// Mapper dari SearchManga (Data Layer) -> MediaItem (UI Layer)
fun SearchManga.toMediaItem(): MediaItem {
    return MediaItem(
        id = this.id,
        title = this.title,
        imageUrl = this.images.jpg.largeImageUrl ?: "",
        score = this.score,
        members = this.members,
        itemType = this.type ?: "N/A",
        itemDetails = "${this.chapters ?: '?'} ch.", // Perbedaan di sini
        airedOrPublished = formatMediaDate(
            this.published?.prop?.from?.year,
            this.published?.prop?.from?.month,
            this.published?.prop?.to?.year,
            this.published?.prop?.to?.month,
            this.published?.prop?.string
        )
    )
}

// Mapper dari GenreAnime (Data) -> MediaGenre (UI)
fun GenreAnime.toMediaGenre(): MediaGenre {
    return MediaGenre(id = this.id, name = this.name)
}

// Mapper dari GenreManga (Data) -> MediaGenre (UI)
fun GenreManga.toMediaGenre(): MediaGenre {
    return MediaGenre(id = this.id, name = this.name)
}