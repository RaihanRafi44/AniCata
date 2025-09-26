package com.raihan.anicata.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResultSearchScreen() {
    // Wadah utama dengan latar belakang hijau mint seperti pada gambar
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFE0F2F1)) // Warna latar belakang hijau mint
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Memanggil Composable header dari file terpisah
        ResultSearchHeader(searchQuery = "Fullmetal Alchemist: Brotherhood The Movie - The Sacred Star of Milos")

        Spacer(modifier = Modifier.height(16.dp))

        // Memanggil layout daftar hasil pencarian dari file ResultList.kt
        SearchResultListLayout()
    }
}

@Preview(showBackground = true)
@Composable
fun ResultSearchScreenPreview() {
    MaterialTheme {
        ResultSearchScreen()
    }
}