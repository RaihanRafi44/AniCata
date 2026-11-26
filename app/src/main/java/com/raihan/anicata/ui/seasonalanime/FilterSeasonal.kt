package com.raihan.anicata.ui.seasonalanime

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimeFilterGroup(
    selectedYear: String,
    onYearChange: (String) -> Unit,
    yearOptions: List<String>,

    selectedSeason: String,
    onSeasonChange: (String) -> Unit,
    seasonOptions: List<String>,

    selectedType: String,
    onTypeChange: (String) -> Unit,
    typeOptions: List<String>,

    selectedStatus: String,
    onStatusChange: (String) -> Unit,
    statusOptions: List<String>,

    selectedSort: String,
    onSortChange: (String) -> Unit,
    sortOptions: List<String>,

    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier,
    itemCount: Int? = null
) {
    val themeColor = Color(0xFF006A60)
    val defaultSeasons = listOf("Winter", "Spring", "Summer", "Fall", "Upcoming")
    val displaySeasons = if (seasonOptions.isEmpty()) {
        defaultSeasons
    } else if (seasonOptions.none { it.equals("Upcoming", ignoreCase = true) }) {
        seasonOptions + "Upcoming"
    } else {
        seasonOptions
    }

    val isUpcoming = selectedSeason.equals("upcoming", ignoreCase = true)
    val currentSeasonFormatted = selectedSeason.capitalizeFirstLetter()
    val dynamicTitle = if (isUpcoming) {
        "Upcoming Anime Lineup"
    } else if (currentSeasonFormatted.isNotBlank() && selectedYear.isNotBlank()) {
        "$currentSeasonFormatted $selectedYear Anime"
    } else {
        "Seasonal Anime Explorer"
    }

    val seasonIcon = if (isUpcoming) {
        "🔮"
    } else when (selectedSeason.lowercase()) {
        "winter" -> "❄️"
        "spring" -> "🌸"
        "summer" -> "☀️"
        "fall" -> "🍂"
        else -> "📺"
    }

    val dynamicSubtitle = if (isUpcoming) {
        "Exploring all upcoming & TBA announced releases"
    } else {
        "Exploring $currentSeasonFormatted $selectedYear releases & lineup"
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Dynamic Contextual Header Row
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
                            text = seasonIcon,
                            fontSize = 18.sp
                        )
                        Text(
                            text = dynamicTitle,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF191C1E),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = dynamicSubtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 11.5.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Optional Item Counter Pill
            itemCount?.let { count ->
                Surface(
                    shape = CircleShape,
                    color = themeColor.copy(alpha = 0.1f),
                    contentColor = themeColor
                ) {
                    Text(
                        text = "$count Shows",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Horizontal Segmented Season Switcher
        Text(
            text = "SEASON",
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold,
            color = themeColor,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(displaySeasons) { season ->
                val isSelected = season.equals(selectedSeason, ignoreCase = true)
                val icon = if (season.equals("upcoming", ignoreCase = true)) {
                    "🔮"
                } else when (season.lowercase()) {
                    "winter" -> "❄️"
                    "spring" -> "🌸"
                    "summer" -> "☀️"
                    "fall" -> "🍂"
                    else -> "📺"
                }

                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) themeColor else Color(0xFFE0F2F1),
                    label = "seasonBg"
                )
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color(0xFF004D40),
                    label = "seasonText"
                )

                Surface(
                    onClick = { onSeasonChange(season) },
                    shape = CircleShape,
                    color = backgroundColor,
                    contentColor = textColor,
                    shadowElevation = if (isSelected) 2.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = icon, fontSize = 12.sp)
                        Text(
                            text = season.capitalizeFirstLetter(),
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Baris 1 Dropdowns: Year & Type
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterDropdownMenu(
                label = "Year",
                options = if (isUpcoming) listOf("TBA") else yearOptions,
                selectedOption = if (isUpcoming) "TBA" else selectedYear,
                onOptionSelected = onYearChange,
                enabled = !isUpcoming,
                modifier = Modifier.weight(1f)
            )
            FilterDropdownMenu(
                label = "Type",
                options = typeOptions,
                selectedOption = selectedType,
                onOptionSelected = onTypeChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Baris 2 Dropdowns: Status & Sort By
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FilterDropdownMenu(
                label = "Status",
                options = statusOptions,
                selectedOption = selectedStatus,
                onOptionSelected = onStatusChange,
                modifier = Modifier.weight(1f)
            )
            FilterDropdownMenu(
                label = "Sort By",
                options = sortOptions,
                selectedOption = selectedSort,
                onOptionSelected = onSortChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Baris 3: Tombol Apply Filters
        Button(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = themeColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Apply Filter",
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Apply Filters",
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            color = Color.LightGray.copy(alpha = 0.4f),
            thickness = 1.dp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val themeColor = Color(0xFF006A60)

    ExposedDropdownMenuBox(
        expanded = if (enabled) expanded else false,
        onExpandedChange = { if (enabled) expanded = !expanded },
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = if (enabled) Color(0xFFF5F5F5) else Color(0xFFEEEEEE),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = if (expanded && enabled) themeColor else Color(0xFFE0E0E0)
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = label.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = selectedOption.ifBlank { "All" },
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (enabled) Color(0xFF191C1E) else Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (enabled) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            }
        }

        if (enabled) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                fontSize = 12.sp,
                                fontWeight = if (option == selectedOption) FontWeight.Bold else FontWeight.Normal,
                                color = if (option == selectedOption) themeColor else Color.Unspecified
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

private fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

@Preview(showBackground = true)
@Composable
fun AnimeFilterGroupFinalPreview() {
    AnimeFilterGroup(
        selectedYear = "2025",
        onYearChange = {},
        yearOptions = listOf("2025", "2024"),
        selectedSeason = "Fall",
        onSeasonChange = {},
        seasonOptions = listOf("Winter", "Spring", "Summer", "Fall", "Upcoming"),
        selectedType = "TV",
        onTypeChange = {},
        typeOptions = listOf("TV", "Movie", "OVA"),
        selectedStatus = "New",
        onStatusChange = {},
        statusOptions = listOf("New", "Continuing"),
        selectedSort = "Score",
        onSortChange = {},
        sortOptions = listOf("Score", "Popularity", "Title (A-Z)", "Episodes"),
        itemCount = 142,
        onUpdateClick = {}
    )
}
