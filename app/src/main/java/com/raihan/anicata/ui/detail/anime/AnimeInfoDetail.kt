package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.anime.full.AiredAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeFrom
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeTo
import com.raihan.anicata.ui.theme.DarkGreenBackground

private fun formatAiredDate(aired: AiredAnime?): String? {
    val prop = aired?.prop ?: return null
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
            is DateInfoAnimeFrom -> {
                day = date.day
                month = date.month
                year = date.year
            }
            is DateInfoAnimeTo -> {
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

    if (fromStr.isBlank()) return null // Jika tanggal 'from' tidak valid, jangan tampilkan

    return if (toStr.isNotBlank() && fromStr != toStr) {
        "$fromStr - $toStr"
    } else {
        fromStr // Hanya tampilkan 'from' jika 'to' kosong atau sama
    }
}

@Composable
fun AnimeInfoDetail(
    animeData: AnimeData
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
            details[label] = listOf(data!!.trim())
        }*/
        // Jika string null atau kosong, tampilkan "?"
        val displayValue = data?.trim().takeIf { !it.isNullOrBlank() } ?: "?"
        details[label] = listOf(displayValue)
    }

    addDetail("Genres", animeData.genres.map { it.name })
    addDetail("Themes", animeData.themes.map { it.name })
    addDetail("Demographics", animeData.demographics.map { it.name })
    //addDetail("Episodes", animeData.episodes?.toString())
    addDetail("Episodes", "${animeData.episodes ?: '?'}")
    addDetail("Status", animeData.status)
    //addDetail("Aired", animeData.aired?.toString())

    val formattedAiredDate = formatAiredDate(animeData.aired)
    addDetail("Aired", formattedAiredDate)
    addDetail("Producers", animeData.producers.map { it.name })
    addDetail("Studios", animeData.studios.map { it.name })
    addDetail("Licensors", animeData.licensors.map { it.name })
    addDetail("Source", animeData.source)
    addDetail("Duration", animeData.duration)
    addDetail("Rating", animeData.rating)

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
private fun AnimeDetailsScreenPreview() {
    //AnimeInfoDetail()
}