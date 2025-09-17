package com.raihan.anicata.ui.top.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.top.StyledDropdownFromFilter

/**
 * Layout utama yang menampilkan judul dan dropdown untuk Top Manga.
 */
@Composable
fun FilterTopManga() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color(0xFFE6F5F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top Manga",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Memanggil dropdown yang sama dari file SharedComponents.kt
        StyledDropdownFromFilter(
            options = listOf("All Manga", "Top Manhwa", "Top Manhua", "Most Popular"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterTopMangaPreview() {
    FilterTopManga()
}