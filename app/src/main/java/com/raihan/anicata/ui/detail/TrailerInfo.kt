package com.raihan.anicata.ui.detail

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
import java.util.regex.Pattern

/*
@Composable
fun VideoPromoSection(modifier: Modifier = Modifier) {
    // Column sebagai container utama dengan latar belakang dan padding
    Column(
        modifier = modifier
            //.background(color = lightMintGreen, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        // Judul utama
        Text(
            text = "PV and Trailer",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Placeholder untuk video pertama
        VideoPlaceholder(
            title = "PV (Blu-ray box ver.)",
            youtubeUrl = "https://www.youtube.com/watch?v=nfK6UgLraZA" // Ganti dengan link Anda
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Placeholder untuk video kedua
        VideoPlaceholder(
            title = "Trailer",
            youtubeUrl = "https://youtu.be/zJ6fDGmDe6U?si=9ukDFc6n8uxzNqpH" // Ganti dengan link Anda
        )
    }
}

*/
/**
 * Composable privat yang dapat digunakan kembali untuk setiap item video.
 * Terdiri dari kotak abu-abu (placeholder) dan teks judul di bawahnya.
 *
 * @param title Judul yang akan ditampilkan di bawah placeholder.
 * @param modifier Modifier untuk kustomisasi dari luar.
 *//*

@Composable
private fun VideoPlaceholder(
    title: String,
    youtubeUrl: String,
    modifier: Modifier = Modifier
) {
    // Dapatkan konteks saat ini untuk menjalankan Intent
    val context = LocalContext.current
    // Buat Intent untuk membuka link YouTube
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)) }
    // Dapatkan URL thumbnail dari link YouTube
    val thumbnailUrl = getYouTubeThumbnailUrl(youtubeUrl)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Kotak abu-abu sebagai placeholder video
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .clickable { context.startActivity(intent) },
            contentAlignment = Alignment.Center
        ){
            // Memuat gambar thumbnail dari URL menggunakan Coil
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "Thumbnail for $title",
                contentScale = ContentScale.Crop, // Memastikan gambar mengisi box
                modifier = Modifier.fillMaxWidth()
            )
            // Tambahkan ikon Play sebagai indikator visual
            Icon(
                imageVector = Icons.Default.PlayCircle,
                contentDescription = "Play Icon",
                modifier = Modifier.size(64.dp),
                tint = Color.White.copy(alpha = 0.8f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Teks judul di bawah placeholder
        Text(
            text = title,
            fontSize = 13.sp,
            color = Color.DarkGray
        )
    }
}

*/
/**
 * Fungsi helper untuk mendapatkan URL thumbnail kualitas tinggi dari link YouTube.
 * @param youtubeUrl Link lengkap video YouTube (e.g., "https://www.youtube.com/watch?v=VIDEO_ID").
 * @return URL gambar thumbnail.
 *//*

private fun getYouTubeThumbnailUrl(youtubeUrl: String): String {
    val videoId = youtubeUrl.split("v=").getOrNull(1)?.substringBefore('&')
    return "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
}


*/
/**
 * Fungsi Preview untuk melihat tampilan komponen di Android Studio
 * tanpa harus menjalankan aplikasi secara keseluruhan.
 *//*

@Preview(showBackground = true)
@Composable
fun VideoPromoSectionPreview() {
    // Memberi padding pada preview untuk menunjukkan bahwa
    // komponen tidak full-screen
    VideoPromoSection()
}*/

