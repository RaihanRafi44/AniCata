package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.anime.full.AnimeData

@Composable
fun SynopsisInfo(
    modifier: Modifier = Modifier,
    animeData: AnimeData
    ) {

    val synopsisText = animeData.synopsis
    val textColor = Color(0xFF333333)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    Column(
        modifier = modifier // Modifier ini membuat komponen fleksibel
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Synopsis",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = if (synopsisText.isNullOrBlank()) "No synopsis available" else synopsisText,
            fontSize = 13.sp,
            color = textColor,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true, name = "Synopsis View Preview")
@Composable
fun SynopsisPreview() {
    // Anda perlu data palsu (fake) untuk preview
    // Untuk sementara, kita pakai teks hardcoded lagi
    val fakeSynopsis = "After a horrific alchemy experiment goes wrong..."
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Synopsis", fontWeight = FontWeight.Bold)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text(fakeSynopsis, textAlign = TextAlign.Justify)
    }
}