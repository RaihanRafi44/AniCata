package com.raihan.anicata.ui.seasonalanime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimeFilterGroup(
    // Baris 1: Tahun
    selectedYear: String,
    onYearChange: (String) -> Unit,
    yearOptions: List<String>,

    // Baris 1: Season
    selectedSeason: String,
    onSeasonChange: (String) -> Unit,
    seasonOptions: List<String>,

    // Baris 2: Tipe
    selectedType: String,
    onTypeChange: (String) -> Unit,
    typeOptions: List<String>,

    // Baris 2: Status
    selectedStatus: String,
    onStatusChange: (String) -> Unit,
    statusOptions: List<String>,

    // Event handler
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor = Color(0xFF00BFFF)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Seasonal Anime",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Baris pertama filter (Tahun & Season)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Year", // Label diubah
                    options = yearOptions,
                    selectedOption = selectedYear,
                    onOptionSelected = onYearChange,
                    modifier = Modifier.weight(1f)
                )
                FilterDropdownMenu(
                    label = "Season", // Label diubah
                    options = seasonOptions,
                    selectedOption = selectedSeason,
                    onOptionSelected = onSeasonChange,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Baris kedua filter (Tipe & Status)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Type", // Label diubah
                    options = typeOptions,
                    selectedOption = selectedType,
                    onOptionSelected = onTypeChange,
                    modifier = Modifier.weight(1f)
                )
                FilterDropdownMenu(
                    label = "Status",
                    options = statusOptions,
                    selectedOption = selectedStatus,
                    onOptionSelected = onStatusChange,
                    modifier = Modifier.weight(1f)
                )
                // Dropdown Genre dihapus
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Update Filter
            Button(
                onClick = onUpdateClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Update Filter",
                    color = Color.White,
                )
            }

            Divider(
                color = dividerColor,
                thickness = 2.dp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

/**
 * Composable dropdown menu yang stateless (tidak berubah)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val verySmallTextStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        BasicTextField(
            value = "$label : $selectedOption",
            onValueChange = {},
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            textStyle = verySmallTextStyle.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Black.copy(
                                alpha = 0.6f
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption, style = verySmallTextStyle) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                )
            }
        }
    }
}

/**
 * Preview diperbarui untuk mencerminkan layout 4 filter yang baru
 */
@Preview(showBackground = true)
@Composable
fun AnimeFilterGroupFinalPreview() {
    AnimeFilterGroup(
        // Baris 1
        selectedYear = "2025",
        onYearChange = {},
        yearOptions = listOf("2025", "2024"),
        selectedSeason = "Fall",
        onSeasonChange = {},
        seasonOptions = listOf("Fall", "Summer"),

        // Baris 2
        selectedType = "tv",
        onTypeChange = {},
        typeOptions = listOf("tv", "movie", "ova"),
        selectedStatus = "New",
        onStatusChange = {},
        statusOptions = listOf("New", "Continuing"),

        // Genre dihapus

        onUpdateClick = {}
    )
}
