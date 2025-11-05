package com.raihan.anicata.ui.detail.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.data.model.anime.full.AnimeData
import java.text.NumberFormat
import java.util.Locale

// Warna latar belakang yang mirip dengan gambar
//val lightGreenBg = Color(0xFFE0F7FA)

/**
 * Komponen utama yang menampilkan informasi anime.
 */
@Composable
fun AnimeInfoStats(
    animeData: AnimeData
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp) // Memberi jarak di dalam komponen
    ) {
        Column(
            // Mengatur item di dalam kolom agar berada di tengah secara horizontal
            horizontalAlignment = Alignment.CenterHorizontally,
            //modifier = Modifier.background(lightGreenBg)
        ) {
            // Judul Anime
            Text(
                text = animeData.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(12.dp)) // Spasi vertikal

            // Baris pertama berisi info: Musim, Rating, dan Tipe TV
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
                // Arrangement.SpaceBetween dihapus agar 'weight' yang mengatur
            ) {
                // Setiap item dibungkus Box dengan weight 1f agar lebarnya sama
                /*Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    val seasonYear = "${animeData.season?.replaceFirstChar { it.titlecase() }} ${animeData.year ?: ""}".trim()
                    InfoItem(
                        icon = Icons.Default.Schedule,
                        text = if (seasonYear.isBlank()) "N/A" else seasonYear
                    )
                }*/

                // 1. Hitung nilainya di luar Box
                //val seasonYear = "${animeData.season?.replaceFirstChar { it.titlecase() }} ${animeData.year ?: ""}".trim()
                // 1. Ambil data mentahnya
                val season = animeData.season.orEmpty()
                val year = animeData.year ?: 0 // Mapper akan memberi default 0

                // 2. Kondisi IF diubah: Tampilkan HANYA jika
                //    season TIDAK kosong ATAU tahun BUKAN 0.
                if (season.isNotBlank() || year != 0) {

                    // 3. Format string HANYA di dalam 'if'
                    val seasonText = season.replaceFirstChar { it.titlecase() }
                    val yearText = if (year != 0) year.toString() else "" // Jangan tampilkan "0"
                    val seasonYear = "$seasonText $yearText".trim()

                    Box( // Box ini sekarang HANYA akan dibuat jika ada data
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        InfoItem(
                            icon = Icons.Default.Schedule,
                            text = seasonYear
                        )
                    }
                }

                // 2. Buat kondisi IF di luar Box
                /*if (seasonYear.isNotBlank()) {
                    Box( // Box ini HANYA akan ada jika seasonYear tidak kosong
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        InfoItem(
                            icon = Icons.Default.Schedule,
                            text = seasonYear
                        )
                    }
                }*/
                // Rating
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    InfoItem(
                        icon = Icons.Default.Star,
                        //text = animeData.score?.toString() ?: "N/A",
                        text = animeData.score?.toString() ?: "?",
                        iconColor = Color(0xFFFFC107)
                    )
                }
                // Type
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    // Menggunakan contoh teks "Special" seperti permintaan Anda
                    InfoItem(
                        icon = Icons.Default.Tv,
                        //text = animeData.type ?: "N/A"
                        text = animeData.type.takeIf { !it.isNullOrBlank() } ?: "?",
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Spasi vertikal

            // Baris kedua berisi info jumlah penonton/anggota
            val formattedMembers = try {
                NumberFormat.getNumberInstance(Locale.US).format(animeData.members)
            } catch (e: Exception) {
                animeData.members.toString()
            }
            InfoItem(
                icon = Icons.Rounded.Group,
                text = formattedMembers
                )
            //InfoItem(icon = Icons.Rounded.Group, text = "3,562,063")
        }
    }
}

/**
 * Komponen kecil untuk menampilkan sebuah ikon dan teks.
 * @param icon Ikon yang akan ditampilkan.
 * @param text Teks yang akan ditampilkan di sebelah ikon.
 * @param iconColor Warna untuk ikon (opsional).
 */
@Composable
fun InfoItem(icon: ImageVector, text: String, iconColor: Color = Color.Black) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null, // Deskripsi untuk aksesibilitas
            modifier = Modifier.size(20.dp),
            tint = iconColor
        )
        Spacer(modifier = Modifier.width(6.dp)) // Spasi antara ikon dan teks
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium
        )
    }
}


/**
 * Fungsi Preview untuk melihat tampilan komponen di Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun AnimeInfoCardPreview() {
    //AnimeInfoStats()
}