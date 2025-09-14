package com.raihan.anicata.ui.detail

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.R

@Composable
fun DetailScreen() {
    // 1. Buat Column yang bisa di-scroll untuk seluruh layar
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 2. Tampilkan HeaderSection di bagian atas
        HeaderSection()

        // 3. Tambahkan konten detail lainnya di bawah header
        // Kita bisa gunakan lagi komponen dari kode DetailScreen Anda sebelumnya
        // seperti InfoRow(), ActionButtons(), dan DetailsSection()
        // (Untuk sekarang, kita beri placeholder dulu atau langsung copy-paste)

        Spacer(modifier = Modifier.height(16.dp))
        // InfoRow() // <-- Anda bisa tambahkan ini nanti
        // ActionButtons() // <-- Anda bisa tambahkan ini nanti
        // DetailsSection() // <-- Anda bisa tambahkan ini nanti
        AnimeInfoStats()

        //Spacer(modifier = Modifier.height(2.dp))

        ActionButtonsGroup()

        Spacer(modifier = Modifier.height(8.dp))

        AnimeDetailsScreen()

        SynopsisInfo()

        CharacterListLayout(characterDataList)

        StaffInfo()

        VideoPromoSection()

        RelatedInfo()

        OtherAnimeInfo()

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen()
}