package com.raihan.anicata.ui.paging

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaginationControls(
    modifier: Modifier = Modifier,
    currentPage: Int,
    startPage: Int, // <-- State baru dari pemanggil
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    visiblePages: Int = 3
) {
    val activeColor = Color(0xFFD0BCFF)
    val inactiveColor = Color(0xFFE0E0E0)

    // Logika window sekarang HANYA menggunakan startPage yang diberikan
    val endPage = (startPage + visiblePages - 1).coerceAtMost(totalPages)

    Box(
        modifier = modifier
            .fillMaxWidth()
            //.background(backgroundColor)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isLeftEnabled = currentPage > 1
            val isRightEnabled = currentPage < totalPages
            // Tombol "Sebelumnya"
            OutlinedButton(
                onClick = {
                    if (currentPage > 1) {
                        onPageChange(currentPage - 1)
                    }
                },
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
                // Border warna ungu saat aktif, abu-abu saat inactive
                border = BorderStroke(
                    width = 2.dp,
                    color = if (isLeftEnabled) activeColor else inactiveColor
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    // Background transparan agar menyatu dengan background halaman
                    containerColor = Color.Transparent,
                    // Ikon panah warna ungu saat aktif
                    contentColor = activeColor,
                    // Background tetap transparan saat inactive
                    disabledContainerColor = Color.Transparent,
                    // Ikon panah warna abu-abu saat inactive
                    disabledContentColor = inactiveColor
                ),
                enabled = currentPage > 1
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Halaman Sebelumnya")
            }

            // Tombol Nomor Halaman
            // Loop dari startPage ke endPage
            for (page in startPage..endPage) {
                Button(
                    onClick = { onPageChange(page) },
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (page == currentPage) activeColor else inactiveColor,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "$page", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Tombol "Berikutnya"
            OutlinedButton(
                onClick = {
                    if (currentPage < totalPages) {
                        onPageChange(currentPage + 1)
                    }
                },
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(0.dp),
                // Border warna ungu saat aktif, abu-abu saat inactive
                border = BorderStroke(
                    width = 2.dp,
                    color = if (isRightEnabled) activeColor else inactiveColor
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    // Background transparan agar menyatu dengan background halaman
                    containerColor = Color.Transparent,
                    // Ikon panah warna ungu saat aktif
                    contentColor = activeColor,
                    // Background tetap transparan saat inactive
                    disabledContainerColor = Color.Transparent,
                    // Ikon panah warna abu-abu saat inactive
                    disabledContentColor = inactiveColor
                ),
                enabled = currentPage < totalPages
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Halaman Berikutnya")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PaginationControlsPreview() {
    // State lokal ini HANYA untuk kebutuhan preview
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
            // Logika baru juga diterapkan di preview
            if (newPage < previewStartPage) {
                previewStartPage = newPage.coerceIn(1, maxStartPage)
            } else if (newPage > previewStartPage + (visiblePages - 1)) {
                previewStartPage = (newPage - (visiblePages - 1)).coerceIn(1, maxStartPage)
            }
        }
    )
}