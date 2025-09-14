package com.raihan.anicata.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.ui.theme.TextPrimaryColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailRow(label: String, values: List<String>) {
    Row(
        modifier = Modifier
            //.padding(horizontal = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = TextPrimaryColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .width(120.dp)
                .alignByBaseline()
        )

        FlowRow(
            modifier = Modifier
                .weight(1f)
                .alignByBaseline(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            values.forEach { value ->
                InfoChip(text = value)
            }
        }
    }
}

@Preview
@Composable
private fun DetailRowPreview() {
    DetailRow(
        label = "Genres",
        values = listOf("Action", "Adventure", "Fantasy", "Shounen")
    )
}