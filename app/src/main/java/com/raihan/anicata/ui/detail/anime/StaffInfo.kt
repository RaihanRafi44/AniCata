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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.raihan.anicata.data.model.anime.staff.StaffAnime
import com.raihan.anicata.utils.ResultWrapper

@Composable
fun StaffInfo(
    staffResult: ResultWrapper<List<StaffAnime>>,
    modifier: Modifier = Modifier
) {
    when (staffResult) {
        is ResultWrapper.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = Color(0xFF006A60)
                )
            }
        }
        is ResultWrapper.Success -> {
            val staffList = staffResult.payload.orEmpty()
            if (staffList.isNotEmpty()) {
                StaffListView(staffList = staffList, modifier = modifier)
            } else {
                Text(
                    text = "No staff found.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 13.5.sp
                )
            }
        }
        is ResultWrapper.Error -> {
            Text(
                text = "Failed to load staff: ${staffResult.exception?.message}",
                modifier = modifier.padding(16.dp),
                color = Color.Red,
                fontSize = 13.sp
            )
        }
        is ResultWrapper.Empty -> {
            Text(
                text = "No staff found.",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 13.5.sp
            )
        }
        is ResultWrapper.Idle -> {}
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
        modifier = modifier.padding(16.dp)
    ) {
        SectionHeaderTitle(
            title = "Staff & Creators",
            modifier = Modifier.padding(bottom = 12.dp)
        )

        itemsToShow.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
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
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (staffList.size > 4) {
            Spacer(modifier = Modifier.height(6.dp))
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

@Composable
fun StaffMemberItem(staff: StaffAnime) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FA),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = staff.person?.images?.jpg?.imageUrl,
                contentDescription = staff.person?.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE0E0E0))
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = staff.person?.name ?: "Unknown",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF191C1E),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = staff.positions?.joinToString(", ") ?: "Staff",
                    fontSize = 11.5.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
