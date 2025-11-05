package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                    // MangaHeaderSection(mangaData = mangaData)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Panggil MangaInfoStats yang sudah diperbaiki
                    MangaInfoStats(mangaData = mangaData)

                    // (Tombol Aksi jika ada)
                    ActionButtonsGroup()

                    Spacer(modifier = Modifier.height(8.dp))

                    // Panggil MangaInfoDetail yang sudah diperbaiki
                    MangaInfoDetail(mangaData = mangaData)

                    MangaSynopsisInfo(mangaData = mangaData)
                    // SynopsisInfo(synopsis = mangaData.synopsis)

                    // Panggil CharacterListLayout untuk manga
                    CharacterListLayout(charactersResult = uiState.mangaCharacters)

                    //MangaStaffInfo(staffResult = uiState.mangaStaff)
                    // MangaStaffInfo(staffResult = uiState.mangaStaff)

                    OtherMangaInfo(
                        relations = mangaData.relations,
                        /*onRelationClick = { relatedId ->
                            navController.navigate(Screen.MangaDetail.createRoute(relatedId))

                            // Logika navigasi saat item relasi diklik
                            // val route = Screen.MangaDetail.createRoute(relatedId)
                            // navController.navigate(route)
                        }*/
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