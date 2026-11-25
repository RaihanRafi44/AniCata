package com.raihan.anicata.ui.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.R
import com.raihan.anicata.data.model.auth.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Color Tokens: Tankobon Print Palette
private object MangaTokens {
    // Warm Paper Palette (Light Mode - Tankobon Paper)
    val WarmCanvas = Color(0xFFF4F0E6)
    val InkBlack = Color(0xFF121417)
    val BrandTealLight = Color(0xFF006A60)
    val AccentTealSoftLight = Color(0xFFCCE8E4)
    val HalftoneDotLight = Color(0x12121417)
    val InkMutedLight = Color(0xFF4B5563)
    val ErrorBgLight = Color(0xFFFEE2E2)
    val ErrorInkLight = Color(0xFFB91C1C)

    // Deep Sumi Ink Palette (Dark Mode - Deep Charcoal Slate)
    val DarkCanvas = Color(0xFF111622)
    val OffWhiteInk = Color(0xFFF4F0E6)
    val BrandTealDark = Color(0xFF2DD4BF)
    val AccentTealSoftDark = Color(0xFF163633)
    val HalftoneDotDark = Color(0x18F4F0E6)
    val InkMutedDark = Color(0xFF9CA3AF)
    val ErrorBgDark = Color(0xFF3B1219)
    val ErrorInkDark = Color(0xFFFCA5A5)

    // Hanko Stamp Tokens
    val StampRed = Color(0xFFD93829)
    val WarmPaper = Color(0xFFF4F1EA)
}

