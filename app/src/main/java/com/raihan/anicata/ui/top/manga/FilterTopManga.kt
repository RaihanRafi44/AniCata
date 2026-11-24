package com.raihan.anicata.ui.top.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.top.TopFilterChipsRow

@Composable
fun FilterTopManga(
    selectedFilterApiValue: String,
    onFilterSelected: (String) -> Unit
) {
    val themeColor = Color(0xFF006A60)

    val filterOptions = remember {
        listOf(
            "All Manga" to "",
            "Publishing" to "publishing",
            "Most Popular" to "bypopularity"
        )
    }

    val displayOptions = remember(filterOptions) {
        filterOptions.map { it.first }
    }

    val selectedDisplayName = filterOptions.find { it.second == selectedFilterApiValue }?.first ?: "All Manga"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Header beraksen konsisten dengan Seasonal Explorer & Home Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp, 24.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(themeColor)
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "📚",
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Top Manga Leaderboard",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF191C1E),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = "Discover top-rated manga, manhwa, and novels",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 11.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        TopFilterChipsRow(
            options = displayOptions,
            selectedOption = selectedDisplayName,
            onOptionSelected = { selectedName ->
                val newApiValue = filterOptions.find { it.first == selectedName }?.second ?: ""
                onFilterSelected(newApiValue)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterTopMangaPreview() {
    FilterTopManga(selectedFilterApiValue = "publishing", onFilterSelected = {})
}
