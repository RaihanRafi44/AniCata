package com.raihan.anicata.ui.home

import com.raihan.anicata.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NewsItem(
    val imageRes: Int,
    val title: String,
    val date: String
)

@Composable
fun NewsUpdateScreen() {
    val newsList = listOf(
        NewsItem(R.drawable.img_banner1, "Sekiro: No Defeat Anime Based on Sekiro: Shadows Die Twice Game Announced", "20 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Kureha's Bride of the Barrier Master Light Novels Get TV Anime", "19 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "A Livid Lady’s Guide to Getting Even Novels Get Anime", "18 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Attack on Titan Final Special Event Announced", "17 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Solo Leveling Season 2 Confirmed for 2026", "16 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "One Piece Episode Break Next Week", "15 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Demon Slayer Movie Breaking Records", "14 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Naruto Remake Rumors Circulating Online", "13 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Spy x Family New Season Teaser Released", "12 Aug 2025"),
        NewsItem(R.drawable.img_banner1, "Jujutsu Kaisen Manga Reaches Final Arc", "11 Aug 2025")
    )

    Column(modifier = Modifier.padding(12.dp)) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "News Update",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Expand",
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // List News
        /*LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(newsList) { news ->
                NewsItemView(news)
            }
        }*/
        // List News pakai Column
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            newsList.forEach { news ->
                NewsItemView(news)
            }
        }
    }
}

@Composable
fun NewsItemView(news: NewsItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.Top // sejajar dengan atas gambar
    ) {
        Image(
            painter = painterResource(id = news.imageRes),
            contentDescription = news.title,
            modifier = Modifier
                .width(100.dp)   // lebih lebar (landscape)
                .height(80.dp)
                //.size(80.dp)
                .clip(RoundedCornerShape(8.dp)) // rounded corner
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)), // border hitam
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp) // jarak antara gambar dan teks
        ) {
            Text(
                text = news.title,
                fontSize = 14.sp,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(6.dp)) // jarak judul ↔ tanggal
            Text(
                text = news.date,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun NewsUpdatePreview() {
    NewsUpdateScreen()
}

