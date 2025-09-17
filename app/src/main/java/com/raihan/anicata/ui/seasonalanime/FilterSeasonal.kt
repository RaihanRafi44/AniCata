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

/**
 * Sebuah Composable yang menampilkan grup filter untuk anime.
 */
@Composable
fun AnimeFilterGroup() {
    val backgroundColor = Color(0xFFE6F5F3)
    val buttonColor = Color(0xFF00BFFF)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    // Column luar untuk padding dari tepi layar
    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
                //.background(backgroundColor, shape = RoundedCornerShape(16.dp)), // Padding di dalam container
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Seasonal Anime",
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
                    label = "Season",
                    options = listOf("Summer 2025", "Fall 2025", "Winter 2026"),
                    modifier = Modifier.weight(1f)
                )
                FilterDropdownMenu(
                    label = "Type",
                    options = listOf("TV", "Movie", "OVA"),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Baris kedua filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterDropdownMenu(
                    label = "Sort by",
                    options = listOf("A - Z", "Popularity", "Score"),
                    modifier = Modifier.weight(1f)
                )
                FilterDropdownMenu(
                    label = "Genres",
                    options = listOf("Action", "Comedy", "Fantasy", "Sci-Fi", "Drama", "Mystery", "Romance", "Horror", "Adventure", "Thriller", "Supernatural", "Superhero", "Historical", "Shounen", "Shoujo"),
                    modifier = Modifier.weight(1f)
                )
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
                    //modifier = Modifier.padding(vertical = 2.dp)
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
 * Composable dropdown menu yang telah diperbaiki.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdownMenu(
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options.firstOrNull() ?: "") }
    val interactionSource = remember { MutableInteractionSource() }

    // Mengecek apakah text field sedang dalam kondisi fokus (diklik)
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
        // Kita tetap menggunakan BasicTextField untuk kustomisasi penuh
        BasicTextField(
            value = "$label : $selectedOptionText",
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            textStyle = verySmallTextStyle.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                // ======================================================================
                // PENDEKATAN BARU YANG BENAR
                // Kita membangun "bingkai" secara manual menggunakan Row, Box, dan Modifier.border
                // ======================================================================
                Row(
                    modifier = Modifier
                        // 1. Membuat border secara manual
                        .border(
                            width = 1.dp,
                            // Warna border berubah saat di-klik (fokus)
                            color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        // 2. Di sini kita mengontrol padding internal secara penuh
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Box untuk menampung teks agar bisa memenuhi ruang
                    Box(modifier = Modifier.weight(1f)) {
                        innerTextField()
                    }
                    // Menampilkan ikon panah di ujung
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
                        selectedOptionText = selectionOption
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
fun AnimeFilterGroupFinalPreview() {
    AnimeFilterGroup()
}