package com.raihan.anicata.ui.detail.manga

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.manga.full.MangaFullRelation

private val specialOrange = Color(0xFFF57C00)
private val tvBrown = Color(0xFFBF360C)
private val mangaBlue = Color(0xFF0288D1)

@Composable
fun OtherMangaInfo(
    relations: List<MangaFullRelation>,
    onRelationClick: (id: Int, type: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (relations.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MangaSectionHeaderTitle(
                title = "Related Media",
                modifier = Modifier.padding(bottom = 12.dp)
            )

            relations.forEachIndexed { index, relation ->
                SectionHeader(title = relation.relation)
                Spacer(modifier = Modifier.height(6.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    relation.entry.forEach { entry ->
                        MediaItem(
                            title = entry.name,
                            tag = entry.type,
                            onClick = { onRelationClick(entry.id, entry.type) }
                        )
                    }
                }

                if (index < relations.size - 1) {
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    } else {
        Text(
            text = "No related media found.",
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 13.5.sp
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF006A60),
        fontSize = 14.sp
    )
}

@Composable
private fun MediaItem(
    title: String,
    tag: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFF8F9FA),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF191C1E),
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            MediaTag(text = tag)
        }
    }
}

@Composable
private fun MediaTag(text: String) {
    val backgroundColor = when (text.uppercase()) {
        "SPECIAL", "MOVIE", "TV", "OVA", "ONA", "MUSIC" -> specialOrange
        "MANGA", "NOVEL", "DOUJINSHI", "MANHWA", "MANHUA", "LIGHT NOVEL", "ONE-SHOT" -> mangaBlue
        "ANIME" -> tvBrown
        else -> Color.Gray
    }

    val displayText = text.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 3.5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
