package com.raihan.anicata.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.theme.JimNightshade

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        /*modifier = Modifier.statusBarsPadding(),
        windowInsets = WindowInsets(0),*/
        //windowInsets = WindowInsets(0, 0, 0, 0),
        title = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "AniCata",
                    fontFamily = JimNightshade, // font custom kamu
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp,2.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp)) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 12.dp), // ✅ tambah jarak ke kanan
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFFE0E0E0), CircleShape),
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.background(Color(0xFFE0E0E0), CircleShape)
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(24.dp))
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE0F2F1), // hijau muda
            scrolledContainerColor = Color(0xFFE0F2F1) // ✅ Warna saat di-scroll (SAMA)
        ),
        scrollBehavior = scrollBehavior // <-- GUNAKAN parameter di sini
    )
}
