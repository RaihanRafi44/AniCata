package com.raihan.anicata.ui.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.theme.JimNightshade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    containerColor: Color = Color(0xFFE0F2F1),
    contentColor: Color = Color(0xFF191C1E),
) {
    // Cek apakah konten halaman sedang di-scroll di bawah TopNavBar
    val isScrolled = (scrollBehavior?.state?.overlappedFraction ?: 0f) > 0.01f
    val animatedElevation by animateDpAsState(
        targetValue = if (isScrolled) 6.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "topBarElevation",
    )

    Surface(
        modifier = modifier,
        shadowElevation = animatedElevation,
        color = containerColor,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "AniCata",
                        fontFamily = JimNightshade,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = contentColor,
                        textAlign = TextAlign.Center,
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.padding(start = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Menu",
                        tint = contentColor,
                        modifier = Modifier.size(26.dp),
                    )
                }
            },
            actions = {
                Row(
                    modifier = Modifier.padding(end = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Circular Action Button: Search
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = contentColor,
                            modifier = Modifier.size(24.dp),
                        )
                    }

                    // Circular Action Button: Settings
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = contentColor,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            ),
            scrollBehavior = scrollBehavior,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopNavBarPreview() {
    Surface {
        TopNavBar(
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
        )
    }
}
