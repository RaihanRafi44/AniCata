package com.raihan.anicata.ui.detail

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
import com.raihan.anicata.ui.theme.DarkGreenBackground

@Composable
fun AnimeDetailsScreen() {
    val details = mapOf(
        "Genres" to listOf("Action", "Adventure", "Drama", "Fantasy", "Military", "Shounen"),
        "Themes" to listOf("Super Power"),
        "Demographics" to listOf("Shounen"),
        "Episodes" to listOf("64"),
        "Status" to listOf("Finished Airing"),
        "Aired" to listOf("5 Apr 2009 to 4 Jul 2010"),
        "Producers" to listOf("Aniplex", "Square Enix", "Techno Sound", "Mainichi Broadcasting System"),
        "Studios" to listOf("Bones"),
        "Licensors" to listOf("Funimation", "Aniplex of America"),
        "Source" to listOf("Manga"),
        "Duration" to listOf("24 min. per ep."),
        "Rating" to listOf("R - 17+ (violence & profanity)"),
    )

    Box(
        modifier = Modifier
            //.fillMaxSize()
            //.background(DarkGreenBackground)
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
    AnimeDetailsScreen()
}