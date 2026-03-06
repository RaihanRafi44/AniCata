package com.raihan.anicata.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.raihan.anicata.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue

data class BannerData(
    val id: Int, // <-- Tambahkan ID
    val imageUrl: String?, // <-- Hapus nullable
    val title: String,
    val genres: String, // <-- Ubah dari 'genre'
    val synopsis: String,
    val type: String // <-- Tambahkan Tipe (TV, Movie, dll)
)

@Composable
fun BannerSlider(
    // --- 2. UBAH PARAMETER ---
    banners: List<BannerData>,
    onBannerClick: (Int) -> Unit // <-- Ubah ke (Int)
) {
    // --- 3. HAPUS DUMMY DATA (val banners = ...) ---

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Trending Season Now\uD83D\uDD25",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        )

        // --- 4. TAMBAHKAN SAFETY CHECK ---
        // Ini PENTING agar pager "tak terbatas" Anda tidak crash jika list kosong
        if (banners.isEmpty()) {
            // Tampilkan placeholder atau biarkan kosong
            Spacer(modifier = Modifier.height(280.dp)) // Menjaga layout
            return
        }

        // --- LOGIKA INFINITE PAGER (Tidak Berubah) ---
        val pageCount = banners.size // Sekarang dinamis
        val initialPage = Int.MAX_VALUE / 2
        val pagerState = rememberPagerState(
            initialPage = initialPage,
            pageCount = { Int.MAX_VALUE }
        )
        val coroutineScope = rememberCoroutineScope()

        // --- AUTO-SCROLL (Tidak Berubah) ---
        LaunchedEffect(Unit) {
            while (true) {
                snapshotFlow { pagerState.isScrollInProgress }
                    .filter { !it }
                    .first()
                delay(3000L)
                if (!pagerState.isScrollInProgress) {
                    val currentPage = pagerState.currentPage
                    val nextPage = currentPage + 1
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) { page ->
            // Konversi (Tidak Berubah)
            val pageIndex = (page - initialPage).mod(pageCount).let { if (it < 0) it + pageCount else it }

            // Efek Carousel (Tidak Berubah)
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )
            val alpha = lerp(
                start = 0.5f,
                stop = 1f,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )

            BannerItem(
                banner = banners[pageIndex],
                modifier = Modifier
                    .padding(horizontal = 0.2.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    // --- 5. PERBARUI CLICKABLE ---
                    .clickable { onBannerClick(banners[pageIndex].id) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- INDIKATOR (Tidak Berubah, sudah dinamis) ---
        BannerIndicator(
            pageCount = pageCount,
            currentPage = (pagerState.currentPage - initialPage).mod(pageCount).let { if (it < 0) it + pageCount else it },
            onDotClick = { index ->
                val targetPageOffset = index - (pagerState.currentPage - initialPage).mod(pageCount)
                val targetPage = pagerState.currentPage + targetPageOffset
                coroutineScope.launch { pagerState.animateScrollToPage(targetPage) }
            }
        )
    }
}

@Composable
fun BannerItem(banner: BannerData, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        // --- 6. HANYA GUNAKAN AsyncImage ---
        AsyncImage(
            model = banner.imageUrl, // <-- Gunakan data
            contentDescription = banner.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 1.0f)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                // --- 7. GUNAKAN DATA DINAMIS ---
                Text(
                    text = banner.type.uppercase(), // <-- Gunakan type
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color(0xFFFF9800), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = banner.title, // <-- Gunakan title
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Genres : ${banner.genres}", // <-- Gunakan genres
                    color = Color.White,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Synopsis : ${banner.synopsis}", // <-- Gunakan synopsis
                    color = Color.White,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    softWrap = true,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Composable BannerIndicator tidak perlu diubah
@Composable
fun BannerIndicator(
    pageCount: Int,
    currentPage: Int,
    onDotClick: (Int) -> Unit,
    indicatorHeight: Dp = 12.dp,
    indicatorWidth: Dp = 12.dp,
    indicatorSelectedWidth: Dp = 24.dp,
    selectedColor: Color = Color(0xFFCE93D8),
    unselectedColor: Color = Color(0xFFDCDCDC)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (index in 0 until pageCount) {
            val isSelected = index == currentPage

            val animatedWidth by animateDpAsState(
                targetValue = if (isSelected) indicatorSelectedWidth else indicatorWidth,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
                label = "widthAnimation"
            )
            val animatedColor by animateColorAsState(
                targetValue = if (isSelected) selectedColor else unselectedColor,
                animationSpec = tween(durationMillis = 180),
                label = "colorAnimation"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .height(indicatorHeight)
                    .width(animatedWidth)
                    .clip(RoundedCornerShape(50))
                    .background(animatedColor)
                    .clickable { onDotClick(index) }
            )
        }
    }
}
