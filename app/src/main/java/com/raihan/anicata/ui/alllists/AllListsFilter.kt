package com.raihan.anicata.ui.alllists

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AllListsFilterGroup() {
    val buttonColor = Color(0xFF00BFFF)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    // State for the selected category, defaulting to "Anime"
    var selectedCategory by remember { mutableStateOf("Anime") }
    // Dummy states for other filters to make them functional
    var selectedGenre by remember { mutableStateOf("Action") }
    var selectedSort by remember { mutableStateOf("A - Z") }
    var selectedType by remember { mutableStateOf("TV") }
    var selectedTarget by remember { mutableStateOf("Shounen") }
    var selectedTheme by remember { mutableStateOf("Military") }


    // Column luar untuk padding dari tepi layar, persis seperti kode asli.
    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "All Lists", // Judul diubah
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Baris pertama filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Category",
                    options = listOf("Anime", "Manga", "Novel"),
                    selectedValue = selectedCategory,
                    onSelectionChanged = { selectedCategory = it },
                    modifier = Modifier.weight(1f)
                )
                FilterDropdownMenu(
                    label = "Genre",
                    options = listOf("Action", "Comedy", "Fantasy", "Sci-Fi", "Drama"),
                    selectedValue = selectedGenre,
                    onSelectionChanged = { selectedGenre = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Baris kedua filter
            // --- CONDITIONAL FILTERS ---
            // Check the value of selectedCategory to decide which filters to show
            if (selectedCategory == "Anime") {
                // Row 2 for Anime: Sort by & Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterDropdownMenu(
                        label = "Sort by",
                        options = listOf("A - Z", "Popularity", "Score"),
                        selectedValue = selectedSort,
                        onSelectionChanged = { selectedSort = it },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdownMenu(
                        label = "Type",
                        options = listOf("TV", "Movie", "OVA"),
                        selectedValue = selectedType,
                        onSelectionChanged = { selectedType = it },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Row 3 for Anime: Target & Themes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterDropdownMenu(
                        label = "Target",
                        options = listOf("Shounen", "Shoujo", "Seinen", "Josei"),
                        selectedValue = selectedTarget,
                        onSelectionChanged = { selectedTarget = it },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdownMenu(
                        label = "Themes",
                        options = listOf("Military", "Mecha", "Isekai", "School"),
                        selectedValue = selectedTheme,
                        onSelectionChanged = { selectedTheme = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            } else { // For Manga and Novel
                // Row 2 for Manga/Novel: Sort by & Themes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilterDropdownMenu(
                        label = "Sort by",
                        options = listOf("A - Z", "Popularity", "Score"),
                        selectedValue = selectedSort,
                        onSelectionChanged = { selectedSort = it },
                        modifier = Modifier.weight(1f)
                    )
                    FilterDropdownMenu(
                        label = "Themes",
                        options = listOf("Military", "Mecha", "Isekai", "School"),
                        selectedValue = selectedTheme,
                        onSelectionChanged = { selectedTheme = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Update Filter
            Button(
                onClick = { /* TODO: Logika update filter */ },
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

            // Divider dari kode asli dihilangkan karena tidak ada di gambar
        }
    }
}

/**
 * Composable dropdown menu yang telah diperbaiki.
 * Kode ini SAMA PERSIS dengan yang Anda kirimkan, tidak ada perubahan gaya.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdownMenu(
    label: String,
    options: List<String>,
    selectedValue: String,
    onSelectionChanged: (String) -> Unit,
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
            value = "$label : $selectedValue",
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            textStyle = verySmallTextStyle.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.6f),
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
                        onSelectionChanged(selectionOption)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllListsFilterGroupPreview() {
    // Background di preview ini hanya untuk simulasi,
    // tidak akan mempengaruhi komponen aslinya.
    // Di sini saya gunakan warna hijau mint dari gambar agar mudah dilihat.
    Box(modifier = Modifier.background(Color(0xFFE6F5F3))) {
        AllListsFilterGroup()
    }
}
