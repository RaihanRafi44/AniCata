package com.raihan.anicata.ui.detail

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

// Data class tidak berubah
data class MediaEntry(
    val id: Int,
    val title: String,
    // val imageUrl: String,
    val tags: List<String> = emptyList()
)

// Dummy data tidak berubah
val relatedEntries = listOf(
    MediaEntry(1, "Fullmetal Alchemist", tags = listOf("MANGA", "ADAPTATION")),
    MediaEntry(2, "Attack on Titan : Final Season Part 1", tags = listOf("MANGA", "ANIME")),
    MediaEntry(3, "One Piece", tags = listOf("MANGA"))
)

/**
 * Section utama yang menampilkan judul dan daftar entri terkait.
 */
@Composable
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


/**
 * Composable untuk menampilkan satu kartu entri, GAYA DISESUAIKAN.
 */
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
}

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
    RelatedInfo()
}