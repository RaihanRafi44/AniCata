package com.raihan.anicata.ui.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreenLayout(
    onClose: () -> Unit,
    onSearchSubmitted: (String) -> Unit,
    onAnimeClick: (Int) -> Unit,
    onMangaClick: (Int) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResultsState by viewModel.searchResults.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var selectedFilter by remember { mutableIntStateOf(0) } // 0: All, 1: Anime, 2: Manga

    val keyboardActions = KeyboardActions(
        onSearch = {
            if (searchQuery.isNotBlank()) {
                keyboardController?.hide()
                onSearchSubmitted(searchQuery)
            }
        }
    )

    val popularSuggestions = listOf(
        "One Piece", "Sousou no Frieren", "Naruto",
        "Jujutsu Kaisen", "Solo Leveling", "Demon Slayer",
        "Attack on Titan", "Chainsaw Man"
    )

    val themeColor = Color(0xFF006A60)

    // Center-aligned vertical Search Modal (Concept 3: Morphing Elastic Dock / Dynamic Spotlight)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .heightIn(max = 560.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFFCFCFD),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // --- MORPHING DOCK HEADER ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE0F2F1),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = themeColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "AniCata Search",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = themeColor,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = "Find your favorite anime & manga",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        onClick = onClose,
                        shape = CircleShape,
                        color = Color(0xFFF1F5F9),
                        contentColor = Color(0xFF475569),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // --- SEARCH FIELD ---
                SearchTextField(
                    query = searchQuery,
                    onQueryChange = viewModel::onQueryChange,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = keyboardActions
                )

                // --- CATEGORY FILTER BAR ---
                SearchCategoryFilterBar(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )

                // --- CONTENT BODY ---
                if (searchQuery.isBlank()) {
                    // IDLE STATE: Popular / Trending Suggestions
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = "Trending",
                                tint = themeColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Popular Searches",
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            popularSuggestions.forEach { suggestion ->
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFF1F5F9),
                                    contentColor = Color(0xFF1E293B),
                                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                    modifier = Modifier.clickable {
                                        viewModel.onQueryChange(suggestion)
                                        onSearchSubmitted(suggestion)
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "# ",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = themeColor
                                        )
                                        Text(
                                            text = suggestion,
                                            fontSize = 12.5.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // SEARCH RESULTS / LOADING STATE
                    when (val state = searchResultsState) {
                        is ResultWrapper.Loading -> {
                            SearchShimmerLoadingList(modifier = Modifier.fillMaxWidth())
                        }
                        is ResultWrapper.Success -> {
                            val filteredList = remember(state.payload, selectedFilter) {
                                val list = state.payload.orEmpty()
                                when (selectedFilter) {
                                    1 -> list.filter { it.category.equals("Anime", ignoreCase = true) }
                                    2 -> list.filter { it.category.equals("Manga", ignoreCase = true) }
                                    else -> list
                                }
                            }

                            if (filteredList.isNotEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    SearchResultList(
                                        animeList = filteredList.take(3),
                                        modifier = Modifier.fillMaxWidth(),
                                        onItemClick = { animeInfo ->
                                            if (animeInfo.category.equals("Anime", ignoreCase = true)) {
                                                onAnimeClick(animeInfo.id)
                                            } else if (animeInfo.category.equals("Manga", ignoreCase = true)) {
                                                onMangaClick(animeInfo.id)
                                            }
                                        }
                                    )

                                    // View All Results Button
                                    Surface(
                                        onClick = { onSearchSubmitted(searchQuery) },
                                        shape = RoundedCornerShape(16.dp),
                                        color = themeColor,
                                        contentColor = Color.White,
                                        shadowElevation = 2.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(vertical = 11.dp, horizontal = 14.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "View all results for \"$searchQuery\"",
                                                fontSize = 13.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No results found for \"$searchQuery\"",
                                        color = Color(0xFF64748B),
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        is ResultWrapper.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = state.exception?.message ?: "An error occurred while searching",
                                    color = Color(0xFFEF4444),
                                    fontSize = 13.5.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        is ResultWrapper.Empty -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No results found for \"$searchQuery\"",
                                    color = Color(0xFF64748B),
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        is ResultWrapper.Idle -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun SearchCategoryFilterBar(
    selectedFilter: Int,
    onFilterSelected: (Int) -> Unit
) {
    val themeColor = Color(0xFF006A60)
    val filters = listOf("All", "Anime", "Manga")

    Surface(
        shape = CircleShape,
        color = Color(0xFFF1F5F9),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            val segmentWidth = maxWidth / filters.size
            val indicatorOffset by animateDpAsState(
                targetValue = segmentWidth * selectedFilter,
                animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
                label = "spotlight_tab_indicator"
            )

            // Fluid Sliding Active Indicator Track
            Surface(
                modifier = Modifier
                    .width(segmentWidth)
                    .height(34.dp)
                    .offset(x = indicatorOffset),
                shape = CircleShape,
                color = themeColor,
                shadowElevation = 2.dp
            ) {}

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                filters.forEachIndexed { index, label ->
                    val isSelected = selectedFilter == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(34.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onFilterSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF475569),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
