package com.raihan.anicata.ui.login

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val BgDark = Color(0xFF141416)
private val CoverColor = Color(0xFF006A60)       // Brand Teal AniCata
private val CoverSpine = Color(0xFF004B44)       // Teal gelap untuk punggung buku
private val CoverBorder = Color(0xFF00897B)      // Garis pinggir sampul
private val PaperColor = Color(0xFFF4F1EA)
private val PaperShadow = Color(0xFFE2DDD2)
private val AccentGold = Color(0xFFF4B942)       // Aksen katakana & garis

@Composable
fun BookOpeningOverlay(
    username: String? = null,
    onAnimationFinished: () -> Unit
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val haptic = LocalHapticFeedback.current
    val density = LocalDensity.current.density

    var isOpening by remember { mutableStateOf(false) }

    // Inisialisasi SoundPool untuk efek suara membalik halaman
    val soundPool = remember {
        if (isPreview) null
        else {
            val attrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(attrs)
                .build()
        }
    }

    val pageFlipSoundId = remember(soundPool) {
        soundPool?.load(context, R.raw.sfx_page_flip, 1) ?: 0
    }

    DisposableEffect(Unit) {
        onDispose {
            soundPool?.release()
        }
    }

    // Animasi idle halus (hint tap berkedip pelan)
    val infiniteTransition = rememberInfiniteTransition(label = "idle_book")
    val hintAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hint_alpha"
    )

    // State animasi utama
    val headerAlpha = remember { Animatable(1f) }
    val coverRotation = remember { Animatable(0f) }
    val page1Rotation = remember { Animatable(0f) }
    val page2Rotation = remember { Animatable(0f) }
    val page3Rotation = remember { Animatable(0f) }

    val cameraScale = remember { Animatable(1f) }
    val cameraOffsetX = remember { Animatable(0f) }
    val overlayAlpha = remember { Animatable(1f) }

    LaunchedEffect(isOpening) {
        if (!isOpening) return@LaunchedEffect

        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

        // 1. Sembunyikan teks header & hint
        launch {
            headerAlpha.animateTo(0f, tween(220))
        }

        // 2. Sampul buku terbuka (rotasi 3D ke kiri)
        launch {
            coverRotation.animateTo(
                targetValue = -165f,
                animationSpec = tween(durationMillis = 520, easing = FastOutSlowInEasing)
            )
        }

        // 3. Lembaran kertas membalik berurutan + SFX
        delay(160)
        soundPool?.play(pageFlipSoundId, 0.8f, 0.8f, 1, 0, 1.0f)
        launch {
            page1Rotation.animateTo(
                targetValue = -162f,
                animationSpec = tween(durationMillis = 440, easing = FastOutSlowInEasing)
            )
        }

        delay(110)
        soundPool?.play(pageFlipSoundId, 0.85f, 0.85f, 1, 0, 1.08f)
        launch {
            page2Rotation.animateTo(
                targetValue = -158f,
                animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing)
            )
        }

        delay(110)
        soundPool?.play(pageFlipSoundId, 0.9f, 0.9f, 1, 0, 1.15f)
        launch {
            page3Rotation.animateTo(
                targetValue = -154f,
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            )
        }

        // 4. Kamera mendekat (zoom-in) ke halaman yang terbuka
        delay(220)
        launch {
            cameraOffsetX.animateTo(
                targetValue = -24f,
                animationSpec = tween(durationMillis = 520, easing = FastOutLinearInEasing)
            )
        }
        cameraScale.animateTo(
            targetValue = 4.5f,
            animationSpec = tween(durationMillis = 520, easing = FastOutLinearInEasing)
        )

        // 5. Pindah ke Home Page lalu fade-out halus
        onAnimationFinished()
        overlayAlpha.animateTo(0f, tween(200))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = overlayAlpha.value }
            .background(BgDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            // Teks Header di atas buku
            Text(
                text = if (!username.isNullOrBlank()) "Welcome back, $username" else "Welcome to Anicata",
                color = PaperColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.graphicsLayer {
                    alpha = headerAlpha.value
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Wadah Buku 3D (menunggu di-tap user)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(210.dp)
                    .height(290.dp)
                    .graphicsLayer {
                        scaleX = cameraScale.value
                        scaleY = cameraScale.value
                        translationX = cameraOffsetX.value
                    }
                    .clickable(
                        enabled = !isOpening,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        isOpening = true
                    }
            ) {
                // Halaman Dasar (tujuan zoom kamera)
                BookBasePage()

                // Lembaran Kertas 3
                FlippingPaperPage(
                    rotationY = page3Rotation.value,
                    cameraDist = 14f * density
                )

                // Lembaran Kertas 2
                FlippingPaperPage(
                    rotationY = page2Rotation.value,
                    cameraDist = 14f * density
                )

                // Lembaran Kertas 1
                FlippingPaperPage(
                    rotationY = page1Rotation.value,
                    cameraDist = 14f * density
                )

                // Sampul Depan Buku
                BookFrontCover(
                    rotationY = coverRotation.value,
                    cameraDist = 14f * density
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Petunjuk tap
            Text(
                text = "Tap the book to open",
                color = PaperColor.copy(alpha = 0.7f),
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.graphicsLayer {
                    alpha = if (isOpening) headerAlpha.value else hintAlpha
                }
            )
        }
    }
}

@Composable
private fun BookFrontCover(
    rotationY: Float,
    cameraDist: Float
) {
    val isBackVisible = rotationY < -90f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.rotationY = rotationY
                transformOrigin = TransformOrigin(0f, 0.5f)
                cameraDistance = cameraDist
            }
            .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp, topStart = 3.dp, bottomStart = 3.dp))
            .background(if (isBackVisible) CoverSpine else CoverColor)
            .border(
                width = 1.5.dp,
                color = CoverBorder,
                shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp, topStart = 3.dp, bottomStart = 3.dp)
            )
    ) {
        if (!isBackVisible) {
            Row(modifier = Modifier.fillMaxSize()) {
                // Punggung / Spine buku
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .fillMaxHeight()
                        .background(CoverSpine)
                )

                // Konten Sampul Depan
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 28.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "VOL. 01",
                        color = PaperColor.copy(alpha = 0.65f),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.5.sp
                    )

                    Column {
                        Text(
                            text = "アニカタ",
                            color = AccentGold,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 3.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "ANICATA",
                            color = PaperColor,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .width(42.dp)
                                .height(3.dp)
                                .background(AccentGold)
                        )
                    }

                    Text(
                        text = "ANIME & MANGA CATALOG",
                        color = PaperColor.copy(alpha = 0.55f),
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FlippingPaperPage(
    rotationY: Float,
    cameraDist: Float
) {
    val shadeAlpha = (kotlin.math.abs(rotationY) / 180f).coerceIn(0f, 0.25f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 3.dp)
            .graphicsLayer {
                this.rotationY = rotationY
                transformOrigin = TransformOrigin(0f, 0.5f)
                cameraDistance = cameraDist
            }
            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            .background(PaperColor)
            .background(Color.Black.copy(alpha = shadeAlpha))
            .border(0.5.dp, PaperShadow, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .border(1.dp, PaperShadow)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, PaperShadow)
            )
        }
    }
}

@Composable
private fun BookBasePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp, horizontal = 3.dp)
            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(PaperShadow, PaperColor, PaperColor)
                )
            )
            .border(1.dp, PaperShadow, RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "アニカタ",
                color = BgDark.copy(alpha = 0.2f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "CHAPTER 01",
                color = BgDark.copy(alpha = 0.35f),
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookOpeningOverlayPreview() {
    BookOpeningOverlay(
        username = "Raihan",
        onAnimationFinished = {}
    )
}