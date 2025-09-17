package com.raihan.anicata.ui.detail

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
// --- Data Model & Sample Data ---

data class CharacterInfo(
    val characterName: String,
    val characterRole: String,
    val voiceActorName: String,
    val voiceActorLanguage: String
)

val characterDataList = listOf(
    CharacterInfo("Elric, Edward", "Main", "Park, Romi", "Japanese"),
    CharacterInfo("Elric, Alphonse", "Main", "Kugimiya, Rie", "Japanese"),
    CharacterInfo("Mustang, Roy", "Supporting", "Miki, Shinichiro", "Japanese"),
    CharacterInfo("Hughes, Maes", "Supporting", "Fujiwara, Keiji", "Japanese")
)


// --- Composable untuk Layout ---

*/
/**
 * Composable utama yang berisi seluruh layout karakter dan pengisi suara.
 *//*

@Composable
fun CharacterListLayout(
    characters: List<CharacterInfo>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Characters and Voice Actors",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            characters.forEach { data ->
                CharacterListItem(info = data)
            }
        }
    }
}

*/
/**
 * Composable untuk menampilkan satu baris item dalam daftar.
 *//*

@Composable
fun CharacterListItem(info: CharacterInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Sisi Kiri: Info Karakter
        Row(
            verticalAlignment = Alignment.Top // DIUBAH: Teks menjadi rata atas
        ) {
            // DIUBAH: Bentuk menjadi persegi panjang vertikal
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0))
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Menambahkan padding atas agar teks tidak terlalu menempel
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = info.characterName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Text(
                    text = info.characterRole,
                    color = Color.Gray,
                    fontSize = 12.sp)
            }
        }

        // Sisi Kanan: Info Pengisi Suara
        Row(
            verticalAlignment = Alignment.Top // DIUBAH: Teks menjadi rata atas
        ) {
            // Menambahkan padding atas agar teks tidak terlalu menempel
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = info.voiceActorName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,)
                Text(
                    text = info.voiceActorLanguage,
                    color = Color.Gray,
                    fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            // DIUBAH: Bentuk menjadi persegi panjang vertikal
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0))
            )
        }
    }
}


// --- Preview untuk Pengecekan ---

@Preview(showBackground = true)
@Composable
fun CharacterListLayoutPreview() {
    Surface(color = Color(0xFFEBF5EE)) {
        CharacterListLayout(characters = characterDataList)
    }
}*/

// --- Data Model & Sample Data ---

data class CharacterInfo(
    val characterName: String,
    val characterRole: String,
    val voiceActorName: String,
    val voiceActorLanguage: String
)

// MENAMBAHKAN LEBIH BANYAK DATA UNTUK MENGUJI FITUR
val characterDataList = listOf(
    CharacterInfo("Elric, Edward", "Main", "Park, Romi", "Japanese"),
    CharacterInfo("Elric, Alphonse", "Main", "Kugimiya, Rie", "Japanese"),
    CharacterInfo("Mustang, Roy", "Supporting", "Miki, Shinichiro", "Japanese"),
    CharacterInfo("Hughes, Maes", "Supporting", "Fujiwara, Keiji", "Japanese"),
    CharacterInfo("Rockbell, Winry", "Supporting", "Takagi, Megumi", "Japanese"),
    CharacterInfo("Hawkeye, Riza", "Supporting", "Orikasa, Fumiko", "Japanese"),
    CharacterInfo("Armstrong, Alex Louis", "Supporting", "Utsumi, Kenji", "Japanese")
)


// --- Composable untuk Layout ---

/**
 * Composable utama yang berisi seluruh layout karakter dan pengisi suara.
 */
@Composable
fun CharacterListLayout(
    characters: List<CharacterInfo>,
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
            text = "Characters and Voice Actors",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsToShow.forEach { data ->
                CharacterListItem(info = data)
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

/**
 * Composable untuk menampilkan satu baris item dalam daftar.
 * (Tidak ada perubahan di sini)
 */
@Composable
fun CharacterListItem(info: CharacterInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Sisi Kiri: Info Karakter
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Box(
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
                    text = info.characterName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Text(
                    text = info.characterRole,
                    color = Color.Gray,
                    fontSize = 12.sp)
            }
        }

        // Sisi Kanan: Info Pengisi Suara
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = info.voiceActorName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,)
                Text(
                    text = info.voiceActorLanguage,
                    color = Color.Gray,
                    fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0))
            )
        }
    }
}


// --- Preview untuk Pengecekan ---

@Preview(showBackground = true)
@Composable
fun CharacterListLayoutPreview() {
    Surface(color = Color(0xFFEBF5EE)) {
        CharacterListLayout(characters = characterDataList)
    }
}
