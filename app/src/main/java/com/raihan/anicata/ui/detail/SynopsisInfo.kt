package com.raihan.anicata.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SynopsisInfo(modifier: Modifier = Modifier) {
    val synopsisText = "After a horrific alchemy experiment goes wrong in the Elric household, " +
            "brothers Edward and Alphonse are left in a catastrophic new reality. " +
            "Ignoring the alchemical principle banning human transmutation, the boys " +
            "attempted to bring their recently deceased mother back to life. Instead, " +
            "they suffered brutal personal loss: Alphonse’s body disintegrated while " +
            "Edward lost a leg and then sacrificed an arm to keep Alphonse’s soul in the " +
            "physical realm by binding it to a hulking suit of armor.\n\n" +
            "The brothers are rescued by their neighbor Pinako Rockbell and her " +
            "granddaughter Winry. Known as a bio-mechanical engineering prodigy, " +
            "Winry creates prosthetic limbs for Edward by utilizing \"automail,\" a tough, " +
            "versatile metal used in robots and combat armor. After years of training, " +
            "the Elric brothers set off on a quest to restore their bodies by locating the " +
            "Philosopher’s Stone—a powerful gem that allows an alchemist to defy the " +
            "traditional laws of Equivalent Exchange."

    //val backgroundColor = Color(0xFFE8F0E8)
    val textColor = Color(0xFF333333)
    val dividerColor = Color.Gray.copy(alpha = 0.5f)

    Column(
        modifier = modifier // Modifier ini membuat komponen fleksibel
            //.background(color = backgroundColor)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Synopsis",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = dividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = synopsisText,
            fontSize = 13.sp,
            color = textColor,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true, name = "Synopsis View Preview")
@Composable
fun SynopsisPreview() {
    SynopsisInfo()
    /*MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp), // Padding agar tidak menempel di tepi preview
            contentAlignment = Alignment.Center
        ) {
            // PERUBAHAN: Memanggil SynopsisInfo() tanpa modifier lebar.
            // Komponen akan secara otomatis menyesuaikan lebarnya dengan konten.
            SynopsisInfo()
        }
    }*/
}