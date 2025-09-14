package com.raihan.anicata.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// ✅ Definisikan item utama dan sub-item secara terpisah
val mainItems = listOf("Seasonal Anime", "Top Lists", "All Lists", "About Us", "Settings")
val topListsSubItems = listOf("Anime", "Manga", "Novel")

/**
 * Komponen utama yang membangun seluruh konten di dalam Navigation Drawer.
 */
@Composable
fun AppDrawerContent(
    userData: UserData?,
    navController: NavController,
    scope: CoroutineScope,
    closeDrawer: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Bagian Header: menampilkan foto profil dan nama pengguna
        DrawerHeader(userData = userData)

        // Divider untuk memisahkan header dengan menu
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Bagian Body: menampilkan daftar menu
        DrawerBody(
            navController = navController,
            scope = scope,
            closeDrawer = closeDrawer
        )

        // Spacer untuk mendorong item sign out ke bawah
        Spacer(modifier = Modifier.weight(1f))

        // Opsi untuk Sign Out
        Text(
            text = "Sign Out",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scope.launch {
                        onSignOut()
                    }
                }
                .padding(horizontal = 28.dp, vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Composable untuk bagian header drawer.
 */
@Composable
private fun DrawerHeader(userData: UserData?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 28.dp, end = 28.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Gunakan AsyncImage dari Coil untuk memuat gambar dari URL
        AsyncImage(
            model = userData?.profilePictureUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = userData?.username ?: "Mirai Katane", // Fallback text jika nama tidak ada
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Composable untuk daftar menu navigasi.
 */
@Composable
private fun DrawerBody(
    navController: NavController,
    scope: CoroutineScope,
    closeDrawer: () -> Unit
) {
    var isTopListsExpanded by rememberSaveable { mutableStateOf(false) }
    // State untuk animasi rotasi ikon panah
    val rotationAngle by animateFloatAsState(
        targetValue = if (isTopListsExpanded) 180f else 0f,
        label = "rotation"
    )

    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
        items(mainItems) { item ->
            if (item == "Top Lists") {
                // --- Item yang bisa di-expand ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isTopListsExpanded = !isTopListsExpanded } // Toggle state
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand Top Lists",
                        tint = Color.Gray,
                        modifier = Modifier.rotate(rotationAngle) // Terapkan rotasi
                    )
                }

                // --- Sub-menu yang bisa muncul/hilang ---
                AnimatedVisibility(visible = isTopListsExpanded) {
                    Column(modifier = Modifier.padding(start = 32.dp)) { // Beri indentasi
                        topListsSubItems.forEach { subItem ->
                            Text(
                                text = subItem,
                                style = TextStyle(fontSize = 15.sp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // TODO: Navigasi untuk sub-item
                                        scope.launch { closeDrawer() }
                                    }
                                    .padding(horizontal = 16.dp, vertical = 10.dp)
                            )
                        }
                    }
                }

            } else {
                // --- Item menu biasa ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // TODO: Implement navigation logic
                            scope.launch { closeDrawer() }
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}