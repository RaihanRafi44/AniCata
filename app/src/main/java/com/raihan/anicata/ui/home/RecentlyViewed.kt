package com.raihan.anicata.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.utils.ResultWrapper

@Composable
fun RecentlyViewedSection(
    state: ResultWrapper<List<RecentlyViewed>>,
    modifier: Modifier = Modifier,
    onItemClick: (Int, String) -> Unit
) {
    val list = (state as? ResultWrapper.Success)?.payload ?: emptyList()
    if (state is ResultWrapper.Success && list.isEmpty()) return

    Column(modifier = modifier) {
        HomeSectionHeader(
            title = "Recently Viewed",
            onViewAllClick = null
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            is ResultWrapper.Loading -> {
                HomeSectionShimmer()
            }
            is ResultWrapper.Error -> {
                Text(
                    text = "Failed to load history",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Start)
                )
            }
            is ResultWrapper.Empty -> {
                Text(
                    text = "No content viewed yet",
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.Start)
                )
            }
            is ResultWrapper.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(list) { item ->
                        ItemCard(
                            item = item,
                            onClick = {
                                if (item.id != null) {
                                    onItemClick(item.id, item.type ?: "")
                                }
                            }
                        )
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun ItemCard(
    item: RecentlyViewed,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(130.dp)
            .clickable { onClick() }
    ) {
        Surface(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFE0E0E0)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                item.type?.let { type ->
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF006A60),
                        contentColor = Color.White
                    ) {
                        Text(
                            text = type.uppercase(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.title ?: "Unknown Title",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF191C1E),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
