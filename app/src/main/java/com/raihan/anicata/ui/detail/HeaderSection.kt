package com.raihan.anicata.ui.detail

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
import com.raihan.anicata.R
import com.raihan.anicata.utils.dropShadow

/*
@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.height(320.dp), // Total height for the header area
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- Container for Background Image and Gradient ---
        Box(
            modifier = Modifier
                // This clips the content (Image and Gradient) inside it
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
        ) {
            // 1. Background Image
            Image(
                painter = painterResource(id = R.drawable.img_banner1), // Ganti dengan gambar background Anda
                contentDescription = "Header Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), // Adjusted height
                contentScale = ContentScale.Crop
            )

            // 2. Darker Gradient Overlay
            Box(
                modifier = Modifier
                    .matchParentSize() // Matches the size of the Image
                    .background(
                        Brush.verticalGradient(
                            // Starts with semi-transparent black and ends with almost opaque black
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }

        // 3. Overlapping Poster with Shadow
        Box(
            modifier = Modifier.offset(y = 100.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // --- Shadow Custom ---
            */
/*Box(
                modifier = Modifier
                    .width(200.dp)   // lebih lebar dari poster
                    .height(50.dp)   // tipis (oval horizontal)
                    .align(Alignment.BottomCenter)
                    .blur(40.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.35f),
                                Color.Transparent
                            ),
                            radius = 300f
                        ),
                        shape = CircleShape // pakai circle biar benar-benar oval
                    )
            )*//*


            // Poster
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp), // aktifkan elevation
                colors = CardDefaults.cardColors(containerColor = Color.Transparent), // biar transparan
                modifier = Modifier
                    .dropShadow( // custom drop shadow tetap bisa dipakai
                        color = Color.Black,
                        alpha = 0.4f,
                        shadowRadius = 24.dp,
                        offsetY = 8.dp,
                        cornerRadius = 12.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_poster),
                    contentDescription = "Anime Poster",
                    modifier = Modifier
                        .width(150.dp)
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }


    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HeaderSectionPreview() {
    // We add a Column just for preview purposes to see how it looks
    // against a white background, simulating the main content area.
    Column {
        HeaderSection()
        // Spacer to see the rounded bottom clearly
        Spacer(modifier = Modifier.height(200.dp))
    }
}*/

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            // Biarkan tingginya otomatis menyesuaikan konten yang sekarang ukurannya sudah benar
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- Container for Background Image and Gradient ---
        Box(
            /*modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))*/
        ) {
            // 1. Background Image
            Image(
                painter = painterResource(id = R.drawable.img_banner1),
                contentDescription = "Header Background",
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
            Image(
                painter = painterResource(id = R.drawable.img_poster),
                contentDescription = "Anime Poster",
                modifier = Modifier
                    .width(150.dp)
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HeaderSectionPreview() {
    Column {
        HeaderSection()
        // Sekarang, meskipun tidak ada Spacer, poster tidak akan terpotong
        // karena HeaderSection sudah memiliki tinggi yang benar (sekitar 320dp).
    }
}
