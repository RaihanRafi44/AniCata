package com.raihan.anicata.data.mapper.ui

import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.data.model.manga.search.SearchManga
import com.raihan.anicata.ui.search.AnimeInfo

/**
 * Mengubah SearchAnime (model data) menjadi AnimeInfo (model UI untuk live search)
 * berdasarkan file SearchAnime.kt yang Anda berikan.
 */
fun SearchAnime.toAnimeInfo(): AnimeInfo {
    // Menggabungkan tipe dan tahun, cth: "(TV, 2002)"
    val typeStr = this.type ?: "Unknown"
    val yearStr = this.year?.toString() ?: "????"
    val typeAndYear = "($typeStr, $yearStr)"

    // Menggabungkan genre dari List<SearchDataGenres>
    val genresString = this.genres.mapNotNull { it.name }.joinToString(", ")
        .ifEmpty { "No genres listed" }

    return AnimeInfo(
        mainTitle = this.title,
        typeAndYear = typeAndYear,
        genres = "Genres : $genresString"
    )
}

/**
 * Mengubah SearchManga (model data) menjadi AnimeInfo (model UI untuk live search)
 * berdasarkan file SearchManga.kt yang Anda berikan.
 */
fun SearchManga.toMangaInfo(): AnimeInfo {
    // Menggabungkan tipe dan tahun
    val typeStr = this.type ?: "Manga"
    // Mengambil tahun dari nested object 'published'
    val yearStr = this.published?.prop?.from?.year?.toString() ?: "????"
    val typeAndYear = "($typeStr, $yearStr)"

    // Menggabungkan genre dan memberi penanda [Manga]
    val genresString = this.genres.mapNotNull { it.name }.joinToString(", ")
        .ifEmpty { "No genres listed" }

    return AnimeInfo(
        mainTitle = this.title,
        typeAndYear = typeAndYear,
        // Tambahkan prefix [Manga] untuk membedakannya di UI
        genres = "Genres : $genresString"
    )
}