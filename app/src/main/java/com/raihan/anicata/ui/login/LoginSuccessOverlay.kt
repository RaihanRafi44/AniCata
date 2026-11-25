package com.raihan.anicata.ui.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val InkBlack = Color(0xFF141416)
private val WarmPaper = Color(0xFFF4F1EA)
private val StampRed = Color(0xFFD93829)
private val StampBaseColor = Color(0xFF26262B)
private val StampBaseBorder = Color(0xFF42424A)
private val StampHandleTop = Color(0xFF34343B)

@Composable
fun LoginSuccessOverlay(
    username: String?,
    onAnimationFinished: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val density = LocalDensity.current
    val screenHeightPx = with(density) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    // Ukuran dinamis mengikuti kotak cap agar 100% presisi
    var stampWidthDp by remember { mutableStateOf(160.dp) }
    var stampHeightDp by remember { mutableStateOf(80.dp) }

    // State animasi
    val curtainOffsetY = remember { Animatable(screenHeightPx) }
    val stampScale = remember { Animatable(2.4f) }      // Mulai besar (dekat kamera / di atas kertas)
    val stampOffsetY = remember { Animatable(-140f) }   // Sedikit di atas sebelum menghantam turun
    val stampAlpha = remember { Animatable(0f) }
    val shadowOffset = remember { Animatable(48f) }     // Jarak bayangan saat stempel melayang

    val inkMarkAlpha = remember { Animatable(0f) }
    val shakeOffsetX = remember { Animatable(0f) }
    val welcomeAlpha = remember { Animatable(0f) }
    val welcomeOffsetY = remember { Animatable(16f) }

    LaunchedEffect(Unit) {
        // 1. Tirai gelap naik menutup halaman login
        curtainOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 260, easing = FastOutSlowInEasing)
        )

        // 2. Stempel turun vertikal (Z-axis) menghantam kertas
        launch { stampAlpha.animateTo(1f, tween(90)) }
        launch { stampOffsetY.animateTo(0f, tween(210, easing = FastOutLinearInEasing)) }
        launch { shadowOffset.animateTo(4f, tween(210, easing = FastOutLinearInEasing)) }
        stampScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 210, easing = FastOutLinearInEasing)
        )

        // 3. IMPACT! Stempel menempel ke kertas
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        inkMarkAlpha.snapTo(1f) // Cap tinta langsung tercetak di bawah badan stempel

        // Efek tekan singkat + micro-shake
        launch {
            stampScale.animateTo(0.94f, tween(60))
            delay(150)
            stampScale.animateTo(1f, tween(70))
        }
        launch {
            shakeOffsetX.animateTo(6f, tween(25))
            shakeOffsetX.animateTo(-5f, tween(25))
            shakeOffsetX.animateTo(3f, tween(20))
            shakeOffsetX.animateTo(0f, tween(20))
        }

        delay(300)

        // 4. Stempel diangkat kembali ke atas (menjauh dari kertas, membuka hasil cap)
        launch {
            stampScale.animateTo(2.3f, tween(280, easing = LinearOutSlowInEasing))
        }
        launch {
            stampOffsetY.animateTo(-160f, tween(280, easing = LinearOutSlowInEasing))
        }
        launch {
            shadowOffset.animateTo(54f, tween(280, easing = LinearOutSlowInEasing))
        }
        launch {
            delay(90)
            stampAlpha.animateTo(0f, tween(180))
        }

        // 5. Teks "Welcome back" muncul halus setelah cap terlihat
        delay(160)
        launch { welcomeAlpha.animateTo(1f, tween(260)) }
        welcomeOffsetY.animateTo(0f, tween(260, easing = FastOutSlowInEasing))

        delay(950)

        // 6. Navigasi ke Home saat layar masih tertutup tirai, lalu tirai menyapu ke atas
        onAnimationFinished()
        curtainOffsetY.animateTo(
            targetValue = -screenHeightPx,
            animationSpec = tween(durationMillis = 280, easing = FastOutLinearInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                translationY = curtainOffsetY.value
                translationX = shakeOffsetX.value
            }
            .background(InkBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Wadah presisi: Cap tinta & Stempel tampak atas berbagi titik tengah & sudut yang sama
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.graphicsLayer {
                    rotationZ = -4f
                }
            ) {
                // Layer 1: Hasil Cap Tinta
                Surface(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(3.dp, StampRed),
                    modifier = Modifier
                        .onSizeChanged { size ->
                            stampWidthDp = with(density) { size.width.toDp() }
                            stampHeightDp = with(density) { size.height.toDp() }
                        }
                        .graphicsLayer {
                            alpha = inkMarkAlpha.value
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 26.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = "アニカタ",
                            color = StampRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ANICATA",
                            color = WarmPaper,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                // Layer 2: Bayangan Stempel di atas kertas (mengecil & merapat saat mendekati kertas)
                Box(
                    modifier = Modifier
                        .width(stampWidthDp + 14.dp)
                        .height(stampHeightDp + 14.dp)
                        .graphicsLayer {
                            translationY = stampOffsetY.value + shadowOffset.value
                            translationX = shadowOffset.value * 0.35f
                            scaleX = stampScale.value * 0.96f
                            scaleY = stampScale.value * 0.96f
                            alpha = stampAlpha.value * 0.45f
                        }
                        .background(Color.Black, RoundedCornerShape(10.dp))
                )

                // Layer 3: Alat Stempel Tampak Atas (Top-Down)
                TopDownStampGraphic(
                    width = stampWidthDp + 14.dp,
                    height = stampHeightDp + 14.dp,
                    modifier = Modifier.graphicsLayer {
                        translationY = stampOffsetY.value
                        scaleX = stampScale.value
                        scaleY = stampScale.value
                        alpha = stampAlpha.value
                    }
                )
            }

            // Teks Sapaan User (Tetap lurus)
            if (!username.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    text = "Welcome back, $username",
                    color = WarmPaper.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.graphicsLayer {
                        alpha = welcomeAlpha.value
                        translationY = welcomeOffsetY.value
                    }
                )
            }
        }
    }
}

/**
 * Alat stempel tampak atas (top-down):
 * Punggung kotak stempel yang menutupi area cap + gagang bulat di tengah.
 */
@Composable
private fun TopDownStampGraphic(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(width)
            .height(height)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF323239), StampBaseColor)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(2.dp, StampBaseBorder, RoundedCornerShape(8.dp))
    ) {
        // Garis aksen merah tipis di pinggir badan stempel (penanda tinta merah)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, StampRed.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
        )

        // Dudukan leher gagang
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(46.dp)
                .background(Color(0xFF1A1A1E), CircleShape)
        ) {
            // Kepala gagang stempel tampak atas (3D highlight)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF52525C), StampHandleTop)
                        ),
                        shape = CircleShape
                    )
                    .border(1.5.dp, Color(0xFF646470), CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginSuccessOverlayPreview() {
    LoginSuccessOverlay(
        username = "Raihan",
        onAnimationFinished = {}
    )
}