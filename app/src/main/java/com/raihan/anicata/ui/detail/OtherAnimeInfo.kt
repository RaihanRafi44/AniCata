package com.raihan.anicata.ui.detail

import androidx.compose.foundation.background
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

// Warna yang mendekati gambar
//val lightGreenishBackground = Color(0xFFE8F5E9)
val specialOrange = Color(0xFFF57C00)
val tvBrown = Color(0xFFBF360C)


@Composable
fun OtherAnimeInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.background(lightGreenishBackground)
            .padding(16.dp)
    ) {
        // --- Bagian Side Story ---
        SectionHeader(title = "Side Story")
        Spacer(modifier = Modifier.height(8.dp))
        MediaItem(title = "Fullmetal Alchemist: Brotherhood Specials", tag = "SPECIAL")
        MediaItem(title = "Fullmetal Alchemist: The Sacred Star of Milos", tag = "MOVIE")

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Spin-Off ---
        SectionHeader(title = "Spin-Off")
        Spacer(modifier = Modifier.height(8.dp))
        MediaItem(title = "Fullmetal Alchemist: Brotherhood - 4-koma Gekijou", tag = "SPECIAL")

        Spacer(modifier = Modifier.height(24.dp))

        // --- Bagian Alternative Version ---
        SectionHeader(title = "Alternative Version")
        Spacer(modifier = Modifier.height(8.dp))
        MediaItem(title = "Fullmetal Alchemist", tag = "TV")
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
fun MediaItem(title: String, tag: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
        "SPECIAL", "MOVIE" -> specialOrange
        "TV" -> tvBrown
        else -> Color.Gray // Warna default jika ada tag lain
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FmaRelatedContentLayoutPreview() {
    OtherAnimeInfo()
}