package com.raihan.anicata.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
class PaginationState(
    initialPage: Int,
    initialStartPage: Int,
    initialTotalPages: Int,
    val visiblePages: Int
) {
    /**
     * Halaman yang sedang dipilih (1-based).
     */
    var currentPage by mutableStateOf(initialPage)
        private set

    /**
     * Jumlah total halaman.
     */
    var totalPages by mutableStateOf(initialTotalPages)

    var startPage by mutableStateOf(initialStartPage)
        private set

    private val maxStartPage: Int
        get() = (totalPages - visiblePages + 1).coerceAtLeast(1)

    init {
        updateStartPageWindow(initialPage)
    }

    /**
     * Fungsi utama yang dipanggil UI ketika user mengganti halaman.
     *
     * @param newPage Halaman baru yang dituju.
     */
    fun onPageChange(newPage: Int) {
        if (newPage == currentPage || newPage < 1) return

        currentPage = newPage
        updateStartPageWindow(newPage)
    }

    /**
     * Logika internal untuk menggeser "jendela" tombol halaman (startPage).
     */
    fun updateStartPageWindow(newPage: Int) {
        val currentWindowEnd = startPage + visiblePages - 1

        if (newPage < startPage) {
            startPage = newPage.coerceIn(1, maxStartPage)
        } else if (newPage > currentWindowEnd) {
            startPage = (newPage - (visiblePages - 1)).coerceIn(1, maxStartPage)
        }
    }
}

@Composable
fun rememberPaginationState(
    totalPages: Int = 1,
    visiblePages: Int = 3
): PaginationState {

    var savedCurrentPage by rememberSaveable { mutableStateOf(1) }
    var savedStartPage by rememberSaveable { mutableStateOf(1) }

    val state = remember(visiblePages) {
        PaginationState(
            initialPage = savedCurrentPage,
            initialStartPage = savedStartPage,
            initialTotalPages = totalPages,
            visiblePages = visiblePages
        )
    }

    LaunchedEffect(state.currentPage) {
        savedCurrentPage = state.currentPage
    }

    LaunchedEffect(state.startPage) {
        savedStartPage = state.startPage
    }

    LaunchedEffect(totalPages) {
        if (totalPages > 0) {
            state.totalPages = totalPages
            state.updateStartPageWindow(state.currentPage)
        }
    }

    return state
}