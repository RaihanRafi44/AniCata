package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.media.MediaItem
import com.raihan.anicata.utils.ResultWrapper
import java.text.NumberFormat
import java.util.Locale

// Composable SearchResultCard tidak berubah, sudah benar
@Composable
fun SearchResultCard(
    item: MediaItem,
    onClick: () -> Unit
) {
    // Tentukan warna tag berdasarkan itemType
    val typeTagColor = when (item.itemType.lowercase()) {
        //"anime" -> Color(0xFFF4842D)
        "manga" -> Color(0xFF2DB8F4)
        "manhwa" -> Color(0xFF2DB8F4)
        "manhua" -> Color(0xFF2DB8F4)
        "novel" -> Color(0xFF2DB8F4)
        "light novel" -> Color(0xFF2DB8F4)
        "doujin" -> Color(0xFF2DB8F4)
        "oneshot" -> Color(0xFF2DB8F4)
        //"movie" -> Color(0xFF8B2DF4)
        else -> Color(0xFFF4842D)
    }
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Bagian Kanan: Informasi Teks
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
                Text(
                    text = item.airedOrPublished,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = "Members",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val formatter = NumberFormat.getInstance(Locale.US)
                    Text(
                        text = formatter.format(item.members ?: 0),
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Tag(text = item.itemType, backgroundColor = typeTagColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Tag(text = item.itemDetails, backgroundColor = epsTagColor)
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = starColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, "%.2f", item.score ?: 0.0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

// Composable Tag tidak berubah
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
fun SearchResultListLayout(
    items: List<MediaItem>, // <-- Terima HANYA list-nya
    onItemClick: (MediaItem) -> Unit
) {
    // Langsung Column untuk me-render list
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item
    ) {
        items.forEach { item ->
            SearchResultCard(
                item = item,
                onClick = { onItemClick(item) }
            )
        }
    }
}

