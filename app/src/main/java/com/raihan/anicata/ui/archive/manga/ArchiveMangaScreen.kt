package com.raihan.anicata.ui.archive.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.R
import com.raihan.anicata.ui.archive.ArchiveDetailCard
import com.raihan.anicata.ui.archive.SmallArchiveHeaderCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveMangaScreen(
    onNavigateBack: () -> Unit,
    onBookmarkClicked: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manga Archive") }, // Judul diubah
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE6F5F3)
                )
            )
        },
        containerColor = Color(0xFFE6F5F3)
    ) { innerPadding ->
        ArchiveMangaLayout(
            modifier = Modifier.padding(innerPadding),
            onBookmarkClicked = onBookmarkClicked,
            onFavoriteClicked = onFavoriteClicked
        )
    }*/

    ArchiveMangaLayout(
        onNavigateBack = onNavigateBack,
        onBookmarkClicked = onBookmarkClicked,
        onFavoriteClicked = onFavoriteClicked
    )
}

/**
 * Layout untuk Halaman Arsip Manga.
 */
@Composable
fun ArchiveMangaLayout(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onBookmarkClicked: () -> Unit,
    onFavoriteClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Archive",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        SmallArchiveHeaderCard(
            title = "Manga",
            imageRes = R.drawable.img_manga1, // GANTI INI
            onClick = onNavigateBack, // Klik card ini untuk kembali
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card untuk "My Bookmark" (Manga)
        ArchiveDetailCard(
            title = "My Bookmark",
            imageRes = R.drawable.img_banner1, // GANTI INI
            onClick = onBookmarkClicked,
            overlayColor = Color(0xFF0077FF).copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Card untuk "My Favorite" (Manga)
        ArchiveDetailCard(
            title = "My Favorite",
            imageRes = R.drawable.img_infinity_castle, // GANTI INI
            onClick = onFavoriteClicked,
            overlayColor = Color(0xFFEA387C).copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArchiveMangaScreenPreview() {
    ArchiveMangaScreen(
        onNavigateBack = {},
        onBookmarkClicked = {},
        onFavoriteClicked = {}
    )
}