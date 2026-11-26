package com.raihan.anicata.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.raihan.anicata.utils.ResultWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

data class BannerData(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val genres: String,
    val synopsis: String,
    val type: String
)

@Composable
fun BannerSlider(
    state: ResultWrapper<List<BannerData>>,
    onBannerClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 18.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF006A60))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Trending Season Now 🔥",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF191C1E)
            )
        }

        when (state) {
            is ResultWrapper.Loading -> {
                BannerShimmer()
            }
            is ResultWrapper.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Failed to load banner. Please try again later.",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
            is ResultWrapper.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No trending anime right now.",
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
            is ResultWrapper.Success -> {
                val banners = state.payload ?: emptyList()

                if (banners.isEmpty()) {
                    Spacer(modifier = Modifier.height(260.dp))
                    return
                }

                val pageCount = banners.size
                val initialPage = Int.MAX_VALUE / 2
                val pagerState = rememberPagerState(
                    initialPage = initialPage,
                    pageCount = { Int.MAX_VALUE }
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    while (true) {
                        snapshotFlow { pagerState.isScrollInProgress }
                            .filter { !it }
                            .first()
                        delay(3500L)
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
                        .height(260.dp)
                ) { page ->
                    val pageIndex = (page - initialPage).mod(pageCount).let { if (it < 0) it + pageCount else it }

                    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    val scale = lerp(
                        start = 0.90f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    )
                    val alpha = lerp(
                        start = 0.6f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    )

                    BannerItem(
                        banner = banners[pageIndex],
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                            }
                            .clickable { onBannerClick(banners[pageIndex].id) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

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
            else -> {}
        }
    }
}

@Composable
fun BannerItem(banner: BannerData, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1E1E1E)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = banner.imageUrl,
                contentDescription = banner.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.95f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF006A60),
                    contentColor = Color.White
                ) {
                    Text(
                        text = banner.type.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = banner.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (banner.genres.isNotBlank() && banner.genres != "N/A") {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = banner.genres,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (banner.synopsis.isNotBlank() && banner.synopsis != "No synopsis available.") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = banner.synopsis,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp,
                        lineHeight = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun BannerIndicator(
    pageCount: Int,
    currentPage: Int,
    onDotClick: (Int) -> Unit,
    indicatorHeight: Dp = 8.dp,
    indicatorWidth: Dp = 8.dp,
    indicatorSelectedWidth: Dp = 20.dp,
    selectedColor: Color = Color(0xFF006A60),
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
                    .padding(horizontal = 4.dp)
                    .height(indicatorHeight)
                    .width(animatedWidth)
                    .clip(RoundedCornerShape(50))
                    .background(animatedColor)
                    .clickable { onDotClick(index) }
            )
        }
    }
}
