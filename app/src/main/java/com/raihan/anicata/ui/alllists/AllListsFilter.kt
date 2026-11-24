package com.raihan.anicata.ui.alllists

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllListsFilterGroup(
    uiState: AllListsUiState,
    onCategoryChanged: (String) -> Unit,
    onSortChanged: (String) -> Unit,
    onTypeChanged: (String) -> Unit,
    onGenreChanged: (String) -> Unit,
    onThemeChanged: (String) -> Unit,
    onTargetChanged: (String) -> Unit,
    onUpdateFilterClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColor = Color(0xFF006A60)
    var showFilterBottomSheet by remember { mutableStateOf(false) }

    val activeFilterCount = remember(uiState) {
        var count = 0
        if (uiState.selectedType != "All") count++
        if (uiState.selectedGenre != "All") count++
        if (uiState.selectedTheme != "All") count++
        if (uiState.selectedTarget != "All") count++
        count
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- 1. Standard AniCata Section Header ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(themeColor)
            )
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "📚",
                        fontSize = 18.sp
                    )
                    Text(
                        text = "All Lists Explorer",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF191C1E),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "Browse & filter ${uiState.selectedCategory.lowercase()} by genre, format & score",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 11.5.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // --- 2. Full-Width Category Segmented Control (Anime / Manga Toggle) ---
        CategorySegmentedControl(
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = { category ->
                onCategoryChanged(category)
                onUpdateFilterClicked()
            },
            themeColor = themeColor,
            modifier = Modifier.fillMaxWidth()
        )

        // --- 3. Quick Filter & Sort Horizontal Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = activeFilterCount > 0,
                onClick = { showFilterBottomSheet = true },
                label = {
                    Text(
                        text = if (activeFilterCount > 0) "Filters ($activeFilterCount)" else "All Filters",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter options",
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE0F2F1),
                    selectedLabelColor = themeColor,
                    selectedLeadingIconColor = themeColor,
                    containerColor = Color(0xFFF0F0F0),
                    labelColor = Color(0xFF424242)
                ),
                border = null,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.height(38.dp)
            )

            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(1.dp)
                    .background(Color.LightGray.copy(alpha = 0.6f))
            )

            uiState.sortOptions.forEach { sortOption ->
                val isSelected = uiState.selectedSort == sortOption
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            onSortChanged(sortOption)
                            onUpdateFilterClicked()
                        }
                    },
                    label = {
                        Text(
                            text = sortOption,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = themeColor,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF0F0F0),
                        labelColor = Color(0xFF424242)
                    ),
                    border = null,
                    modifier = Modifier.height(38.dp)
                )
            }
        }

        // --- 4. Active Filter Dismissible Pills Bar ---
        AnimatedVisibility(
            visible = activeFilterCount > 0,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Active:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor,
                    modifier = Modifier.padding(end = 2.dp)
                )

                if (uiState.selectedType != "All") {
                    ActiveFilterBadge(
                        label = "Type: ${uiState.selectedType}",
                        onDismiss = {
                            onTypeChanged("All")
                            onUpdateFilterClicked()
                        },
                        themeColor = themeColor
                    )
                }

                if (uiState.selectedGenre != "All") {
                    ActiveFilterBadge(
                        label = "Genre: ${uiState.selectedGenre}",
                        onDismiss = {
                            onGenreChanged("All")
                            onUpdateFilterClicked()
                        },
                        themeColor = themeColor
                    )
                }

                if (uiState.selectedTarget != "All") {
                    ActiveFilterBadge(
                        label = "Target: ${uiState.selectedTarget}",
                        onDismiss = {
                            onTargetChanged("All")
                            onUpdateFilterClicked()
                        },
                        themeColor = themeColor
                    )
                }

                if (uiState.selectedTheme != "All") {
                    ActiveFilterBadge(
                        label = "Theme: ${uiState.selectedTheme}",
                        onDismiss = {
                            onThemeChanged("All")
                            onUpdateFilterClicked()
                        },
                        themeColor = themeColor
                    )
                }

                TextButton(
                    onClick = {
                        onTypeChanged("All")
                        onGenreChanged("All")
                        onTargetChanged("All")
                        onThemeChanged("All")
                        onUpdateFilterClicked()
                    },
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "Reset All",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    // --- Modal Bottom Sheet Filter ---
    if (showFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterBottomSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            FilterBottomSheetContent(
                uiState = uiState,
                onTypeChanged = onTypeChanged,
                onGenreChanged = onGenreChanged,
                onTargetChanged = onTargetChanged,
                onThemeChanged = onThemeChanged,
                themeColor = themeColor,
                onApply = {
                    onUpdateFilterClicked()
                    showFilterBottomSheet = false
                },
                onReset = {
                    onTypeChanged("All")
                    onGenreChanged("All")
                    onTargetChanged("All")
                    onThemeChanged("All")
                    onUpdateFilterClicked()
                    showFilterBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun CategorySegmentedControl(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    themeColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(25.dp),
        color = Color(0xFFF0F0F0),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val options = listOf("Anime", "Manga")
            options.forEach { category ->
                val isSelected = selectedCategory == category
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(22.dp))
                        .background(
                            if (isSelected) themeColor
                            else Color.Transparent
                        )
                        .clickable { if (!isSelected) onCategorySelected(category) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else Color(0xFF424242)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActiveFilterBadge(
    label: String,
    onDismiss: () -> Unit,
    themeColor: Color
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE0F2F1),
        contentColor = themeColor
    ) {
        Row(
            modifier = Modifier
                .clickable { onDismiss() }
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove filter",
                modifier = Modifier.size(13.dp)
            )
        }
    }
}

@Composable
private fun FilterBottomSheetContent(
    uiState: AllListsUiState,
    onTypeChanged: (String) -> Unit,
    onGenreChanged: (String) -> Unit,
    onTargetChanged: (String) -> Unit,
    onThemeChanged: (String) -> Unit,
    themeColor: Color,
    onApply: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Filter Options",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF191C1E)
            )
            TextButton(onClick = onReset) {
                Text("Reset All", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterChipSection(
                title = "Format / Type",
                options = uiState.typeOptions,
                selectedOption = uiState.selectedType,
                onOptionSelected = onTypeChanged,
                themeColor = themeColor
            )

            FilterChipSection(
                title = "Genre",
                options = uiState.genreList.map { it.name },
                selectedOption = uiState.selectedGenre,
                onOptionSelected = onGenreChanged,
                themeColor = themeColor
            )

            if (uiState.demographicList.size > 1) {
                FilterChipSection(
                    title = "Demographic / Target",
                    options = uiState.demographicList.map { it.name },
                    selectedOption = uiState.selectedTarget,
                    onOptionSelected = onTargetChanged,
                    themeColor = themeColor
                )
            }

            if (uiState.themeList.size > 1) {
                FilterChipSection(
                    title = "Theme",
                    options = uiState.themeList.map { it.name },
                    selectedOption = uiState.selectedTheme,
                    onOptionSelected = onThemeChanged,
                    themeColor = themeColor
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = onApply,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = themeColor,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Apply Filters",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun FilterChipSection(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    themeColor: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF191C1E)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedOption == option
                FilterChip(
                    selected = isSelected,
                    onClick = { onOptionSelected(option) },
                    label = {
                        Text(
                            text = option,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = themeColor,
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF0F0F0),
                        labelColor = Color(0xFF424242)
                    ),
                    border = null
                )
            }
        }
    }
}
