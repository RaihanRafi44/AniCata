package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raihan.anicata.R
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.utils.dropShadow

private fun getTypeBadgeColor(type: String): Color {
    return when (type.uppercase()) {
        "TV", "MANGA" -> Color(0xFF3F51B5)
        "MOVIE" -> Color(0xFFE53935)
        "SPECIAL", "OVA", "ONA" -> Color(0xFF8E24AA)
        "NOVEL", "LIGHT NOVEL" -> Color(0xFFF57C00)
        else -> Color(0xFF00897B)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeaderSection(
    animeData: AnimeData,
    onBackClick: (() -> Unit)? = null
) {
    val canvasBg = Color(0xFFF4F6F6)

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- Backdrop Image & Gradient Blend ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
        ) {
            AsyncImage(
                model = animeData.images.jpg.largeImageUrl ?: animeData.images.jpg.imageUrl,
                contentDescription = "Header Background",
                error = painterResource(id = R.drawable.img_banner1),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                contentScale = ContentScale.Crop
            )

            // Deep Vignette Gradient Blend
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.55f),
                                Color.Black.copy(alpha = 0.40f),
                                Color.Black.copy(alpha = 0.65f),
                                canvasBg.copy(alpha = 0.90f),
                                canvasBg
                            )
                        )
                    )
            )

            // Floating Frosted Back Button
            onBackClick?.let { onBack ->
                Surface(
                    onClick = onBack,
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.45f),
                    contentColor = Color.White,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(14.dp)
                        .size(42.dp)
                        .align(Alignment.TopStart)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // --- Overlapping Responsive Poster & Badges Showcase ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.dropShadow(
                    color = Color.Black,
                    alpha = 0.35f,
                    shadowRadius = 18.dp,
                    offsetY = 6.dp,
                    cornerRadius = 16.dp
                )
            ) {
                Box {
                    AsyncImage(
                        model = animeData.images.jpg.largeImageUrl ?: animeData.images.jpg.imageUrl,
                        contentDescription = animeData.title,
                        error = painterResource(id = R.drawable.img_poster),
                        modifier = Modifier
                            .width(145.dp)
                            .height(210.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Type Badge
                    animeData.type?.let { type ->
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = getTypeBadgeColor(type),
                            contentColor = Color.White
                        ) {
                            Text(
                                text = type.uppercase(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                            )
                        }
                    }

                    // Status Badge
                    animeData.status?.let { status ->
                        val isAiring = status.contains("Currently", ignoreCase = true) || animeData.airing
                        val statusBg = if (isAiring) Color(0xFF2E7D32) else Color(0xFF455A64)
                        val statusText = if (isAiring) "AIRING" else "FINISHED"

                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = statusBg,
                            contentColor = Color.White
                        ) {
                            Text(
                                text = statusText,
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Title
            Text(
                text = animeData.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF191C1E),
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            // Japanese / English Subtitle
            val altTitle = animeData.titleEnglish ?: animeData.titleJapanese
            if (!altTitle.isNullOrBlank() && !altTitle.equals(animeData.title, ignoreCase = true)) {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = altTitle,
                    fontSize = 12.5.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Quick Info Tags Pill Row
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val season = animeData.season.orEmpty()
                val year = animeData.year ?: 0
                if (season.isNotBlank() || year != 0) {
                    val seasonText = season.replaceFirstChar { it.titlecase() }
                    val yearText = if (year != 0) year.toString() else ""
                    HeaderChip(text = "$seasonText $yearText".trim())
                }

                animeData.rating?.let { rating ->
                    val shortRating = rating.split(" ").firstOrNull() ?: rating
                    HeaderChip(text = shortRating)
                }

                animeData.source?.let { source ->
                    HeaderChip(text = source)
                }
            }
        }
    }
}

@Composable
private fun HeaderChip(text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFE0F2F1),
        contentColor = Color(0xFF006A60)
    ) {
        Text(
            text = text,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.5.dp)
        )
    }
}
