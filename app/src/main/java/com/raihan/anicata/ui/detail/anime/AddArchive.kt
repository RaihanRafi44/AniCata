package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val themeColor = Color(0xFF006A60)
    val favoriteRed = Color(0xFFE53935)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val bookmarkText = if (isBookmarked) "Bookmarked" else "Bookmark"
        val bookmarkIcon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder

        val favoriteText = if (isFavorite) "Favorited" else "Favorite"
        val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

        // Bookmark Button (Teal Theme)
        CustomActionButton(
            text = bookmarkText,
            icon = bookmarkIcon,
            containerColor = if (isBookmarked) themeColor else Color(0xFFE0F2F1),
            contentColor = if (isBookmarked) Color.White else themeColor,
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
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = modifier.height(48.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Perfectly Symmetrical, Non-Ambiguous Modern Confirmation Dialog.
 * Optimized padding and concise text ensure ZERO clipping on all device screen sizes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AniCataConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    dismissText: String = "Batal",
    icon: ImageVector,
    iconTint: Color = Color(0xFF006A60),
    iconBgColor: Color = Color(0xFFE0F2F1),
    confirmButtonColor: Color = Color(0xFF006A60),
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            tonalElevation = 6.dp,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Icon Badge
                Surface(
                    shape = CircleShape,
                    color = iconBgColor,
                    modifier = Modifier.size(52.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2. Title
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF191C1E),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 3. Message
                Text(
                    text = message,
                    fontSize = 13.5.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Symmetrical Equal-Width Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dismiss Button (Equal Weight)
                    OutlinedButton(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF475569)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Text(
                            text = dismissText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }

                    // Confirm Button (Equal Weight)
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = confirmButtonColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                    ) {
                        Text(
                            text = confirmText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
