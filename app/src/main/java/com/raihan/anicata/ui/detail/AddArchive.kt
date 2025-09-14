package com.raihan.anicata.ui.detail

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButtonsGroup(modifier: Modifier = Modifier) {
    // Surface adalah container utama dengan latar belakang hijau dan sudut membulat.
    // Ukurannya akan menyesuaikan dengan konten di dalamnya (wrap_content).
    Surface(
        modifier = modifier, // Gunakan modifier dari parameter untuk fleksibilitas
        //color = Color(0xFFE8F5E9), // Warna hijau pucat
        shape = RoundedCornerShape(16.dp) // Sudut membulat untuk container
    ) {
        // Column untuk menata tombol secara vertikal
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp) // Jarak antar tombol
        ) {
            // Tombol pertama: Bookmark
            CustomActionButton(
                text = "Bookmark",
                icon = Icons.Filled.Bookmark,
                onClick = { /* TODO: Logika ketika tombol Bookmark diklik */ }
            )

            // Tombol kedua: Add to my favorite
            CustomActionButton(
                text = "Add to my favorite",
                icon = Icons.Outlined.FavoriteBorder,
                onClick = { /* TODO: Logika ketika tombol Favorite diklik */ }
            )
        }
    }
}

/**
 * Composable privat untuk membuat tombol dengan ikon dan teks.
 */
@Composable
private fun CustomActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF1D1FD),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth() // Memberi lebar yang tetap agar terlihat rapi
            .height(56.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 16.sp)
    }
}

/**
 * Fungsi pratinjau untuk melihat tampilan `ActionButtonsScreen` di Android Studio.
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ActionButtonsScreenPreview() {
    ActionButtonsGroup()
}