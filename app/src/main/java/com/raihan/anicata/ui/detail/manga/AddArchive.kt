package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
    val bookmarkBlue = Color(0xFF2563EB)
    val bookmarkLightBlue = Color(0xFFEFF6FF)
    val favoriteRed = Color(0xFFE53935)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val bookmarkText = if (isBookmarked) "Bookmarked" else "Bookmark"
        val bookmarkIcon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

        val favoriteText = if (isFavorite) "Favorited" else "Favorite"
        val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

        // Bookmark Button (Blue Theme)
        CustomActionButton(
            text = bookmarkText,
            icon = bookmarkIcon,
            containerColor = if (isBookmarked) bookmarkBlue else bookmarkLightBlue,
            contentColor = if (isBookmarked) Color.White else bookmarkBlue,
            onClick = onBookmarkClick,
            modifier = Modifier.weight(1f)
        )

        // Favorite Button (Red/Pink Theme)
        CustomActionButton(
            text = favoriteText,
            icon = favoriteIcon,
            containerColor = if (isFavorite) favoriteRed else Color(0xFFFFEBEE),
            contentColor = if (isFavorite) Color.White else favoriteRed,
            onClick = onFavoriteClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CustomActionButton(
    text: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier.height(46.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
