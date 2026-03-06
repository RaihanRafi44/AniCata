package com.raihan.anicata.ui.detail.manga

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
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
fun ActionButtonsGroup(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    isFavorite: Boolean,
    onBookmarkClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
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
            val bookmarkText = if (isBookmarked) "Bookmarked" else "Bookmark"
            val bookmarkIcon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

            val favoriteText = if (isFavorite) "Favorited" else "Favorite"
            val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

            val containerBookmarkColor = if (isBookmarked) Color(0xFFE0E0E0) else Color(0xFFF1D1FD)
            val containerFavoriteColor = if (isFavorite) Color(0xFFE0E0E0) else Color(0xFFF1D1FD)

            // Tombol pertama: Bookmark
            CustomActionButton(
                text = bookmarkText,
                icon = bookmarkIcon,
                containerColor = containerBookmarkColor,
                onClick = onBookmarkClick
            )

            // Tombol kedua: Add to my favorite
            CustomActionButton(
                text = favoriteText,
                icon = favoriteIcon,
                containerColor = containerFavoriteColor,
                onClick = onFavoriteClick
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
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
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
