package com.raihan.anicata.ui.detail.manga

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
import com.raihan.anicata.data.model.manga.full.MangaDetailFull

@Composable
fun MangaSynopsisInfo(
    modifier: Modifier = Modifier,
    mangaData: MangaDetailFull // <-- 2. Terima data MangaDetailFull
) {
    // 3. Gunakan sinopsis dari mangaData
    val synopsisText = mangaData.synopsis //
    val textColor = Color(0xFF333333) //
    val dividerColor = Color.Gray.copy(alpha = 0.5f) //

    Column(
        modifier = modifier // Modifier ini membuat komponen fleksibel
            //.background(color = backgroundColor)
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
            text = if (synopsisText.isNullOrBlank()) "No synopsis available." else synopsisText,
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

}