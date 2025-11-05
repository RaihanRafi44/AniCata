package com.raihan.anicata.ui.detail.manga

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
import com.raihan.anicata.data.model.manga.full.MangaFullRelation

// Warna yang mendekati gambar
//val lightGreenishBackground = Color(0xFFE8F5E9)
private val specialOrange = Color(0xFFF57C00)
private val tvBrown = Color(0xFFBF360C)
private val mangaYellow = Color(0xFF2DB8F4)


@Composable
fun OtherMangaInfo(
    relations: List<MangaFullRelation>, // <-- 2. Terima List<MangaFullRelation>
    //onRelationClick: (Int) -> Unit,   // <-- 3. Terima callback klik
    onRelationClick: (id: Int, type: String) -> Unit,
    modifier: Modifier = Modifier
) {

    // 4. Terapkan logika yang sama dengan OtherAnimeInfo
    if (relations.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            relations.forEachIndexed { index, relation ->
                SectionHeader(title = relation.relation) //
                Spacer(modifier = Modifier.height(8.dp)) //

                relation.entry.forEach { entry ->
                    MediaItem(
                        title = entry.name,
                        tag = entry.type,
                        //onClick = { onRelationClick(entry.id) } // <-- 5. Panggil callback dengan ID
                        onClick = { onRelationClick(entry.id, entry.type) }
                    )
                }

                if (index < relations.size - 1) {
                    Spacer(modifier = Modifier.height(24.dp)) //
                }
            }
        }
    } else {
        Text(
            text = "No related manga found.",
            modifier = modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
    /*Column(
        modifier = Modifier
            .fillMaxWidth()
            //.background(lightGreenishBackground)
            .padding(16.dp)
    ) {
        // --- Bagian Side Story ---
        SectionHeader(title = "Side Story")
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Spin-Off ---
        SectionHeader(title = "Spin-Off")
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Alternative Version ---
        SectionHeader(title = "Alternative Version")
        Spacer(modifier = Modifier.height(8.dp))
    }*/
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
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
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
        /*"SPECIAL", "MOVIE" -> specialOrange
        "TV" -> tvBrown*/
        "SPECIAL", "MOVIE", "TV", "OVA", "ONA", "MUSIC" -> specialOrange
        "MANGA", "NOVEL", "DOUJINSHI", "MANHWA", "MANHUA", "LIGHT NOVEL", "ONE-SHOT" -> mangaYellow
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
            //text = text,
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