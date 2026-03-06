package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.manga.characters.CharacterManga
import com.raihan.anicata.utils.ResultWrapper
import kotlin.collections.isNotEmpty
import kotlin.collections.orEmpty

@Composable
fun CharacterListLayout(
    charactersResult: ResultWrapper<List<CharacterManga>>,
    modifier: Modifier = Modifier
) {

    when (charactersResult) {
        is ResultWrapper.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is ResultWrapper.Success -> {
            val characters = charactersResult.payload.orEmpty()
            if (characters.isNotEmpty()) {
                CharacterListView(characters = characters, modifier = modifier)
            } else {
                Text(
                    text = "No characters found.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        is ResultWrapper.Error -> {
            Text(
                text = "Failed to load characters: ${charactersResult.exception?.message}",
                modifier = modifier.padding(16.dp),
                color = Color.Red
            )
        }
        is ResultWrapper.Empty -> {
            Text(
                text = "No characters found.",
                modifier = modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        is ResultWrapper.Idle -> { /* Tidak melakukan apa-apa */ }
    }

}

@Composable
private fun CharacterListView(
    characters: List<CharacterManga>,
    modifier: Modifier = Modifier
) {
    // 1. State untuk melacak apakah daftar harus diperluas atau tidak.
    // Defaultnya adalah false (tidak diperluas).
    var isExpanded by remember { mutableStateOf(false) }

    // 2. Tentukan daftar item yang akan ditampilkan berdasarkan state isExpanded.
    // Jika tidak diperluas, hanya tampilkan 4 item. Jika ya, tampilkan semua.
    val itemsToShow = if (isExpanded) characters else characters.take(4)

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Characters",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar baris
        ) {
            itemsToShow.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar kolom
                ) {
                    // Item Kiri
                    Box(modifier = Modifier.weight(1f)) {
                        CharacterCard(info = rowItems[0]) // Panggil Composable baru
                    }

                    // Item Kanan
                    Box(modifier = Modifier.weight(1f)) {
                        if (rowItems.size > 1) {
                            CharacterCard(info = rowItems[1]) // Panggil Composable baru
                        } else {
                            Spacer(Modifier.fillMaxWidth()) // Spacer jika jumlah item ganjil
                        }
                    }
                }
            }
        }

        // 3. Tampilkan tombol "View More / View Less" hanya jika jumlah
        // total karakter lebih dari 4.
        if (characters.size > 4) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                // Teks berubah tergantung state
                text = if (isExpanded) "View Less" else "View More",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // Saat diklik, ubah nilai state (dari true ke false atau sebaliknya)
                        isExpanded = !isExpanded
                    }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CharacterCard(info: CharacterManga) {
    // Logika ini diambil dari "Sisi Kiri" di CharacterListItem
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = info.character?.images?.jpg?.imageUrl,
            contentDescription = info.character?.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(64.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0E0E0))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = info.character?.name ?: "Unknown",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = info.role ?: "N/A",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}
