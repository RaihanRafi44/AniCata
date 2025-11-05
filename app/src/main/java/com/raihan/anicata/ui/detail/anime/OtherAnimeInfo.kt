package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.anime.full.RelationAnime

// Warna yang mendekati gambar
//val lightGreenishBackground = Color(0xFFE8F5E9)
private val specialOrange = Color(0xFFF57C00)
private val tvBrown = Color(0xFFBF360C)

// Warna tambahan dari file RelatedInfo.kt
private val mangaBlue = Color(0xFF2DB8F4)
private val animePink = Color(0xFFD81B60)

@Composable
fun OtherAnimeInfo(
    relations: List<RelationAnime>, // <-- Terima data relasi dari AnimeData
    //onRelationClick: (Int) -> Unit, // <-- 2. TAMBAHKAN CALLBACK
    onRelationClick: (id: Int, type: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Cek jika relasi tidak kosong
    if (relations.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.background(lightGreenishBackground)
                .padding(16.dp)
        ){
            // Loop untuk setiap TIPE relasi (Adaptation, Prequel, etc)
            relations.forEachIndexed { index, relation ->
                // Tampilkan header untuk tipe relasi
                SectionHeader(title = relation.relation)
                Spacer(modifier = Modifier.height(8.dp))

                // Loop untuk setiap anime DI DALAM tipe relasi tsb
                relation.entry.forEach { entry ->
                    MediaItem(
                        title = entry.name,
                        tag = entry.type,
                        // 3. Teruskan ID dan callback ke MediaItem
                        //onClick = { onRelationClick(entry.malId) }
                        onClick = { onRelationClick(entry.malId, entry.type) }
                    )
                }

                // Beri spasi jika bukan item terakhir
                if (index < relations.size - 1) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    } else {
        // Tampilkan pesan jika tidak relasi
        Text(
            text = "No related anime found.",
            modifier = modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = Color.DarkGray,
        fontSize = 16.sp
    )
}

@Composable
fun MediaItem(
    title: String,
    tag: String,
    onClick: () -> Unit // <-- 4. TAMBAHKAN PARAMETER ONCLICK
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }, // <-- 5. BUAT ROW BISA DIKLIK
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f), // Mendorong tag ke kanan
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        MediaTag(text = tag)
    }
}

@Composable
fun MediaTag(text: String) {
    val backgroundColor = when (text.uppercase()) {
        "SPECIAL", "MOVIE", "TV", "OVA", "ONA", "MUSIC" -> specialOrange
        "MANGA", "NOVEL", "DOUJINSHI", "MANHWA", "MANHUA", "LIGHT NOVEL", "ONE-SHOT" -> mangaBlue
        "ANIME" -> tvBrown
        else -> Color.Gray // Warna default jika ada tag lain
    }

    val displayText = text.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FmaRelatedContentLayoutPreview() {
    //OtherAnimeInfo()
}