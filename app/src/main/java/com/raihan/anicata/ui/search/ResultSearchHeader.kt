package com.raihan.anicata.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultSearchHeader(
    modifier: Modifier = Modifier,
    searchQuery: String
) {
    Column(
        // 1. Mengisi lebar parent & menambah padding horizontal
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Judul Utama
        Text(
            text = "Result Search",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp)) // Jarak bisa sedikit dikurangi

        // Sub-judul (Query Pencarian)
        Text(
            text = "\"$searchQuery\"",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            // 2. Memastikan teks selalu rata tengah, bahkan jika lebih dari 1 baris
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSearchHeaderPreview() {
    // Contoh untuk melihat teks yang panjang
    ResultSearchHeader(searchQuery = "Fullmetal Alchemist: Brotherhood The Movie - The Sacred Star of Milos")
}