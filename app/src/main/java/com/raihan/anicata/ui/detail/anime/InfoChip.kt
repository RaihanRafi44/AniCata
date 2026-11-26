package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class ChipPalette(
    val bg: Color,
    val text: Color,
    val border: Color
)

private fun getGenrePalette(genre: String): ChipPalette {
    val name = genre.lowercase()
    return when {
        name.contains("action") || name.contains("adventure") || name.contains("martial") ->
            ChipPalette(Color(0xFFFFEBEE), Color(0xFFC62828), Color(0xFFFFCDD2))

        name.contains("comedy") || name.contains("parody") || name.contains("gag") ->
            ChipPalette(Color(0xFFFFF8E1), Color(0xFFE65100), Color(0xFFFFECB3))

        name.contains("fantasy") || name.contains("supernatural") || name.contains("magic") || name.contains("isekai") ->
            ChipPalette(Color(0xFFF3E5F5), Color(0xFF6A1B9A), Color(0xFFE1BEE7))

        name.contains("romance") || name.contains("shoujo") || name.contains("josei") ->
            ChipPalette(Color(0xFFFCE4EC), Color(0xFFC2185B), Color(0xFFF8BBD0))

        name.contains("sci-fi") || name.contains("mecha") || name.contains("cyberpunk") || name.contains("space") ->
            ChipPalette(Color(0xFFE0F7FA), Color(0xFF00838F), Color(0xFFB2EBF2))

        name.contains("mystery") || name.contains("horror") || name.contains("psychological") || name.contains("thriller") ->
            ChipPalette(Color(0xFFE8EAF6), Color(0xFF283593), Color(0xFFC5CAE9))

        name.contains("slice") || name.contains("school") || name.contains("sports") || name.contains("iyashikei") ->
            ChipPalette(Color(0xFFE8F5E9), Color(0xFF2E7D32), Color(0xFFC8E6C9))

        name.contains("music") || name.contains("idol") ->
            ChipPalette(Color(0xFFFFF3E0), Color(0xFFEF6C00), Color(0xFFFFE0B2))

        else ->
            ChipPalette(Color(0xFFE0F2F1), Color(0xFF006A60), Color(0xFFB2DFDB))
    }
}

@Composable
fun InfoChip(text: String) {
    val palette = getGenrePalette(text)

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = palette.bg,
        border = BorderStroke(1.dp, palette.border)
    ) {
        Text(
            text = text,
            color = palette.text,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
        )
    }
}
