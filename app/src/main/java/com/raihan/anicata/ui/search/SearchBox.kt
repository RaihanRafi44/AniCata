package com.raihan.anicata.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Komponen ini sekarang hanya berisi BasicTextField
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp) // Sesuaikan tinggi jika perlu
            .background(
                color = Color(0xFFE8F5E9), // Warna hijau muda
                shape = RoundedCornerShape(12.dp)
            )
            .border(BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)), RoundedCornerShape(12.dp)),
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(text = "Cari sesuatu...", color = Color.Gray, fontSize = 18.sp)
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchTextFieldPreview() {
    var query by remember { mutableStateOf("Naruto") }
    SearchTextField(
        query = query,
        onQueryChange = { query = it },
        modifier = Modifier.padding(16.dp)
    )
}