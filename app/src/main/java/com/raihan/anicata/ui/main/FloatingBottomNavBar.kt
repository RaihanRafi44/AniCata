package com.raihan.anicata.ui.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NavItemData(
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector = activeIcon,
)

val defaultNavItems = listOf(
    NavItemData(
        label = "Home",
        activeIcon = Icons.Default.Home,
    ),
    NavItemData(
        label = "Archive",
        activeIcon = Icons.Default.Bookmark,
    ),
    NavItemData(
        label = "Profile",
        activeIcon = Icons.Default.Person,
    ),
)

@Composable
fun FloatingBottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    items: List<NavItemData> = defaultNavItems,
    isCollapsed: Boolean = false,
    accentColor: Color = Color(0xFF006A60),
    inactiveColor: Color = Color.Gray,
    containerColor: Color = Color.White,
    borderColor: Color = Color(0xFFE0E0E0),
    borderWidth: Dp = 0.5.dp,
) {
    val selectedNavItem = items.getOrElse(selectedItem) { items.first() }
    val shape = RoundedCornerShape(40.dp)

    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .background(containerColor, shape = shape)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape,
            )
            .clip(shape)
            .animateContentSize(
                alignment = Alignment.Center,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh,
                ),
            ),
    ) {
        AnimatedContent(
            targetState = isCollapsed,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                val duration = if (targetState) 60 else 150
                (fadeIn(animationSpec = tween(duration, easing = FastOutLinearInEasing)) +
                    scaleIn(initialScale = 0.9f, transformOrigin = TransformOrigin.Center) +
                    expandHorizontally(expandFrom = Alignment.CenterHorizontally, animationSpec = tween(duration, easing = FastOutLinearInEasing))) togetherWith
                (fadeOut(animationSpec = tween(duration, easing = FastOutLinearInEasing)) +
                    scaleOut(targetScale = 0.9f, transformOrigin = TransformOrigin.Center) +
                    shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally, animationSpec = tween(duration, easing = FastOutLinearInEasing)))
            },
            label = "NavBarCollapseTransition",
        ) { collapsed ->
            if (collapsed) {
                // Collapsed UI: Single active item in horizontal layout
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = selectedNavItem.activeIcon,
                        contentDescription = selectedNavItem.label,
                        modifier = Modifier.size(20.dp),
                        tint = accentColor,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = selectedNavItem.label,
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            } else {
                // Expanded UI: All navigation items in vertical layout
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items.forEachIndexed { index, item ->
                        NavBarItem(
                            item = item,
                            selected = selectedItem == index,
                            onClick = { onItemSelected(index) },
                            modifier = Modifier.weight(1f),
                            accentColor = accentColor,
                            inactiveColor = inactiveColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpecialPageFloatingNavBar(
    onScrollToTop: () -> Unit,
    onScrollToBottom: () -> Unit,
    onNavigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF006A60),
    containerColor: Color = Color(0xFFFCFCFC),
    borderColor: Color = Color(0xFFE0E0E0),
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(bottom = 36.dp, end = 28.dp)
        ) {
            // VERTICAL BRANCH (Arrow Up & Arrow Down) above bottom right anchor
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(animationSpec = tween(200)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = spring(stiffness = Spring.StiffnessLow)
                        ) +
                        scaleIn(initialScale = 0.6f, transformOrigin = TransformOrigin(0.5f, 1f)),
                exit = fadeOut(animationSpec = tween(150)) +
                        slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(150)
                        ) +
                        scaleOut(targetScale = 0.6f, transformOrigin = TransformOrigin(0.5f, 1f))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    // Scroll to Top Button (↑)
                    FloatingActionButtonMini(
                        icon = Icons.Default.ArrowUpward,
                        contentDescription = "Scroll to top",
                        onClick = {
                            onScrollToTop()
                            isExpanded = false
                        },
                        accentColor = accentColor,
                        containerColor = containerColor,
                        borderColor = borderColor
                    )

                    // Scroll to Bottom Button (↓)
                    FloatingActionButtonMini(
                        icon = Icons.Default.ArrowDownward,
                        contentDescription = "Scroll to bottom",
                        onClick = {
                            onScrollToBottom()
                            isExpanded = false
                        },
                        accentColor = accentColor,
                        containerColor = containerColor,
                        borderColor = borderColor
                    )
                }
            }

            // BOTTOM ROW (Horizontal Nav Items + Anchor Button)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                // HORIZONTAL NAV BRANCH (Home, Archive, Profile to the left)
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn(animationSpec = tween(200)) +
                            slideInHorizontally(
                                initialOffsetX = { it / 2 },
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ) +
                            scaleIn(initialScale = 0.6f, transformOrigin = TransformOrigin(1f, 0.5f)),
                    exit = fadeOut(animationSpec = tween(150)) +
                            slideOutHorizontally(
                                targetOffsetX = { it / 2 },
                                animationSpec = tween(150)
                            ) +
                            scaleOut(targetScale = 0.6f, transformOrigin = TransformOrigin(1f, 0.5f))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        // Home (🏠)
                        FloatingActionButtonMini(
                            icon = Icons.Default.Home,
                            contentDescription = "Home",
                            onClick = {
                                onNavigateTo("home")
                                isExpanded = false
                            },
                            accentColor = accentColor,
                            containerColor = containerColor,
                            borderColor = borderColor
                        )

                        // Archive (🔖)
                        FloatingActionButtonMini(
                            icon = Icons.Default.Bookmark,
                            contentDescription = "Archive",
                            onClick = {
                                onNavigateTo("archive")
                                isExpanded = false
                            },
                            accentColor = accentColor,
                            containerColor = containerColor,
                            borderColor = borderColor
                        )

                        // Profile (👤)
                        FloatingActionButtonMini(
                            icon = Icons.Default.Person,
                            contentDescription = "Profile",
                            onClick = {
                                onNavigateTo("profile")
                                isExpanded = false
                            },
                            accentColor = accentColor,
                            containerColor = containerColor,
                            borderColor = borderColor
                        )
                    }
                }

                // ANCHOR TOGGLE BUTTON (<- when closed, -> when open)
                Surface(
                    onClick = { isExpanded = !isExpanded },
                    shape = CircleShape,
                    color = accentColor,
                    shadowElevation = 0.dp,
                    modifier = Modifier
                        .size(48.dp)
                        .border(width = 0.5.dp, color = borderColor, shape = CircleShape)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isExpanded) Icons.AutoMirrored.Filled.ArrowForward else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (isExpanded) "Tutup Navigasi" else "Buka Navigasi",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FloatingActionButtonMini(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    accentColor: Color,
    containerColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = containerColor,
        shadowElevation = 0.dp,
        modifier = modifier
            .size(48.dp)
            .border(width = 0.5.dp, color = borderColor, shape = CircleShape)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = accentColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun NavBarItem(
    item: NavItemData,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF006A60),
    inactiveColor: Color = Color.Gray,
) {
    val animatedIconColor by animateColorAsState(
        targetValue = if (selected) accentColor else inactiveColor,
        animationSpec = tween(durationMillis = 200),
        label = "iconColor",
    )

    val animatedTextColor by animateColorAsState(
        targetValue = if (selected) Color.Black else inactiveColor,
        animationSpec = tween(durationMillis = 200),
        label = "textColor",
    )

    val animatedIndicatorColor by animateColorAsState(
        targetValue = if (selected) accentColor else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "indicatorColor",
    )

    val animatedIndicatorWidth by animateDpAsState(
        targetValue = if (selected) 16.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "indicatorWidth",
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = if (selected) item.activeIcon else item.inactiveIcon,
            contentDescription = item.label,
            modifier = Modifier.size(24.dp),
            tint = animatedIconColor,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = item.label,
            color = animatedTextColor,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(18.dp)
                .height(3.dp),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .width(animatedIndicatorWidth)
                    .height(3.dp)
                    .background(
                        color = animatedIndicatorColor,
                        shape = RoundedCornerShape(1.5.dp),
                    ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun FloatingBottomNavBarExpandedPreview() {
    var selected by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        FloatingBottomNavBar(
            selectedItem = selected,
            onItemSelected = { selected = it },
            isCollapsed = false,
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun FloatingBottomNavBarCollapsedPreview() {
    var selected by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.BottomCenter,
    ) {
        FloatingBottomNavBar(
            selectedItem = selected,
            onItemSelected = { selected = it },
            isCollapsed = true,
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun SpecialPageFloatingNavBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        SpecialPageFloatingNavBar(
            onScrollToTop = {},
            onScrollToBottom = {},
            onNavigateTo = {}
        )
    }
}
