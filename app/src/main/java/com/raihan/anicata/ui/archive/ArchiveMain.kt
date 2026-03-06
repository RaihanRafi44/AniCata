package com.raihan.anicata.ui.archive

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.R
import org.koin.dsl.module

@Composable
fun ArchiveMainLayout(
    onAnimeClicked: () -> Unit,
    onMangaClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFE6F5F3)) // Latar belakang mint muda
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Archive",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card navigasi "Anime"
        ArchiveCategoryCard(
            title = "Anime",
            imageRes = R.drawable.img_anime1, // GANTI INI
            onClick = onAnimeClicked, // Teruskan aksi klik
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Card navigasi "Manga"
        ArchiveCategoryCard(
            title = "Manga",
            imageRes = R.drawable.img_manga1, // GANTI INI
            onClick = onMangaClicked, // Teruskan aksi klik
            modifier = Modifier.fillMaxWidth()
        )
    }
}
