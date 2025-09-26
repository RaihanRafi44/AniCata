package com.raihan.anicata.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreenLayout(
    onClose: () -> Unit // <-- TAMBAHKAN parameter ini
) {
    //var searchQuery by remember { mutableStateOf("Naruto") }
    var searchQuery by remember { mutableStateOf("") } // <-- Ubah state awal menjadi string kosong
    val animeList = getAnimeData()

    // Layout utama sekarang adalah Row, bukan Column
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top // Penting: agar semua elemen rata atas
    ) {
        // Bagian Kiri: Berisi Search bar dan List
        Column(
            modifier = Modifier.weight(1f), // Mengisi sisa ruang
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Panggil Composable Search Field dari SearchBox.kt
            SearchTextField(
                query = searchQuery,
                onQueryChange = { newQuery -> searchQuery = newQuery }
            )

            // Panggil Composable List dari SearchList.kt
            SearchResultList(animeList = animeList)
        }

        // Bagian Kanan: Tombol Silang (Close)
        IconButton(
            onClick = onClose, // <-- PANGGIL lambda onClose saat tombol diklik,
            modifier = Modifier
                .size(48.dp) // Samakan tingginya dengan search bar
                .background(
                    color = Color(0xFFD32F2F),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
fun SearchScreenLayoutPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = 0.5f)),
        contentAlignment = Alignment.TopCenter
    ) {
        SearchScreenLayout(onClose = {})
    }
}