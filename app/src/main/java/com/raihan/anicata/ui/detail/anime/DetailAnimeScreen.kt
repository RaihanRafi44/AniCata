package com.raihan.anicata.ui.detail.anime

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
                // Jika sukses, tampilkan emua konten
                // detailResult.payload berisi objek AnimeData
                val animeData = detailResult.payload!!

                // --- PERBAIKAN DI SINI ---
                // 1. Tentukan relasi utama
                //val primaryRelationTypes = setOf("Sequel", "Prequel", "Adaptation")

                // 2. Pisahkan (partition) list relasi
                /*val (primaryRelations, otherRelations) = animeData.relations.partition {
                    it.relation in primaryRelationTypes
                }*/
                // --- AKHIR PERBAIKAN ---

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    HeaderSection(animeData = animeData)

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimeInfoStats(animeData = animeData)

                    ActionButtonsGroup()

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimeInfoDetail(animeData = animeData)

                    SynopsisInfo(animeData = animeData)

                    CharacterListLayout(charactersResult = uiState.animeCharacters)

                    StaffInfo(staffResult = uiState.animeStaff)

                    VideoPromoSection(trailer = animeData.trailer)

                    //RelatedInfo(relations = primaryRelations)

                    //OtherAnimeInfo(relations = otherRelations)
                    // --- BERIKAN SELURUH DATA KE 'OtherAnimeInfo' ---
                    //OtherAnimeInfo(relations = animeData.relations)
                    OtherAnimeInfo(
                        relations = animeData.relations,
                        // 3. Buat lambda onClick di sini
                        /*onRelationClick = { relatedAnimeId ->
                            // 4. Perintahkan NavController untuk navigasi
                            // Ini akan membuka DetailAnimeScreen LAGI,
                            // tapi dengan ID baru.
                            navController.navigate(Screen.Detail.createRoute(relatedAnimeId))
                        },*/
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
                // State awal, tidak melakukan apa-apa
            }
        }
    }


}

/*
@Preview(showBackground = true)
@Composable
fun DetailAnimeScreenPreview() {
    DetailAnimeScreen()
}*/
