package com.raihan.anicata.ui.archive.manga

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveMangaScreenBookmark(
    viewModel: ArchiveMangaViewModel = koinViewModel(),
    onMangaClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    onDeleteClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getBookmarksManga()
    }

    val bookmarksState by viewModel.bookmarkMangaState.collectAsState()
    val selectedIds by viewModel.selectedId.collectAsState()
    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isSelectionMode) "${selectedIds.size} Dipilih" else "Bookmark",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                windowInsets = WindowInsets(0,0,0,0),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isSelectionMode) {
                                viewModel.clearSelection()
                            } else {
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSelectionMode) Icons.Default.Close else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (isSelectionMode) "Batal" else "Kembali"
                        )
                    }
                },
                actions = {
                    if (isSelectionMode && selectedIds.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.deleteSelectedBookmarks()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            when (val state = bookmarksState) {
                is ResultWrapper.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ResultWrapper.Error -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Gagal memuat data: ${state.exception?.message}")
                    }
                }
                is ResultWrapper.Success -> {
                    val data = state.payload

                    if (data.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                                contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Belum ada manga di bookmark")
                        }
                    } else {
                        ArchiveMangaListBookmarkLayout(
                            mangaList = data,
                            selectedIds = selectedIds,
                            isSelectionMode = isSelectionMode,
                            onMangaClick = { idManga ->
                                if (isSelectionMode) {
                                    viewModel.toggleSelection(idManga)
                                } else {
                                    val idInt = idManga.toIntOrNull()
                                        ?: 0
                                onMangaClick(idInt)
                                }
                            },
                            onMangaLongClick = { idManga ->
                                if (!isSelectionMode) {
                                    viewModel.startSelection(idManga)
                                }
                            }
                        )
                    }
                }
                else -> {}
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}