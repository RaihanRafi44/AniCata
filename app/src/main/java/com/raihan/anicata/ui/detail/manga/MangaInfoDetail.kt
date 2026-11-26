package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.manga.full.MangaFullDateFrom
import com.raihan.anicata.data.model.manga.full.MangaFullDateTo
import com.raihan.anicata.data.model.manga.full.MangaFullPublished
import com.raihan.anicata.ui.detail.anime.DetailRow

private fun formatAiredDate(published: MangaFullPublished?): String? {
    val prop = published?.prop ?: return null
    val from = prop.from
    val to = prop.to

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    fun formatDatePart(date: Any?): String {
        val day: Int?
        val month: Int?
        val year: Int?

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

        if (year == null || year == 0) return ""

        val yearStr = year.toString()
        val monthStr = if (month != null && month in 1..12) monthNames[month - 1] + " " else ""
        val dayStr = if (day != null && day != 0) day.toString() + ", " else ""

        return "$monthStr$dayStr$yearStr"
    }

    val fromStr = formatDatePart(from)
    val toStr = formatDatePart(to)

    if (fromStr.isBlank()) return prop.string?.takeIf { it.isNotBlank() }

    return if (toStr.isNotBlank() && fromStr != toStr) {
        "$fromStr - $toStr"
    } else {
        fromStr
    }
}

@Composable
fun MangaInfoDetail(
    mangaData: MangaDetailFull,
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

    addDetail("Genres", mangaData.genres.map { it.name })
    if (mangaData.themes.isNotEmpty()) {
        addDetail("Themes", mangaData.themes.map { it.name })
    }
    if (mangaData.demographics.isNotEmpty()) {
        addDetail("Demographics", mangaData.demographics.map { it.name })
    }
    addDetail("Chapters", "${mangaData.chapters ?: '?'}")
    addDetail("Volumes", "${mangaData.volumes ?: '?'}")
    addDetail("Status", mangaData.status)

    val formattedAiredDate = formatAiredDate(mangaData.published)
    addDetail("Published", formattedAiredDate)

    if (mangaData.authors.isNotEmpty()) {
        addDetail("Authors", mangaData.authors.map { "${it.name} (${it.type})" })
    }
    if (mangaData.serializations.isNotEmpty()) {
        addDetail("Serialization", mangaData.serializations.map { it.name })
    }

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
