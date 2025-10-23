package com.raihan.anicata.ui.top.novel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.manga.top.TopManga
import com.raihan.anicata.data.model.manga.top.TopMangaPublished
import java.text.NumberFormat
import java.util.Locale
import kotlin.collections.forEach

private fun formatAiredDate(aired: TopMangaPublished?): String {
    val from = aired?.prop?.from
    val to = aired?.prop?.to

    // Tambahkan pemeriksaan apakah bulan valid (antara 1 dan 12)
    val isFromDateValid = from?.year != null && from.month != null && from.month in 1..12

    if (!isFromDateValid) {
        return aired?.prop?.string ?: "N/A"
    }

    val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Des")

    // Kode ini sekarang aman karena kita sudah memeriksa nilai bulan di atas
    val fromMonth = monthNames[from.month - 1]
    val fromString = "$fromMonth ${from.year}"

    val isToDateValid = to?.year != null && to.month != null && to.month in 1..12
    val toString = if (isToDateValid) {
        val toMonth = monthNames[to.month - 1]
        " - $toMonth ${to.year}"
    } else {
        ""
    }

    return fromString + toString
}

@Composable
fun NovelCard(item: TopManga) {
    val tvTagColor = Color(0xFFF4842D)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f)),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            // Gambar (sama persis)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.images.jpg.largeImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Kolom Info Utama (Layout dari kotak merah + spacing dari Anda)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Kotak 1: Judul
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )

                // Kotak 2: Tanggal Tayang
                Text(
                    text = formatAiredDate(item.published), // Asumsi fungsi ini ada
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )

                // Kotak 3: Members dan Rank (Layout dari kotak merah)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian kiri (Members)
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Members",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val formatter = NumberFormat.getInstance(Locale.US)
                    Text(
                        text = formatter.format(item.members),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )

                    // Spacer untuk mendorong Rank ke kanan
                    Spacer(Modifier.weight(1f))

                    // Bagian kanan (Rank)
                    Text(
                        text = "Rank ${item.rank?.toString() ?: "?"}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }

                // Kotak 4: Tags dan Score (Layout dari kotak merah)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bagian kiri (Tags)
                    item.type?.let { Tag(text = it, backgroundColor = tvTagColor) }
                    Spacer(modifier = Modifier.width(6.dp))

                    // Spacer untuk mendorong Score ke kanan
                    Spacer(Modifier.weight(1f))

                    // Bagian kanan (Score)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = starColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, "%.2f", item.score),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun NovelListTopLayout(
    novelList: List<TopManga>,
    currentPage: Int = 1,
    pageSize: Int = 25,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        val offset = ((currentPage - 1).coerceAtLeast(0)) * pageSize
        val displayRanked: List<TopManga> = novelList.mapIndexed { index, novel ->
            novel.copy(rank = offset + index + 1)
        }

        displayRanked.forEach { novel ->
            NovelCard(item = novel)
        }
    }
}