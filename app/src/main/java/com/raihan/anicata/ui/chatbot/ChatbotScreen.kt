package com.raihan.anicata.ui.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Construction
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.main.HoshinaIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    val tealPrimary = Color(0xFF006A60)
    val tealContainer = Color(0xFFE0F2F1)
    val tealLight = Color(0xFFF0FDFB)
    val darkText = Color(0xFF191C1E)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Clean Top Bar with Back Button only (matching TopNavBar height & style)
        Surface(
            color = tealContainer,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Hoshina AI",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkText
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateBack?.invoke() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = darkText,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }

        // Scrollable Body Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Avatar Card with Hoshina Anime Girl Icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = tealContainer,
                    modifier = Modifier
                        .size(100.dp)
                        .border(3.dp, tealPrimary, CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = HoshinaIcon,
                            contentDescription = "Hoshina",
                            tint = tealPrimary,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                // AI Sparkle Badge
                Surface(
                    shape = CircleShape,
                    color = tealPrimary,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = "AI",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = "Hoshina AI Assistant",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your Personal Anime & Manga Companion",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Under Development Badge
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = Color(0xFFFFF3E0),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFB74D)),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Construction,
                        contentDescription = "Development",
                        tint = Color(0xFFE65100),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "FEATURE UNDER DEVELOPMENT",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE65100),
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Main Info Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = tealLight),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCE8E4)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Hello there! 👋",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = tealPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "I am Hoshina, your upcoming virtual assistant for AniCata. My conversational engine is currently being trained to help you navigate the world of anime and manga seamlessly.",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFF303133)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "What Hoshina will do when ready:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkText
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    UpcomingFeatureItem(
                        icon = Icons.Rounded.AutoAwesome,
                        title = "Smart Recommendations",
                        description = "Get personalized anime and manga suggestions tailored to your taste."
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    UpcomingFeatureItem(
                        icon = Icons.Rounded.Psychology,
                        title = "Character & Plot QA",
                        description = "Ask questions about character stats, plot summaries, and staff details."
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    UpcomingFeatureItem(
                        icon = Icons.Rounded.Lightbulb,
                        title = "Library Insights",
                        description = "Analyze your reading habits and organize your favorite collection."
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Disabled Chat Box Preview at bottom
        Surface(
            color = Color(0xFFF7FAF9),
            tonalElevation = 4.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    enabled = false,
                    placeholder = {
                        Text(
                            text = "Hoshina is learning... Chat coming soon!",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Color(0xFFD0D0D0),
                        disabledContainerColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(tealPrimary.copy(alpha = 0.4f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun UpcomingFeatureItem(
    icon: ImageVector,
    title: String,
    description: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFF006A60).copy(alpha = 0.12f),
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF006A60),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF191C1E)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
        }
    }
}
