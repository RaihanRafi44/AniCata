package com.raihan.anicata.ui.detail.anime

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.raihan.anicata.R
import com.raihan.anicata.data.model.anime.full.TrailerAnime

@Composable
fun VideoPromoSection(
    trailer: TrailerAnime?,
    modifier: Modifier = Modifier
) {
    val videoId = trailer?.youtubeId
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        SectionHeaderTitle(
            title = "PV & Trailer",
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (!videoId.isNullOrBlank()) {
            VideoPlaceholder(
                title = "Official Trailer",
                youtubeId = videoId,
                onPlay = {
                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
                    try {
                        context.startActivity(appIntent)
                    } catch (e: Exception) {
                        context.startActivity(webIntent)
                    }
                }
            )
        } else {
            Text(
                text = "No trailer available",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun VideoPlaceholder(
    title: String,
    youtubeId: String,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .aspectRatio(16f / 9f)
                .background(Color.Black)
                .clickable { onPlay() },
            contentAlignment = Alignment.Center
        ) {
            val thumbnailUrl = "https://i.ytimg.com/vi/$youtubeId/sddefault.jpg"
            SubcomposeAsyncImage(
                model = thumbnailUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.dp),
                            color = Color(0xFF006A60)
                        )
                    }
                },
                success = { successState ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = successState.painter,
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.85f)
                                        ),
                                        startY = 150f
                                    )
                                )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_play_youtube),
                            contentDescription = "Play Icon",
                            modifier = Modifier.size(56.dp)
                        )

                        Text(
                            text = title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        )
                    }
                }
            )
        }
    }
}
