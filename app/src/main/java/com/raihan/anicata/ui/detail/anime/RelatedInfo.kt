package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.raihan.anicata.data.model.anime.full.MalUrlAnimeEntryRelation
import com.raihan.anicata.data.model.anime.full.RelationAnime

/**
 * Section utama yang menampilkan judul dan daftar entri terkait.
 */
/*@Composable
fun RelatedInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Related Entries",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar item disamakan
        ) {
            items(relatedEntries) { entry ->
                EntryCard(entry = entry)
            }
        }
    }
}


*//**
 * Composable untuk menampilkan satu kartu entri, GAYA DISESUAIKAN.
 *//*
@Composable
fun EntryCard(
    entry: MediaEntry,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(130.dp) // Lebar kartu disesuaikan
            .height(230.dp), // Tinggi total kartu disesuaikan
    ) {
        Box(
            modifier = Modifier
                .height(180.dp) // Tinggi area gambar disesuaikan
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Placeholder untuk gambar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .border(
                        width = 1.dp,
                        color = Color.Black, // Warna border disesuaikan
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            // Kolom untuk menampung semua tag
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp), // Padding container tag disesuaikan
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                entry.tags.forEach { tagText ->
                    TagBadge(text = tagText)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp)) // Jarak disesuaikan

        // Judul di bawah gambar
        Text(
            text = entry.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center, // Perataan teks diubah ke tengah
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp) // Tinggi teks judul dibuat tetap
        )
    }
}*/

@Composable
fun RelatedInfo(
    modifier: Modifier = Modifier,
    relations: List<RelationAnime> // <-- Terima data relasi dari API
) {
    // Gabungkan semua entri dari relasi primer (Sequel, Prequel, Adaptation)
    val allPrimaryEntries = relations.flatMap { it.entry }

    // Tampilkan section ini HANYA jika ada datanya
    if (allPrimaryEntries.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Related Entries",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Loop data 'allPrimaryEntries' dari API
                items(allPrimaryEntries) { entry ->
                    EntryCard(entry = entry) // Kirim data API ke EntryCard
                }
            }
        }
    }
}

/**
 * EntryCard diubah agar menerima data dari API (MalUrlAnimeEntryRelation)
 * dan TIDAK menampilkan gambar (karena API tidak menyediakannya).
 */
@Composable
fun EntryCard(
    entry: MalUrlAnimeEntryRelation, // <-- Terima data API
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(130.dp) // Lebar kartu
            .height(80.dp) // Tinggi kartu dikurangi (tanpa gambar)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Judul
        Text(
            text = entry.name, // <-- Data dari API
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        // Tag Tipe (Anime/Manga)
        TagBadge(text = entry.type) // <-- Data dari API
    }
}

/**
 * Composable TagBadge (tidak berubah, bisa dipakai ulang)
 */

/**
 * Composable khusus untuk badge/tag, GAYA DISESUAIKAN.
 */
@Composable
fun TagBadge(text: String) {
    fun getColorForTag(tag: String): Color {
        return when (tag.uppercase()) {
            "MANGA" -> Color(0xFFF9A825) // Oranye
            "ADAPTATION" -> Color(0xFF1E88E5) // Biru
            "ANIME" -> Color(0xFFD81B60) // Merah muda
            else -> Color.Gray
        }
    }

    Text(
        text = text,
        modifier = Modifier
            .background(
                color = getColorForTag(text),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp), // Padding internal disesuaikan
        color = Color.Black, // Warna teks diubah ke hitam
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFE0F2F1)
@Composable
fun RelatedEntriesSectionPreview() {
    //RelatedInfo()
}