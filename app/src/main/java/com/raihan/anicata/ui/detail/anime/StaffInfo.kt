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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.utils.ResultWrapper
import kotlin.compareTo
import kotlin.text.chunked
import kotlin.text.forEach

@Composable
fun StaffInfo(
    staffResult: ResultWrapper<List<StaffAnime>>,
    modifier: Modifier = Modifier
) {

    when (staffResult) {
        is ResultWrapper.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is ResultWrapper.Success -> {
            val staffList = staffResult.payload.orEmpty()
            if (staffList.isNotEmpty()) {
                StaffListView(staffList = staffList, modifier = modifier)
            } else {
                Text(
                    text = "No staff found.",
                    modifier = modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        is ResultWrapper.Error -> {
            Text(
                text = "Failed to load staff: ${staffResult.exception?.message}",
                modifier = modifier.padding(16.dp),
                color = Color.Red
            )
        }
        is ResultWrapper.Empty -> {
            Text(
                text = "No staff found.",
                modifier = modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
        is ResultWrapper.Idle -> { /* Tidak melakukan apa-apa */ }

    }

}

@Composable
private fun StaffListView(
    staffList: List<StaffAnime>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val itemsToShow = if (staffList.size > 4 && !isExpanded) {
        staffList.take(4)
    } else {
        staffList
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Staff",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        itemsToShow.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { staff ->
                    Box(modifier = Modifier.weight(1f)) {
                        StaffMemberItem(staff = staff)
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Tampilkan tombol jika jumlah staf lebih dari 4
        if (staffList.size > 4) {
            // PERUBAHAN: Bagian ini disamakan dengan model CharvaInfo.kt
            // Menggunakan Text dengan modifier clickable, bukan TextButton.
            Text(
                text = if (isExpanded) "View Less" else "View More",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isExpanded = !isExpanded
                    }
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun StaffMemberItem(staff: StaffAnime) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = staff.person?.images?.jpg?.imageUrl,
            contentDescription = staff.person?.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(64.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0E0E0))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.padding(top = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = staff.person?.name ?: "Unknown",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color.Black,
                maxLines = 3
            )
            Text(
                text = staff.positions?.joinToString(", ") ?: "N/A",
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
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
    //StaffInfo()
}
