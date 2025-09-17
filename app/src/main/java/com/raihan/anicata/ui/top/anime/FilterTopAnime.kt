package com.raihan.anicata.ui.top.anime

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
 * Layout utama yang menampilkan judul dan dropdown untuk Top Anime.
 */
@Composable
fun FilterTopAnime() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color(0xFFE6F5F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Top Anime",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Memanggil dropdown dari file SharedComponents.kt
        StyledDropdownFromFilter(
            options = listOf("All Anime", "Top Airing", "Top Upcoming", "Most Popular"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterTopAnimePreview() {
    FilterTopAnime()
}