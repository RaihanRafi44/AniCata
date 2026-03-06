package com.raihan.anicata.ui.detail.anime

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
fun DetailAnimeScreen(
    animeId: Int, // Terima ID dari Navigasi
    viewModel: DetailAnimeViewModel = koinViewModel(),
    navController: NavController // <-- 2. AMBIL NAVCONTROLLER DARI SCREEN YANG MEMANGGIL
) {

    // Panggil API saat screen pertama kali dibuka
    LaunchedEffect(key1 = animeId) {
        viewModel.getAnimeDetails(animeId)
    }

    // Observasi state dari ViewModel
    val uiState by viewModel.uiState.collectAsState()

    val isBookmarked by viewModel.isBookmarked.collectAsState() // Observasi status bookmark

    val isFavorite by viewModel.isFavorite.collectAsState() // Observasi status favorite

    var showAddDialogBookmarkAnime by remember { mutableStateOf(false) }
    var showRemoveDialogBookmarkAnime by remember { mutableStateOf(false) }

    var showAddDialogFavoriteAnime by remember { mutableStateOf(false) }
    var showRemoveDialogFavoriteAnime by remember { mutableStateOf(false) }

    if (showAddDialogBookmarkAnime) {
        AlertDialog(
            onDismissRequest = { showAddDialogBookmarkAnime = false },
            title = { Text(text = "Konfirmasi") },
            text = { Text(text = "Apakah Anda yakin ingin menambahkan anime ini ke Bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Ambil data anime dari state (jika sukses) untuk disimpan
                        val animeData = (uiState.animeDetail as? ResultWrapper.Success)?.payload
                        if (animeData != null) {
                            viewModel.saveToBookmark(animeData)
                        }
                        showAddDialogBookmarkAnime = false
                    }
                ) {
                    Text("Ya, Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogBookmarkAnime = false }) {
                    Text("Batal")
                }
            }
        )
    }

    if (showRemoveDialogBookmarkAnime) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogBookmarkAnime = false },
            title = { Text("Hapus Bookmark") },
            text = { Text("Anda yakin ingin menghapus anime ini dari bookmark?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Panggil fungsi Hapus di ViewModel
                        viewModel.removeFromBookmark(animeId.toString())
                        showRemoveDialogBookmarkAnime = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogBookmarkAnime = false }) { Text("Batal") }
            }
        )
    }

    if (showAddDialogFavoriteAnime) {
        AlertDialog(
            onDismissRequest = { showAddDialogFavoriteAnime = false },
            title = { Text(text = "Konfirmasi") },
            text = { Text(text = "Apakah Anda yakin ingin menambahkan anime ini ke Favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Ambil data anime dari state (jika sukses) untuk disimpan
                        val animeData = (uiState.animeDetail as? ResultWrapper.Success)?.payload
                        if (animeData != null) {
                            viewModel.saveToFavorite(animeData)
                        }
                        showAddDialogFavoriteAnime = false
                    }
                ) {
                    Text("Ya, Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialogFavoriteAnime = false }) {
                    Text("Batal")
                }
            }
        )
    }

    if (showRemoveDialogFavoriteAnime) {
        AlertDialog(
            onDismissRequest = { showRemoveDialogFavoriteAnime = false },
            title = { Text("Hapus Favorite") },
            text = { Text("Anda yakin ingin menghapus anime ini dari favorite?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.removeFromFavorite(animeId.toString())
                        showRemoveDialogFavoriteAnime = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialogFavoriteAnime = false }) { Text("Batal") }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val detailResult = uiState.animeDetail) {
            is ResultWrapper.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ResultWrapper.Error -> {
                // Tampilkan pesan error
                Text(
                    text = "Gagal memuat data: ${detailResult.exception?.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Success -> {

                val animeData = detailResult.payload!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HeaderSection(animeData = animeData)

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimeInfoStats(animeData = animeData)

                    ActionButtonsGroup(
                        isBookmarked = isBookmarked,
                        isFavorite = isFavorite,
                        onBookmarkClick = {
                            if (!isBookmarked) {
                                showAddDialogBookmarkAnime = true
                            } else {
                                showRemoveDialogBookmarkAnime = true
                            }
                        },
                        onFavoriteClick = {
                            if (!isFavorite) {
                                showAddDialogFavoriteAnime = true
                            } else {
                                showRemoveDialogFavoriteAnime = true
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimeInfoDetail(animeData = animeData)

                    SynopsisInfo(animeData = animeData)

                    CharacterListLayout(charactersResult = uiState.animeCharacters)

                    StaffInfo(staffResult = uiState.animeStaff)

                    VideoPromoSection(trailer = animeData.trailer)

                    OtherAnimeInfo(
                        relations = animeData.relations,

                        onRelationClick = { id, type ->
                            if (type.lowercase() == "manga" || type.lowercase() == "novel" || type.lowercase() == "light novel") {
                                navController.navigate(Screen.MangaDetail.createRoute(id))
                            } else {
                                navController.navigate(Screen.Detail.createRoute(id))
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "Data anime tidak ditemukan.",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultWrapper.Idle -> {

            }
        }
    }


}

