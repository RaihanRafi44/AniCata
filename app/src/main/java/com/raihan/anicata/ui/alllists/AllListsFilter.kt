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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AllListsFilterGroup(
    // --- Parameter Kategori ---
    selectedCategory: String,
    onCategoryChanged: (String) -> Unit,
    categoryOptions: List<String>,

    // --- Parameter Sort ---
    selectedSort: String,
    onSortChanged: (String) -> Unit,
    sortOptions: List<String>,

    // --- Parameter Tipe ---
    selectedType: String,
    onTypeChanged: (String) -> Unit,
    typeOptions: List<String>,

    // --- Parameter Genre, Tema, Target ---
    onUpdateFilterClicked: () -> Unit,
    selectedGenre: String,
    onGenreChanged: (String) -> Unit,
    genreOptions: List<String>,
    selectedTheme: String,
    onThemeChanged: (String) -> Unit,
    themeOptions: List<String>,
    selectedTarget: String,
    onTargetChanged: (String) -> Unit,
    targetOptions: List<String>
) {
    val buttonColor = Color(0xFF00BFFF)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "All Lists",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Baris pertama filter: Category & Genre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Category",
                    options = categoryOptions, // Diambil dari ViewModel
                    selectedValue = selectedCategory, // Diambil dari ViewModel
                    onSelectionChanged = onCategoryChanged, // Panggil lambda ViewModel
                    modifier = Modifier.weight(1f)
                )

                FilterDropdownMenu(
                    label = "Genre",
                    options = genreOptions,
                    selectedValue = selectedGenre,
                    onSelectionChanged = onGenreChanged,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 2: Sort by & Type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Sort by",
                    options = sortOptions, // Diambil dari ViewModel
                    selectedValue = selectedSort,
                    onSelectionChanged = onSortChanged,
                    modifier = Modifier.weight(1f)
                )

                FilterDropdownMenu(
                    label = "Type",
                    options = typeOptions, // Diambil dari ViewModel (sudah dinamis)
                    selectedValue = selectedType,
                    onSelectionChanged = onTypeChanged,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 3: Target & Themes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Target",
                    options = targetOptions,
                    selectedValue = selectedTarget,
                    onSelectionChanged = onTargetChanged,
                    modifier = Modifier.weight(1f)
                )

                FilterDropdownMenu(
                    label = "Themes",
                    options = themeOptions,
                    selectedValue = selectedTheme,
                    onSelectionChanged = onThemeChanged,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Update Filter
            Button(
                onClick = onUpdateFilterClicked,
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
 * Composable dropdown menu internal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterDropdownMenu( // Jadikan private jika hanya dipakai di file ini
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
    // Perbaiki cek loading: kosong ATAU hanya berisi "All" (opsi default)
    val isLoadingOptions = options.isEmpty() || (options.size == 1 && options.firstOrNull() == "All")
    val displayValue = if (isLoadingOptions) "$label : Loading..." else "$label : $selectedValue"
    val isEnabled = !isLoadingOptions // Dropdown disabled saat loading

    ExposedDropdownMenuBox(
        expanded = expanded && isEnabled, // Jangan expand jika disabled
        onExpandedChange = { if (isEnabled) expanded = !expanded }, // Jangan ubah jika disabled
        modifier = modifier
    ) {
        BasicTextField(
            value = displayValue,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            textStyle = verySmallTextStyle.copy(
                // Sedikit abu-abu jika disabled
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else Color.Gray
            ),
            interactionSource = interactionSource,
            enabled = isEnabled, // TextField juga disabled
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (isEnabled && isFocused) MaterialTheme.colorScheme.primary
                            else if (isEnabled) Color.Black.copy(alpha = 0.6f)
                            else Color.Gray.copy(alpha = 0.4f), // Border abu-abu jika disabled
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded && isEnabled,
                        // Ikon abu-abu jika disabled
                        modifier = Modifier.then(if (!isEnabled) Modifier.graphicsLayer(alpha = 0.5f) else Modifier)
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = expanded && isEnabled,
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
