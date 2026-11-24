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
import com.raihan.anicata.data.model.anime.characters.CharacterAnime
import com.raihan.anicata.utils.ResultWrapper

@Composable
fun CharacterListLayout(
    charactersResult: ResultWrapper<List<CharacterAnime>>,
    modifier: Modifier = Modifier
) {
    when (charactersResult) {
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
            val characters = charactersResult.payload.orEmpty()
            if (characters.isNotEmpty()) {
                CharacterListView(characters = characters, modifier = modifier)
            } else {
                Text(
                    text = "No characters found.",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 13.5.sp
                )
            }
        }
        is ResultWrapper.Error -> {
            Text(
                text = "Failed to load characters: ${charactersResult.exception?.message}",
                modifier = modifier.padding(16.dp),
                color = Color.Red,
                fontSize = 13.sp
            )
        }
        is ResultWrapper.Empty -> {
            Text(
                text = "No characters found.",
                modifier = modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 13.5.sp
            )
        }
        is ResultWrapper.Idle -> {}
    }
}

@Composable
private fun CharacterListView(
    characters: List<CharacterAnime>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val itemsToShow = if (isExpanded) characters else characters.take(4)

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        SectionHeaderTitle(
            title = "Characters & Voice Actors",
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsToShow.forEach { data ->
                CharacterListItem(info = data)
            }
        }

        if (characters.size > 4) {
            Spacer(modifier = Modifier.height(10.dp))
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
fun CharacterListItem(info: CharacterAnime) {
    val themeColor = Color(0xFF006A60)
    val japaneseVA = info.voiceActors.find { it.language == "Japanese" }
        ?: info.voiceActors.firstOrNull()

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FA),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Sisi Kiri: Info Karakter
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = info.character?.images?.jpg?.imageUrl,
                    contentDescription = info.character?.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(58.dp)
                        .height(76.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE0E0E0))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = info.character?.name ?: "Unknown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp,
                        color = Color(0xFF191C1E),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    info.role?.let { role ->
                        val isMain = role.equals("Main", ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (isMain) Color(0xFFE0F2F1) else Color(0xFFF0F0F0),
                            contentColor = if (isMain) themeColor else Color(0xFF616161)
                        ) {
                            Text(
                                text = role.uppercase(),
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Sisi Kanan: Info Pengisi Suara (Japanese VA)
            if (japaneseVA != null) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Text(
                            text = japaneseVA.person?.name ?: "Unknown",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = Color(0xFF191C1E),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = japaneseVA.language ?: "Japanese",
                            color = Color.Gray,
                            fontSize = 11.5.sp,
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    AsyncImage(
                        model = japaneseVA.person?.images?.jpg?.imageUrl,
                        contentDescription = japaneseVA.person?.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(58.dp)
                            .height(76.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFE0E0E0))
                    )
                }
            }
        }
    }
}
