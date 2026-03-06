package com.raihan.anicata.ui.detail.anime

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.raihan.anicata.R
import com.raihan.anicata.data.model.anime.full.TrailerAnime
import java.util.regex.Pattern

@Composable
fun VideoPromoSection(
    trailer: TrailerAnime?,
    modifier: Modifier = Modifier
) {
    // State untuk melacak apakah video sedang dimainkan
    //var isPlaying by remember { mutableStateOf(false) }

    // Ambil youtubeId dari data trailer
    val videoId = trailer?.youtubeId
    val context = LocalContext.current // <-- Dapatkan Context

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "PV and Trailer",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!videoId.isNullOrBlank()) {
            VideoPlaceholder(
                title = "Official Trailer",
                youtubeId = videoId, // videoId di sini dijamin valid
                onPlay = {
                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
                    try {
                        context.startActivity(appIntent)
                    } catch (e: Exception) {
                        context.startActivity(webIntent)
                    }
                },
                modifier = Modifier
            )
        } else {
            // Tampilkan pesan jika tidak ada trailer
            Text(
                text = "No trailer available",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), // Beri padding agar tidak terlalu mepet
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun VideoPlaceholder(
    title: String,
    youtubeId: String,
    onPlay: () -> Unit, // Diubah menjadi () -> Unit
    modifier: Modifier = Modifier
) {
    val videoId = getVideoIdFromUrl(youtubeId)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(16f / 9f)
                .background(Color.DarkGray)
                .clickable(enabled = true) { onPlay() }, // Panggil onPlay
            contentAlignment = Alignment.Center
        ) {
            val thumbnailUrl = "https://i.ytimg.com/vi/$youtubeId/sddefault.jpg"
            SubcomposeAsyncImage(
                model = thumbnailUrl, contentDescription = title,
                modifier = Modifier.fillMaxWidth(),
                loading = { CircularProgressIndicator(modifier = Modifier.size(48.dp)) },
                success = { successState ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = successState.painter, contentDescription = title,
                            contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black),
                                        startY = 300f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                        // Ganti R.drawable.ic_play_youtube dengan nama file drawable Anda
                        Image(
                            painter = painterResource(id = R.drawable.ic_play_youtube),
                            contentDescription = "Play Icon", modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 2,
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

@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val updatedVideoId by rememberUpdatedState(youtubeVideoId)

    // >> PERBAIKAN 1.1: Modifier clip sekarang diterapkan langsung ke AndroidView <<
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f) // Sesuaikan rasio jika perlu
            .clip(RoundedCornerShape(12.dp)), // Bentuk rounded
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                // >> PERBAIKAN 1.2: Properti ini membuat View mengikuti bentuk clip <<
                this.clipToOutline = true
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(updatedVideoId, 0f)
                    }
                })
            }
        }
    )
}

private fun getVideoIdFromUrl(youtubeUrl: String): String? {
    val patterns = arrayOf(
        "//www.youtube.com/embed/([^/?#&]+)", "//www.youtube.com/watch\\?v=([^/?#&]+)",
        "//youtu.be/([^/?#&]+)", "//www.youtube.com/v/([^/?#&]+)"
    )
    patterns.forEach { pattern ->
        val compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
        val matcher = compiledPattern.matcher(youtubeUrl)
        if (matcher.find()) { return matcher.group(1) }
    }
    return null
}

@Preview(showBackground = true)
@Composable
fun VideoPromoSectionPreview() {
    //VideoPromoSection()
}