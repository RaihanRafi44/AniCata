package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.anime.full.AnimeData
import java.util.Locale

@Composable
fun AnimeInfoStats(
    animeData: AnimeData,
    modifier: Modifier = Modifier
) {
    val formattedMembers = try {
        val num = animeData.members
        if (num >= 1_000_000) String.format(Locale.US, "%.1fM", num / 1_000_000.0)
        else if (num >= 1_000) String.format(Locale.US, "%.1fK", num / 1_000.0)
        else num.toString()
    } catch (e: Exception) {
        animeData.members.toString()
    }

    val epText = if (animeData.episodes == null || animeData.episodes == 0) "?" else "${animeData.episodes} eps"

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: Score & Rank
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricCard(
                icon = Icons.Default.Star,
                iconTint = Color(0xFFFFB300),
                containerBg = Color(0xFFFFF8E1),
                borderColor = Color(0xFFFFECB3),
                valueColor = Color(0xFFE65100),
                value = animeData.score?.let { String.format(Locale.US, "%.2f", it) } ?: "?",
                label = "Score",
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                icon = Icons.Default.EmojiEvents,
                iconTint = Color(0xFFAB47BC),
                containerBg = Color(0xFFF3E5F5),
                borderColor = Color(0xFFE1BEE7),
                valueColor = Color(0xFF6A1B9A),
                value = animeData.rank?.let { "#$it" } ?: "N/A",
                label = "Rank",
                modifier = Modifier.weight(1f)
            )
        }

        // Row 2: Members & Episodes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricCard(
                icon = Icons.Default.People,
                iconTint = Color(0xFF0288D1),
                containerBg = Color(0xFFE1F5FE),
                borderColor = Color(0xFFB3E5FC),
                valueColor = Color(0xFF01579B),
                value = formattedMembers,
                label = "Members",
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                icon = Icons.Default.Tv,
                iconTint = Color(0xFF00897B),
                containerBg = Color(0xFFE0F2F1),
                borderColor = Color(0xFFB2DFDB),
                valueColor = Color(0xFF004D40),
                value = epText,
                label = "Episodes",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(
    icon: ImageVector,
    iconTint: Color,
    containerBg: Color,
    borderColor: Color,
    valueColor: Color,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = containerBg,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueColor
                )
                Text(
                    text = label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = valueColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}
