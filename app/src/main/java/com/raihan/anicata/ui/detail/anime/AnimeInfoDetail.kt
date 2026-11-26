package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.anime.full.AiredAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeFrom
import com.raihan.anicata.data.model.anime.full.DateInfoAnimeTo

private fun formatAiredDate(aired: AiredAnime?): String? {
    val prop = aired?.prop ?: return null
    val from = prop.from
    val to = prop.to

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    fun formatDatePart(date: Any?): String {
        val day: Int?
        val month: Int?
        val year: Int?

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

        if (year == null || year == 0) return ""

        val yearStr = year.toString()
        val monthStr = if (month != null && month in 1..12) monthNames[month - 1] + " " else ""
        val dayStr = if (day != null && day != 0) day.toString() + ", " else ""

        return "$monthStr$dayStr$yearStr"
    }

    val fromStr = formatDatePart(from)
    val toStr = formatDatePart(to)

    if (fromStr.isBlank()) return prop.string.takeIf { it.isNotBlank() }

    return if (toStr.isNotBlank() && fromStr != toStr) {
        "$fromStr - $toStr"
    } else {
        fromStr
    }
}

@Composable
fun AnimeInfoDetail(
    animeData: AnimeData,
    modifier: Modifier = Modifier
) {
    val details = mutableMapOf<String, List<String>>()

    fun addDetail(label: String, data: List<String>?) {
        if (!data.isNullOrEmpty()) {
            details[label] = data
        } else {
            details[label] = listOf("?")
        }
    }

    fun addDetail(label: String, data: String?) {
        val displayValue = data?.trim().takeIf { !it.isNullOrBlank() } ?: "?"
        details[label] = listOf(displayValue)
    }

    addDetail("Genres", animeData.genres.map { it.name })
    if (animeData.themes.isNotEmpty()) {
        addDetail("Themes", animeData.themes.map { it.name })
    }
    if (animeData.demographics.isNotEmpty()) {
        addDetail("Demographics", animeData.demographics.map { it.name })
    }
    addDetail("Status", animeData.status)

    val formattedAiredDate = formatAiredDate(animeData.aired)
    addDetail("Aired", formattedAiredDate)

    if (animeData.studios.isNotEmpty()) {
        addDetail("Studios", animeData.studios.map { it.name })
    }
    if (animeData.producers.isNotEmpty()) {
        addDetail("Producers", animeData.producers.map { it.name })
    }
    if (animeData.licensors.isNotEmpty()) {
        addDetail("Licensors", animeData.licensors.map { it.name })
    }
    addDetail("Source", animeData.source)
    addDetail("Duration", animeData.duration)
    addDetail("Rating", animeData.rating)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            details.forEach { (label, values) ->
                DetailRow(label = label, values = values)
            }
        }
    }
}
