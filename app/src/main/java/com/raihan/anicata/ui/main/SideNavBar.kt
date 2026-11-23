package com.raihan.anicata.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.FormatListBulleted
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Leaderboard
import androidx.compose.material.icons.rounded.OndemandVideo
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.auth.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerMenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String? = null,
    val subItems: List<DrawerSubMenuItem>? = null
)

data class DrawerSubMenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val drawerMenuItems = listOf(
    DrawerMenuItem(
        title = "Home",
        icon = Icons.Rounded.Home,
        route = "home"
    ),
    DrawerMenuItem(
        title = "Seasonal Anime",
        icon = Icons.Rounded.Tv,
        route = "seasonal"
    ),
    DrawerMenuItem(
        title = "Top Lists",
        icon = Icons.Rounded.Leaderboard,
        subItems = listOf(
            DrawerSubMenuItem("Top Anime", Icons.Rounded.OndemandVideo, "top_anime"),
            DrawerSubMenuItem("Top Manga", Icons.AutoMirrored.Rounded.MenuBook, "top_manga")
        )
    ),
    DrawerMenuItem(
        title = "All Lists",
        icon = Icons.AutoMirrored.Rounded.FormatListBulleted,
        route = "all_lists"
    ),
    DrawerMenuItem(
        title = "Archive",
        icon = Icons.Rounded.Bookmark,
        route = "archive"
    )
)

/**
 * Komponen utama yang membangun seluruh konten di dalam Navigation Drawer.
 */
@Composable
fun AppDrawerContent(
    userData: UserData?,
    navController: NavController,
    scope: CoroutineScope,
    closeDrawer: () -> Unit,
    onSignOut: () -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp)
    ) {
        // Bagian Header Profil Sederhana
        DrawerHeader(userData = userData)

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            color = Color(0xFFE0E0E0),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bagian Body Navigasi
        DrawerBody(
            menuItems = drawerMenuItems,
            currentRoute = currentRoute,
            onNavigate = { route ->
                if (route != currentRoute) {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                scope.launch { closeDrawer() }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Footer Brand Info
        DrawerFooter()
    }
}

/**
 * Composable simpel dan bersih untuk bagian header profil.
 */
@Composable
private fun DrawerHeader(userData: UserData?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = userData?.profilePictureUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = userData?.username ?: "Mirai Katane",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF191C1E)
        )
    }
}

/**
 * Composable untuk daftar menu navigasi.
 */
@Composable
private fun DrawerBody(
    menuItems: List<DrawerMenuItem>,
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    var isTopListsExpanded by rememberSaveable { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isTopListsExpanded) 180f else 0f,
        label = "rotation"
    )

    val activeColor = Color(0xFF006A60)

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        menuItems.forEach { item ->
            if (item.subItems != null) {
                // Top Lists dengan sub-menu expandable
                val isSubSelected = item.subItems.any { it.route == currentRoute }

                Surface(
                    onClick = { isTopListsExpanded = !isTopListsExpanded },
                    shape = RoundedCornerShape(28.dp),
                    color = if (isSubSelected && !isTopListsExpanded) activeColor.copy(alpha = 0.12f) else Color.Transparent
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = activeColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF191C1E),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Expand ${item.title}",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(rotationAngle)
                        )
                    }
                }

                AnimatedVisibility(visible = isTopListsExpanded) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 2.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        item.subItems.forEach { subItem ->
                            val isSelected = subItem.route == currentRoute
                            NavigationDrawerItem(
                                icon = {
                                    Icon(
                                        imageVector = subItem.icon,
                                        contentDescription = subItem.title,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = subItem.title,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                },
                                selected = isSelected,
                                onClick = { onNavigate(subItem.route) },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = activeColor,
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.White,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = activeColor,
                                    unselectedTextColor = Color(0xFF191C1E)
                                ),
                                modifier = Modifier.height(44.dp)
                            )
                        }
                    }
                }
            } else if (item.route != null) {
                val isSelected = item.route == currentRoute
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                        )
                    },
                    selected = isSelected,
                    onClick = { onNavigate(item.route) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = activeColor,
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedContainerColor = Color.Transparent,
                        unselectedIconColor = activeColor,
                        unselectedTextColor = Color(0xFF191C1E)
                    ),
                    modifier = Modifier.height(48.dp)
                )
            }
        }
    }
}

/**
 * Composable untuk footer di bagian paling bawah drawer.
 */
@Composable
private fun DrawerFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "AniCata v1.0.0",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006A60)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Your Ultimate Media Companion",
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}
