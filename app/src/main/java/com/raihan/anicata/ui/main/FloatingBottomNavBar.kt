package com.raihan.anicata.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/*@Composable
fun FloatingBottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(50),
            shadowElevation = 8.dp,
            tonalElevation = 2.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onItemSelected(0) }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = if (selectedItem == 0) Color.Blue else Color.Gray
                    )
                }
                IconButton(onClick = { onItemSelected(1) }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = if (selectedItem == 1) Color.Blue else Color.Gray
                    )
                }
            }
        }
    }
}*/
@Composable
fun FloatingBottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            shadowElevation = 8.dp,
            tonalElevation = 2.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .width(180.dp) // atur panjang floating bar (140 dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Item Home
                NavBarItem(
                    icon = Icons.Default.Home,
                    contentDescription = "Home",
                    selected = selectedItem == 0,
                    onClick = { onItemSelected(0) },
                    modifier = Modifier.weight(1f)
                )

                // Item Settings
                NavBarItem(
                    icon = Icons.Default.Settings,
                    contentDescription = "Settings",
                    selected = selectedItem == 1,
                    onClick = { onItemSelected(1) },
                    modifier = Modifier.weight(1f)
                )

                // Item Profile
                NavBarItem(
                    icon = Icons.Default.Person,
                    contentDescription = "Profile",
                    selected = selectedItem == 2,
                    onClick = { onItemSelected(2) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun NavBarItem(
    icon: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(if (selected) Color(0xFFE0E0FF) else Color.Transparent) // blok isi penuh ½ area
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(if (selected) 28.dp else 24.dp), // icon lebih besar saat aktif
            tint = if (selected) Color.Blue else Color.Gray
        )
    }
}








