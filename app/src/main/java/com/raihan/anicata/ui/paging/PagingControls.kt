package com.raihan.anicata.ui.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Minimal & Clean Pagination Controls
 */
@Composable
fun PaginationControls(
    modifier: Modifier = Modifier,
    currentPage: Int,
    startPage: Int = 1,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    visiblePages: Int = 3
) {
    val themeColor = Color(0xFF006A60)
    val endPage = (startPage + visiblePages - 1).coerceAtMost(totalPages)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol "Sebelumnya"
            IconButton(
                onClick = { if (currentPage > 1) onPageChange(currentPage - 1) },
                enabled = currentPage > 1,
                modifier = Modifier.size(38.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFE0F2F1),
                    contentColor = themeColor,
                    disabledContainerColor = Color(0xFFF5F5F5),
                    disabledContentColor = Color.LightGray
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Halaman Sebelumnya",
                    modifier = Modifier.size(18.dp)
                )
            }

            // Nomor Halaman
            for (page in startPage..endPage) {
                val isCurrent = page == currentPage
                Surface(
                    onClick = { onPageChange(page) },
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape,
                    color = if (isCurrent) themeColor else Color(0xFFF5F5F5),
                    contentColor = if (isCurrent) Color.White else Color(0xFF191C1E)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "$page",
                            fontSize = 13.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }

            // Tombol "Berikutnya"
            IconButton(
                onClick = { if (currentPage < totalPages) onPageChange(currentPage + 1) },
                enabled = currentPage < totalPages,
                modifier = Modifier.size(38.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFE0F2F1),
                    contentColor = themeColor,
                    disabledContainerColor = Color(0xFFF5F5F5),
                    disabledContentColor = Color.LightGray
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Halaman Berikutnya",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaginationControlsPreview() {
    var previewCurrentPage by remember { mutableStateOf(1) }
    var previewStartPage by remember { mutableStateOf(1) }
    val totalPages = 10
    val visiblePages = 3
    val maxStartPage = (totalPages - visiblePages + 1).coerceAtLeast(1)

    PaginationControls(
        totalPages = totalPages,
        currentPage = previewCurrentPage,
        startPage = previewStartPage,
        visiblePages = visiblePages,
        onPageChange = { newPage ->
            previewCurrentPage = newPage
            if (newPage < previewStartPage) {
                previewStartPage = newPage.coerceIn(1, maxStartPage)
            } else if (newPage > previewStartPage + (visiblePages - 1)) {
                previewStartPage = (newPage - (visiblePages - 1)).coerceIn(1, maxStartPage)
            }
        }
    )
}
