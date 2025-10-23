package com.raihan.anicata.ui.top.anime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.top.StyledDropdownFromFilter

@Composable
fun FilterTopAnime(
    selectedFilterApiValue: String,
    onFilterSelected: (String) -> Unit
){
    val filterOptions = remember {
        listOf(
            "All Anime" to "",
            "Top Airing" to "airing",
            "Top Upcoming" to "upcoming",
            "Most Popular" to "bypopularity"
        )
    }

    val displayOptions = remember(filterOptions) {
        filterOptions.map { it.first }
    }

    val selectedDisplayName = filterOptions.find { it.second == selectedFilterApiValue}?.first ?: "All Anime"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top Anime",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(36.dp))

        StyledDropdownFromFilter(
            options = displayOptions,
            selectedOption = selectedDisplayName,
            onOptionSelected = { selectedDisplayName ->
                val newApiValue = filterOptions.find { it.first == selectedDisplayName }?.second ?: ""
                onFilterSelected(newApiValue)
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Preview(showBackground = true)
@Composable
fun FilterTopAnimePreview() {
    // Beri nilai default untuk preview
    FilterTopAnime(selectedFilterApiValue = "airing", onFilterSelected = {})
}