/*@Composable
fun VideoPromoSection(modifier: Modifier = Modifier) {
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

        // >> PERBAIKAN 1: Menggunakan link YouTube yang valid sebagai contoh
        VideoPlaceholder(
            title = "PV (Blu-ray box ver.)",
            youtubeUrl = "https://youtu.be/omUQ53VQwUw?si=_g7TfoPS0SITCbRP" // Contoh link valid
        )

        Spacer(modifier = Modifier.height(24.dp))

        VideoPlaceholder(
            title = "Trailer",
            youtubeUrl = "https://youtu.be/zJ6fDGmDe6U?si=tY9d8CjeRRTZOzUe" // Contoh link valid
        )
    }
}

@Composable
private fun VideoPlaceholder(
    title: String,
    youtubeUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)) }

    // >> PERBAIKAN 2: Menggunakan fungsi baru yang lebih andal untuk mendapatkan ID video
    val videoId = getVideoIdFromUrl(youtubeUrl)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.DarkGray)
                .clickable(enabled = videoId != null) {
                    context.startActivity(intent)
                },
            contentAlignment = Alignment.Center
        ) {
            // Cek apakah ID video valid sebelum mencoba memuat gambar
            if (videoId != null) {
                // >> PERBAIKAN 3: Gunakan SubcomposeAsyncImage untuk handle loading/error state
                val thumbnailUrl = "https://i.ytimg.com/vi/$videoId/hqdefault.jpg"
                SubcomposeAsyncImage(
                    model = thumbnailUrl,
                    contentDescription = "Thumbnail for $title",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                    loading = {
                        // Tampilan saat gambar sedang dimuat
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    },
                    error = {
                        // Tampilan jika gambar gagal dimuat
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = "Error",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    },
                    success = {
                        // Tampilkan gambar dan ikon play jika berhasil
                        it.painter.apply {
                            AsyncImage(
                                model = thumbnailUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Play Icon",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }
                )
            } else {
                // Tampilan jika URL YouTube tidak valid
                Icon(
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = "Invalid URL",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            fontSize = 13.sp,
            color = Color.DarkGray
        )
    }
}

// >> PERBAIKAN 4: Fungsi helper yang jauh lebih andal untuk mengekstrak ID video
private fun getVideoIdFromUrl(youtubeUrl: String): String? {
    val patterns = arrayOf(
        "//www.youtube.com/embed/([^/?#&]+)",
        "//www.youtube.com/watch\\?v=([^/?#&]+)",
        "//youtu.be/([^/?#&]+)",
        "//www.youtube.com/v/([^/?#&]+)"
    )
    patterns.forEach { pattern ->
        val compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE)
        val matcher = compiledPattern.matcher(youtubeUrl)
        if (matcher.find()) {
            return matcher.group(1)
        }
    }
    return null // Kembalikan null jika tidak ada ID yang ditemukan
}

@Preview(showBackground = true)
@Composable
fun VideoPromoSectionPreview() {
    VideoPromoSection()
}*/

data class VideoItem(
    val title: String,
    val youtubeUrl: String
)

@Composable
fun VideoPromoSection(modifier: Modifier = Modifier) {
    val videos = remember {
        listOf(
            VideoItem(
                title = "PV (Blu-ray box ver.)",
                youtubeUrl = "https://youtu.be/EPaoHkV0dYw?si=EW2LpdRTSBGwu3ET"
            ),
            VideoItem(
                title = "Trailer",
                youtubeUrl = "https://youtu.be/4ZKgq7Aw34s?si=5JyUMys4am6n4K2B"
            )
        )
    }

    // >> PERBAIKAN 2.1: State sekarang menyimpan Set (kumpulan) dari ID video <<
    var playingVideoIds by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            //.padding(bottom = 16.dp)
    ) {
        Text(
            text = "PV and Trailer",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        videos.forEach { videoItem ->
            val videoId = getVideoIdFromUrl(videoItem.youtubeUrl)

            // >> PERBAIKAN 2.2: Cek apakah ID video ada di dalam Set <<
            if (videoId != null && playingVideoIds.contains(videoId)) {
                YoutubePlayer(youtubeVideoId = videoId)
            } else {
                VideoPlaceholder(
                    title = videoItem.title,
                    youtubeUrl = videoItem.youtubeUrl,
                    onPlay = { id ->
                        // >> PERBAIKAN 2.3: Menambahkan ID ke Set, bukan mengganti <<
                        playingVideoIds = playingVideoIds + id
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VideoPlaceholder(
    title: String,
    youtubeUrl: String,
    onPlay: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val videoId = getVideoIdFromUrl(youtubeUrl)

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
                .clickable(enabled = videoId != null) { onPlay(videoId!!) },
            contentAlignment = Alignment.Center
        ) {
            if (videoId != null) {
                val thumbnailUrl = "https://i.ytimg.com/vi/$videoId/sddefault.jpg"
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
    VideoPromoSection()
}