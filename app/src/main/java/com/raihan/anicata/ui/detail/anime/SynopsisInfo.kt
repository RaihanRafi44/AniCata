package com.raihan.anicata.ui.detail.anime

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.anime.full.AnimeData

@Composable
fun SectionHeaderTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    val themeColor = Color(0xFF006A60)
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(4.dp, 20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(themeColor)
        )
        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF191C1E)
        )
    }
}

@Composable
fun SynopsisInfo(
    modifier: Modifier = Modifier,
    animeData: AnimeData
) {
    val synopsisText = animeData.synopsis
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SectionHeaderTitle(title = "Synopsis")

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = if (synopsisText.isNullOrBlank()) "No synopsis available." else synopsisText,
            fontSize = 14.sp,
            color = Color(0xFF263238),
            lineHeight = 23.sp,
            textAlign = TextAlign.Justify,
            maxLines = if (isExpanded) Int.MAX_VALUE else 4,
            overflow = TextOverflow.Ellipsis
        )

        if (!synopsisText.isNullOrBlank() && synopsisText.length > 180) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isExpanded) "View Less" else "View More",
                color = Color(0xFF006A60),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(vertical = 6.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
