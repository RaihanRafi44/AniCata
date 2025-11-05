package com.raihan.anicata.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreenLayout(
    onClose: () -> Unit,
    onSearchSubmitted: (String) -> Unit,
    onAnimeClick: (Int) -> Unit, // <-- 1. TAMBAHKAN PARAMETER BARU
    onMangaClick: (Int) -> Unit, // <-- 1. TAMBAHKAN PARAMETER BARU
    viewModel: SearchViewModel = koinViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResultsState by viewModel.searchResults.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(
        onSearch = {
            if (searchQuery.isNotBlank()) {
                keyboardController?.hide()
                onSearchSubmitted(searchQuery)
            }
        }
    )

    // --- UBAH DI SINI: Root diubah dari Row menjadi Column ---
    Column(
        modifier = Modifier
            .fillMaxSize() // Mengisi parent (misal: dialog atau box)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // --- BARIS 1: Search Field dan Tombol Close ---
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top // Pastikan rata atas
        ) {
            // Panggil Composable Search Field
            SearchTextField(
                modifier = Modifier.weight(1f), // <-- UBAH DI SINI: Search field mengisi sisa ruang
                query = searchQuery,
                onQueryChange = viewModel::onQueryChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = keyboardActions
            )

            // --- Tombol Close dipindahkan ke dalam Row ini ---
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(48.dp)
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

        // --- BARIS 2: Konten (Loading, List, Error) ---
        when (val state = searchResultsState) {
            is ResultWrapper.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // weight(1f) untuk mengisi sisa ruang vertikal
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is ResultWrapper.Success -> {
                SearchResultList(
                    animeList = state.payload!!,
                    // <-- UBAH DI SINI: Tambahkan weight agar list mengisi sisa ruang
                    modifier = Modifier.weight(1f),
                    // --- 2. TAMBAHKAN onITEMClick ---
                    onItemClick = { animeInfo ->
                        // 3. LOGIKA FILTER ANDA:
                        // Hanya navigasi jika item yang diklik adalah Anime
                        if (animeInfo.category == "Anime") {
                            onAnimeClick(animeInfo.id)
                        }
                        if (animeInfo.category == "Manga") {
                            onMangaClick(animeInfo.id)
                        }
                        // Jika item adalah "Manga", tidak terjadi apa-apa
                    }
                )
            }
            is ResultWrapper.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Pusatkan pesan error
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.exception?.message ?: "An unknown error occurred",
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            is ResultWrapper.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Pusatkan pesan empty
                    contentAlignment = Alignment.Center
                ) {
                    if (searchQuery.isNotBlank()) {
                        Text(
                            text = "No results found for \"$searchQuery\"",
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            is ResultWrapper.Idle -> {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
