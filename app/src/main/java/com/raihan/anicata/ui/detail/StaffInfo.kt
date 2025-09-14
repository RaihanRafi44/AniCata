package com.raihan.anicata.ui.detail

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data class untuk merepresentasikan data seorang staff
data class Staff(val name: String, val role: String)

// Daftar staff sesuai gambar
val staffList = listOf(
    Staff("Cook, Justin", "Producer"),
    Staff("Yonai, Noritomo", "Producer"),
    Staff("Maruyama, Hiroo", "Producer"),
    Staff("Kurashige, Nobuyuki", "Producer")
)

/**
 * Composable utama yang membangun seluruh layout Staff.
 */
@Composable
fun StaffInfo() {
    // Column sebagai container utama dengan background dan padding
    Column(
        modifier = Modifier
            //.background(Color(0xFFE6F2E8)) // Warna background hijau mint
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        // Teks Judul "Staff"
        Text(
            text = "Staff",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid 2x2 yang dibuat secara manual dengan Row dan Column
        // Baris pertama
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kolom pertama di baris pertama
            Box(modifier = Modifier.weight(1.05f)) {
                StaffMemberItem(staff = staffList[0])
            }
            // Kolom kedua di baris pertama
            Box(modifier = Modifier.weight(1f)) {
                StaffMemberItem(staff = staffList[1])
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Baris kedua
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kolom pertama di baris kedua
            Box(modifier = Modifier.weight(1.05f)) {
                StaffMemberItem(staff = staffList[2])
            }
            // Kolom kedua di baris kedua
            Box(modifier = Modifier.weight(1f)) {
                StaffMemberItem(staff = staffList[3])
            }
        }
    }
}

/**
 * Composable untuk menampilkan satu item staff.
 * Terdiri dari placeholder gambar dan teks nama/jabatan.
 */
@Composable
fun StaffMemberItem(staff: Staff) {
    // PERUBAHAN 1: Mengubah alignment seluruh baris menjadi rata atas (Top)
    Row(
        verticalAlignment = Alignment.Top
    ) {
        // Placeholder untuk gambar (tidak ada perubahan)
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0E0E0))
        )

        Spacer(modifier = Modifier.width(8.dp))

        // PERUBAHAN 2: Menghapus modifier height dan menggantinya dengan padding atas,
        // serta menambahkan arrangement agar ada jarak antar teks.
        Column(
            modifier = Modifier.padding(top = 4.dp), // Memberi sedikit jarak dari atas
            verticalArrangement = Arrangement.spacedBy(4.dp) // Jarak antara nama dan role
        ) {
            Text(
                text = staff.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.Black,
                maxLines = 3
            )
            Text(
                text = staff.role,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

/**
 * Fungsi Preview untuk melihat tampilan di Android Studio.
 */
@Preview(showBackground = true)
@Composable
fun StaffLayoutPreview() {
    StaffInfo()
}