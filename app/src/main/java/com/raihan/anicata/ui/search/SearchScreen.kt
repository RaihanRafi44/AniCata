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

/*@Composable
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
}*/

/*
@Composable
fun SearchScreenLayout(
    onClose: () -> Unit,
    // Parameter baru untuk navigasi saat menekan "Enter"
    onSearchSubmitted: (String) -> Unit,
    // Inject ViewModel menggunakan Koin
    viewModel: SearchViewModel = koinViewModel()
) {
    // Ambil state query dan hasil pencarian dari ViewModel
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResultsState by viewModel.searchResults.collectAsState()

    // Kontroler keyboard untuk menutup keyboard saat "Enter" ditekan
    val keyboardController = LocalSoftwareKeyboardController.current

    // Definisikan aksi keyboard
    val keyboardActions = KeyboardActions(
        onSearch = {
            if (searchQuery.isNotBlank()) {
                keyboardController?.hide() // Tutup keyboard
                onSearchSubmitted(searchQuery) // Pindah ke ResultSearchScreen
            }
        }
    )

    Row(
        modifier = Modifier
            //.fillMaxWidth()
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Bagian Kiri: Berisi Search bar dan List
        Column(
            //modifier = Modifier.weight(1f),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), // <-- UBAH DI SINI: Isi seluruh tinggi
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Panggil Composable Search Field
            SearchTextField(
                query = searchQuery,
                onQueryChange = viewModel::onQueryChange, // Panggil fungsi ViewModel
                // Tambahkan keyboard options & actions
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = keyboardActions
            )

            // 'when' sekarang menggunakan state dari ResultWrapper
            when (val state = searchResultsState) {
                is ResultWrapper.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                            //.padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ResultWrapper.Success -> {
                    // payload dijamin non-null oleh konstruktor Success
                    SearchResultList(animeList = state.payload!!)
                }
                is ResultWrapper.Error -> {
                    Text(
                        text = state.exception?.message ?: "An unknown error occurred",
                        color = Color.Red,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                is ResultWrapper.Empty -> {
                    if (searchQuery.isNotBlank()) {
                        Text(
                            text = "No results found for \"$searchQuery\"",
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
                is ResultWrapper.Idle -> {
                    // State awal (saat query kosong), tidak perlu tampilkan apa-apa
                    Spacer(modifier = Modifier.weight(1f)) // <-- UBAH DI SINI
                }
            }
        }

        // Bagian Kanan: Tombol Silang (Close)
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
}
*/

/*
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
}*/

@Composable
fun SearchScreenLayout(
    onClose: () -> Unit,
    onSearchSubmitted: (String) -> Unit,
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
                    modifier = Modifier.weight(1f)
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
