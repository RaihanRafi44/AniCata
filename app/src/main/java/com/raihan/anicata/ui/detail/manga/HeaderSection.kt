package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.raihan.anicata.R
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.utils.dropShadow


@Composable
fun MangaHeaderSection(
    mangaData: MangaDetailFull
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Biarkan tingginya otomatis menyesuaikan konten yang sekarang ukurannya sudah benar
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- Container for Background Image and Gradient ---
        Box{
            // 1. Background Image
            AsyncImage(
                model = mangaData.images.jpg.largeImageUrl,
                contentDescription = "Header Background",
                error = painterResource(id = R.drawable.img_banner1),
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(250.dp),
                    .aspectRatio(16f / 10f),
                contentScale = ContentScale.Crop
            )

            // 2. Darker Gradient Overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
                    .border(
                        width = 0.2.dp,
                        color = Color.Black,
                        //shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
            )
        }

        // 3. Overlapping Poster with Shadow
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                // --- PERUBAHAN UTAMA DI SINI ---
                // Ganti offset dengan padding. Ini akan membuat Box parent "tahu"
                // bahwa ada ruang ekstra yang dibutuhkan di bagian atas.
                .padding(top = 100.dp)
                .dropShadow(
                    color = Color.Black,
                    alpha = 0.4f,
                    shadowRadius = 24.dp,
                    offsetY = 8.dp,
                    cornerRadius = 12.dp
                )
        ) {
            AsyncImage(
                model = mangaData.images.jpg.imageUrl, //
                contentDescription = "Manga Poster",
                error = painterResource(id = R.drawable.img_poster),
                modifier = Modifier
                    .width(150.dp)
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
