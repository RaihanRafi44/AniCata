package com.raihan.anicata.ui.alllists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.raihan.anicata.data.model.anime.search.AiredSearchAnime
import com.raihan.anicata.data.model.anime.search.SearchAnime
import com.raihan.anicata.data.model.anime.top.AiredTopAnime
import com.raihan.anicata.ui.paging.PaginationControls
import com.raihan.anicata.utils.PaginationState
import java.text.NumberFormat
import java.util.Locale

private fun formatAiredDate(aired: AiredSearchAnime?): String {
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
fun AnimeAllCard(item: SearchAnime) {
    val tvTagColor = Color(0xFFF4842D)
    val epsTagColor = Color(0xFF4CAF50)
    val starColor = Color(0xFFFFC107)

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
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

            // Info Teks
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
                    text = formatAiredDate(item.aired), // Asumsi fungsi ini ada
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
                    Tag(text = item.type ?: "TV", backgroundColor = tvTagColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Tag(
                        text = "${item.episodes ?: '?'} eps",
                        backgroundColor = epsTagColor
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = starColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, "%.2f", (item.score ?: 0.0).toFloat()),
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

/*@Composable
fun AnimeListLayout(
    animeList: List<SearchAnime>,
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String,
    paginationState: PaginationState
    // 2. 'onPageChange' dihapus dari parameter
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF0F0F0))
    ) {
        // Box untuk list, loading, atau error
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (isError) {
                // 2. TAMPILKAN PESAN ERROR YANG LEBIH BAIK
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Gagal memuat data",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorMessage, // <-- Tampilkan pesan error dari ViewModel
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = animeList,
                        key = { it.id }
                    ) { anime ->
                        AnimeAllCard(item = anime)
                    }
                }
            }
        }

        // 3. Panggil PagingControls dari file 'PagingControls.kt'
        if (!isLoading && !isError && paginationState.totalPages > 1) {
            PaginationControls(
                currentPage = paginationState.currentPage,
                startPage = paginationState.startPage,
                totalPages = paginationState.totalPages,
                onPageChange = { newPage ->
                    // Panggil fungsi 'onPageChange' dari PaginationState
                    paginationState.onPageChange(newPage)
                },
                visiblePages = paginationState.visiblePages
            )
        }
    }
}*/
@Composable
fun AnimeListLayout(
    // 1. HAPUS SEMUA PARAMETER KECUALI animeList
    animeList: List<SearchAnime>
) {
    // 2. GANTI root menjadi Column (BUKAN LazyColumn)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            // 3. Pindahkan padding dari LazyColumn ke sini
            .padding(horizontal = 16.dp, vertical = 16.dp),
        // 4. Pindahkan verticalArrangement dari LazyColumn ke sini
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 5. GANTI 'items' menjadi 'forEach'
        animeList.forEach { anime ->
            AnimeAllCard(item = anime)
        }
    }
}

@Composable
fun ErrorStateContent(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Gagal memuat data",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
