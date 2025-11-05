package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.anime.full.AiredAnime
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.manga.full.MangaFullDateFrom
import com.raihan.anicata.data.model.manga.full.MangaFullDateTo
import com.raihan.anicata.data.model.manga.full.MangaFullPublished
import com.raihan.anicata.data.model.manga.full.MangaFullPublishedProp
import com.raihan.anicata.ui.detail.anime.DetailRow

private fun formatAiredDate(published: MangaFullPublished?): String? {
    val prop = published?.prop ?: return null
    val from = prop.from
    val to = prop.to

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    // Fungsi kecil untuk mengubah satu bagian tanggal (From atau To)
    fun formatDatePart(date: Any?): String {
        val day: Int?
        val month: Int?
        val year: Int?

        // Cek tipe data dan ambil nilainya
        when (date) {
            is MangaFullDateFrom -> {
                day = date.day
                month = date.month
                year = date.year
            }
            is MangaFullDateTo -> {
                day = date.day
                month = date.month
                year = date.year
            }
            else -> return ""
        }

        if (year == null || year == 0) return "" // Tahun wajib ada

        val yearStr = year.toString()
        // Cek bulan valid (1-12)
        val monthStr = if (month != null && month in 1..12) monthNames[month - 1] + " " else ""
        val dayStr = if (day != null && day != 0) day.toString() + ", " else ""

        return "$monthStr$dayStr$yearStr"
    }

    val fromStr = formatDatePart(from)
    val toStr = formatDatePart(to)

    //if (fromStr.isBlank()) return null // Jika tanggal 'from' tidak valid, jangan tampilkan
    if (fromStr.isBlank()) return prop.string // Jika tanggal 'from' tidak valid, jangan tampilkan

    return if (toStr.isNotBlank() && fromStr != toStr) {
        "$fromStr - $toStr"
    } else {
        fromStr // Hanya tampilkan 'from' jika 'to' kosong atau sama
    }
}

@Composable
fun MangaInfoDetail(
    mangaData: MangaDetailFull
) {
    // Buat map secara dinamis dari data API
    val details = mutableMapOf<String, List<String>>()

    fun addDetail(label: String, data: List<String>?) {
        if (!data.isNullOrEmpty()) {
            details[label] = data
        } else {
            // Jika list null atau kosong, tampilkan "?"
            details[label] = listOf("?")
        }
    }

    fun addDetail(label: String, data: String?) {
        /*if (!data?.trim().isNullOrBlank()) {
            details[label] = listOf(data.trim())
        }*/
        // Jika string null atau kosong, tampilkan "?"
        val displayValue = data?.trim().takeIf { !it.isNullOrBlank() } ?: "?"
        details[label] = listOf(displayValue)
    }

    addDetail("Genres", mangaData.genres.map { it.name })
    addDetail("Themes", mangaData.themes.map { it.name })
    addDetail("Demographics", mangaData.demographics.map { it.name })
    /*addDetail("Chapters", mangaData.chapters?.toString())
    addDetail("Volume", mangaData.volumes?.toString())*/
    // Terapkan logika "? ch." dan "? vol."
    addDetail("Chapters", "${mangaData.chapters ?: '?'}") //
    addDetail("Volumes", "${mangaData.volumes ?: '?'}") //
    addDetail("Status", mangaData.status)
    //addDetail("Aired", animeData.aired?.toString())

    val formattedAiredDate = formatAiredDate(mangaData.published)
    addDetail("Published", formattedAiredDate)

    addDetail("Authors", mangaData.authors.map { "${it.name} (${it.type}}" })
    addDetail("Serialization", mangaData.serializations.map { it.name })
    /*addDetail("Licensors", mangaData.licensors.map { it.name })
    addDetail("Source", mangaData.source)
    addDetail("Duration", mangaData.duration)
    addDetail("Rating", mangaData.rating)*/

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            details.forEach { (label, values) ->
                DetailRow(label = label, values = values)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MangaDetailsScreenPreview() {
}