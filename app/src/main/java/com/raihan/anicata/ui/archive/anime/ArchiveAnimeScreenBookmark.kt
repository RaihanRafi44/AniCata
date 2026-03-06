package com.raihan.anicata.ui.archive.anime

import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raihan.anicata.utils.ResultWrapper
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveAnimeScreenBookmark(

    //animeList: List<DummyArchiveAnimeBookmark> = dummy_anime_archive_bookmark_data,
    viewModel: ArchiveAnimeViewModel = koinViewModel(),
    onAnimeClick: (Int) -> Unit,
    onNavigateBack: () -> Unit ,
    onDeleteClick: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getBookmarks()
    }

    val bookmarkState by viewModel.bookmarkState.collectAsState()
    val selectedIds by viewModel.selectedIds.collectAsState()       // Observasi Seleksi
    val isSelectionMode by viewModel.isSelectionMode.collectAsState() // Observasi Mode
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
                windowInsets = WindowInsets(0, 0, 0, 0),
                navigationIcon = {
                    IconButton(onClick = {
                        // Jika mode seleksi, tombol back berfungsi untuk BATAL seleksi
                        if (isSelectionMode) {
                            viewModel.clearSelection()
                        } else {
                            onNavigateBack()
                        }
                    }) {
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

            when (val state = bookmarkState) {
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
                            Text(text = "Belum ada anime di bookmark")
                        }
                    } else {
                        ArchiveAnimeListBookmarkLayout(
                            animeList = data,
                            selectedIds = selectedIds,
                            isSelectionMode = isSelectionMode,
                            onAnimeClick = { idString ->
                                if (isSelectionMode) {
                                    // Jika sedang mode seleksi, klik = pilih/batal pilih
                                    viewModel.toggleSelection(idString)
                                } else {
                                    // Jika mode biasa, klik = navigasi ke detail
                                    val idInt = idString.toIntOrNull() ?: 0
                                    onAnimeClick(idInt)
                                }
                            },
                            onAnimeLongClick = { idString ->
                                // Jika belum mode seleksi, mulai seleksi
                                if (!isSelectionMode) {
                                    viewModel.startSelection(idString)
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