@Composable
fun SignInScreen(
    state: LoginState,
    onSignInClick: () -> Unit,
    onDismissError: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    onSuccessAnimationFinished: () -> Unit = {}
) {
    // Select Palette based on theme
    val canvasBase = if (useDarkTheme) MangaTokens.DarkCanvas else MangaTokens.WarmCanvas
    val inkPrimary = if (useDarkTheme) MangaTokens.OffWhiteInk else MangaTokens.InkBlack
    val brandTeal = if (useDarkTheme) MangaTokens.BrandTealDark else MangaTokens.BrandTealLight
    val accentTealSoft = if (useDarkTheme) MangaTokens.AccentTealSoftDark else MangaTokens.AccentTealSoftLight
    val halftoneDot = if (useDarkTheme) MangaTokens.HalftoneDotDark else MangaTokens.HalftoneDotLight
    val inkMuted = if (useDarkTheme) MangaTokens.InkMutedDark else MangaTokens.InkMutedLight
    val errorBg = if (useDarkTheme) MangaTokens.ErrorBgDark else MangaTokens.ErrorBgLight
    val errorInk = if (useDarkTheme) MangaTokens.ErrorInkDark else MangaTokens.ErrorInkLight

    // Entrance Animation Triggers
    var entranceStarted by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        entranceStarted = true
    }

    val anim1Progress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (entranceStarted) 1f else 0f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "anim1"
    )

    val anim2Progress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (entranceStarted) 1f else 0f,
        animationSpec = tween(400, delayMillis = 100, easing = FastOutSlowInEasing),
        label = "anim2"
    )

    val anim3Progress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (entranceStarted) 1f else 0f,
        animationSpec = tween(400, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "anim3"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(canvasBase)
            .drawWithCache {
                val step = 20.dp.toPx()
                val cols = (size.width / step).toInt() + 2
                val rows = (size.height / step).toInt() + 2
                val maxRadius = 1.8.dp.toPx()

                onDrawWithContent {
                    drawContent()
                    for (r in 0 until rows) {
                        for (c in 0 until cols) {
                            val cx = c * step
                            val cy = r * step
                            val factor = (1f - (cy / size.height)).coerceIn(0f, 1f)
                            val radius = maxRadius * factor
                            if (radius > 0.3.dp.toPx()) {
                                drawCircle(
                                    color = halftoneDot,
                                    radius = radius,
                                    center = Offset(cx, cy)
                                )
                            }
                        }
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- TOP EDITORIAL STRIP & MANGA PANEL ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = anim1Progress
                        translationY = (1f - anim1Progress) * 20.dp.toPx()
                    }
            ) {
                // Editorial Top Strip
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ANICATA",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        letterSpacing = 1.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = inkPrimary.copy(alpha = 0.75f)
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (useDarkTheme) Color(0xFF006A60) else brandTeal
                    ) {
                        Text(
                            text = "VOL. 01",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                HorizontalDivider(
                    color = inkPrimary.copy(alpha = 0.2f),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Manga Artwork Panel with Hard Shadow
                MangaArtworkPanel(
                    inkPrimary = inkPrimary,
                    brandTeal = if (useDarkTheme) Color(0xFF006A60) else brandTeal,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // --- LEFT-ALIGNED BRAND BLOCK ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = anim2Progress
                        translationY = (1f - anim2Progress) * 20.dp.toPx()
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = RoundedCornerShape(6.dp),
                        color = accentTealSoft,
                        border = BorderStroke(2.dp, inkPrimary)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "AniCata Logo",
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AniCata",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = inkPrimary,
                            letterSpacing = (-1).sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = brandTeal,
                            border = BorderStroke(1.5.dp, inkPrimary)
                        ) {
                            Text(
                                text = "アニカタ",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Discover, track, and archive your anime and manga shelf.",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = inkMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            // --- TACTILE GOOGLE BUTTON & ERROR BANNER ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = anim3Progress
                        translationY = (1f - anim3Progress) * 20.dp.toPx()
                    }
            ) {
                // Manga Error Caption Box
                AnimatedVisibility(
                    visible = state.signInError != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(4.dp),
                        color = errorBg,
                        border = BorderStroke(1.5.dp, errorInk)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Error",
                                    tint = errorInk,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = state.signInError ?: "",
                                    color = errorInk,
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 16.sp
                                )
                            }
                            IconButton(
                                onClick = onDismissError,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss Error",
                                    tint = errorInk,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                // Tactile Neo-Brutalist Google Button
                TactileGoogleButton(
                    state = state,
                    inkPrimary = inkPrimary,
                    brandTeal = if (useDarkTheme) Color(0xFF006A60) else brandTeal,
                    onSignInClick = onSignInClick
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Editorial Footer
                val legalText = buildAnnotatedString {
                    append("By continuing, you agree to ")

                    pushStringAnnotation(tag = "TERMS", annotation = "terms")
                    withStyle(
                        style = SpanStyle(
                            color = brandTeal,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Terms")
                    }
                    pop()

                    append(" & ")

                    pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                    withStyle(
                        style = SpanStyle(
                            color = brandTeal,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("Privacy Policy")
                    }
                    pop()

                    append(".")
                }

                ClickableText(
                    text = legalText,
                    style = androidx.compose.ui.text.TextStyle(
                        color = inkMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { offset ->
                        legalText.getStringAnnotations(offset, offset)
                            .firstOrNull()?.let { annotation ->
                                when (annotation.tag) {
                                    "TERMS" -> onTermsClick()
                                    "PRIVACY" -> onPrivacyClick()
                                }
                            }
                    }
                )
            }
        }

        // --- HANKO STAMP OPENING OVERLAY ON LOGIN SUCCESS ---
        if (state.isSignInSuccessful) {
            LoginSuccessOverlay(
                username = state.username,
                onAnimationFinished = onSuccessAnimationFinished
            )
        }
    }
}

@Composable
private fun MangaArtworkPanel(
    inkPrimary: Color,
    brandTeal: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "kenBurns")
    val kenBurnsScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
    ) {
        // Solid Hard Shadow Offset Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.7f)
                .offset(x = 6.dp, y = 6.dp)
                .background(inkPrimary, RoundedCornerShape(6.dp))
        )

        // Main Framed Manga Panel Surface
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.7f)
                .clip(RoundedCornerShape(6.dp))
                .border(BorderStroke(2.5.dp, inkPrimary), RoundedCornerShape(6.dp))
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_anime),
                contentDescription = "Manga Panel Artwork",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = kenBurnsScale
                        scaleY = kenBurnsScale
                    }
            )

            // Panel Vignette Edge Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x66000000)
                            )
                        )
                    )
            )

            // Bottom-Left Katakana Registration Tag
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                shape = RoundedCornerShape(3.dp),
                color = brandTeal,
                border = BorderStroke(1.dp, inkPrimary)
            ) {
                Text(
                    text = "● アニカタ",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun TactileGoogleButton(
    state: LoginState,
    inkPrimary: Color,
    brandTeal: Color,
    onSignInClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentOffset by animateDpAsState(
        targetValue = if (isPressed || state.isLoading) 2.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "pressOffset"
    )

    val shadowOffset = 4.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !state.isLoading,
                onClick = onSignInClick
            )
    ) {
        // Hard Shadow Layer Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .offset(x = shadowOffset, y = shadowOffset)
                .background(inkPrimary, RoundedCornerShape(8.dp))
        )

        // Front Pressable Surface Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .offset(x = currentOffset, y = currentOffset)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(BorderStroke(2.dp, inkPrimary), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = brandTeal,
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = state.loadingMessage,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF121417)
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "CONTINUE WITH GOOGLE",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        color = Color(0xFF121417)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "1. Tankobon Paper (Light)")
@Composable
fun SignInScreenWarmPaperPreview() {
    SignInScreen(
        state = LoginState(),
        onSignInClick = {},
        useDarkTheme = false
    )
}

@Preview(showBackground = true, name = "2. Deep Sumi Ink (Dark)")
@Composable
fun SignInScreenDarkInkPreview() {
    SignInScreen(
        state = LoginState(),
        onSignInClick = {},
        useDarkTheme = true
    )
}

@Preview(showBackground = true, name = "3. Error Caption Preview")
@Composable
fun SignInScreenErrorPreview() {
    SignInScreen(
        state = LoginState(signInError = "NETWORK ERROR // CANNOT CONNECT TO GOOGLE"),
        onSignInClick = {},
        useDarkTheme = false
    )
}
