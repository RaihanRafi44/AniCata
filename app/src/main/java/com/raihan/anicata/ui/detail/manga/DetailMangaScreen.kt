package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raihan.anicata.ui.navigation.Screen
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailMangaScreen(
    mangaId: Int,
    viewModel: DetailMangaViewModel = koinViewModel(),
    navController: NavController
) {

    LaunchedEffect(key1 = mangaId) {
        viewModel.getMangaDetails(mangaId)
    }

    val uiState by viewModel.uiState.collectAsState()

    val isBookmarked by viewModel.isBookmarked.collectAsState()

    val isFavorite by viewModel.isFavorite.collectAsState()

    var showAddDialogBookmarkManga by remember { mutableStateOf(false) }
    var showRemoveDialogBookmarkManga by remember { mutableStateOf(false) }

    var showAddDialogFavoriteManga by remember { mutableStateOf(false) }
    var showRemoveDialogFavoriteManga by remember { mutableStateOf(false) }

    if (showAddDialogBookmarkManga) {
        AlertDialog(
            onDismissRequest = { showAddDialogBookmarkManga = false },
            title = { Text(text = "Konfirmasi") },
            text = { Text(text = "Apakah Anda yakin ingin menambahkan manga ini ke Bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val mangaData = (uiState.mangaDetail as? ResultWrapper.Success)?.payload

                        if (mangaData != null) {
                            viewModel.saveToBookmark(mangaData)
                        }
                        showAddDialogBookmarkManga = false
                    }
                ) {
                    Text("Ya, Simpan")
                    }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogBookmarkManga = false }) { Text("Batal") }
            }
        )
    }

    if (showRemoveDialogBookmarkManga) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogBookmarkManga = false },
            title = { Text("Hapus Bookmark") },
            text = { Text("Anda yakin ingin menghapus anime ini dari bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Panggil fungsi Hapus di ViewModel
                        viewModel.removeFromBookmark(mangaId.toString())
                        showRemoveDialogBookmarkManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogBookmarkManga = false }) { Text("Batal") }
            }
        )
    }

    if (showAddDialogFavoriteManga) {
        AlertDialog(
            onDismissRequest = { showAddDialogFavoriteManga = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menambahkan manga ini ke Favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val mangaData = (uiState.mangaDetail as? ResultWrapper.Success)?.payload

                        if (mangaData != null) {
                            viewModel.saveToFavorite(mangaData)
                        }
                        showAddDialogFavoriteManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Ya, Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogFavoriteManga = false }) { Text("Batal") }
            }
        )
    }

    if (showRemoveDialogFavoriteManga) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogFavoriteManga = false },
            title = { Text("Hapus Favorite") },
            text = { Text("Anda yakin ingin menghapus anime ini dari favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.removeFromFavorite(mangaId.toString())
                        showRemoveDialogFavoriteManga = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogFavoriteManga = false }) { Text("Batal") }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val detailResult = uiState.mangaDetail) { //
            is ResultWrapper.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ResultWrapper.Error -> {
                Text(
                    text = "Gagal memuat: ${detailResult.exception?.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Success -> {
                // Jika sukses, ambil datanya
                val mangaData = detailResult.payload!! //

                // Tampilkan semua komponen UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MangaHeaderSection(mangaData = mangaData)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Panggil MangaInfoStats yang sudah diperbaiki
                    MangaInfoStats(mangaData = mangaData)

                    // (Tombol Aksi jika ada)
                    ActionButtonsGroup(
                        isBookmarked = isBookmarked,
                        isFavorite = isFavorite,
                        onBookmarkClick = {
                            if (!isBookmarked) {
                                showAddDialogBookmarkManga = true
                            } else {
                                showRemoveDialogBookmarkManga = true
                            }
                        },
                        onFavoriteClick = {
                            if (!isFavorite) {
                                showAddDialogFavoriteManga = true
                            } else {
                                showRemoveDialogFavoriteManga = true
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Panggil MangaInfoDetail yang sudah diperbaiki
                    MangaInfoDetail(mangaData = mangaData)

                    MangaSynopsisInfo(mangaData = mangaData)

                    // Panggil CharacterListLayout untuk manga
                    CharacterListLayout(charactersResult = uiState.mangaCharacters)

                    OtherMangaInfo(
                        relations = mangaData.relations,
                        onRelationClick = {id, type ->
                            if (type.lowercase() == "anime" || type.lowercase() == "ova" || type.lowercase() == "movie") {
                                navController.navigate(Screen.Detail.createRoute(id))
                            } else {
                                navController.navigate(Screen.MangaDetail.createRoute(id))
                            }
                        }

                    )
                    // OtherMangaInfo(relations = mangaData.relations, onRelationClick = { ... })

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "Data manga tidak ditemukan.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Idle -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    //DetailScreen()
}