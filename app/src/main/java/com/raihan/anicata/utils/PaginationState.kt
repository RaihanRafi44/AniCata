package com.raihan.anicata.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class PaginationState(
    initialPage: Int,
    initialTotalPages: Int,
    val visiblePages: Int
) {
    /**
     * Halaman yang sedang dipilih (1-based).
     * Dibuat private set agar hanya bisa diubah dari dalam kelas ini.
     */
    var currentPage by mutableStateOf(initialPage)
        private set

    /**
     * Jumlah total halaman.
     * Bisa di-set dari luar, agar UI bisa mengupdatenya saat data API masuk.
     */
    var totalPages by mutableStateOf(initialTotalPages)

    /**
     * Halaman pertama yang ditampilkan di deretan tombol (misal: 1, 4, 7).
     */
    var startPage by mutableStateOf(1)
        private set

    /**
     * Menghitung nilai 'startPage' maksimum yang mungkin.
     */
    private val maxStartPage: Int
        get() = (totalPages - visiblePages + 1).coerceAtLeast(1)

    init {
        // Inisialisasi startPage berdasarkan halaman awal
        updateStartPageWindow(initialPage)
    }

    /**
     * Fungsi utama yang dipanggil UI ketika user mengganti halaman.
     *
     * @param newPage Halaman baru yang dituju.
     */
    fun onPageChange(newPage: Int) {
        if (newPage == currentPage || newPage < 1 || newPage > totalPages) return

        currentPage = newPage
        updateStartPageWindow(newPage)
    }

    /**
     * Logika internal untuk menggeser "jendela" tombol halaman (startPage).
     */
    private fun updateStartPageWindow(newPage: Int) {
        val currentWindowEnd = startPage + visiblePages - 1

        if (newPage < startPage) {
            // Jika halaman baru ada di 'kiri' jendela, geser jendela ke kiri
            startPage = newPage.coerceIn(1, maxStartPage)
        } else if (newPage > currentWindowEnd) {
            // Jika halaman baru ada di 'kanan' jendela, geser jendela ke kanan
            startPage = (newPage - (visiblePages - 1)).coerceIn(1, maxStartPage)
        }
        // Jika di dalam jendela, startPage tidak berubah
    }
}

/**
 * Composable function untuk mengingat instance dari [PaginationState].
 *
 * @param initialPage Halaman awal.
 * @param totalPages Total halaman dari ViewModel (akan di-observe).
 * @param visiblePages Jumlah tombol angka yang terlihat.
 */
@Composable
fun rememberPaginationState(
    initialPage: Int = 1,
    totalPages: Int = 1,
    visiblePages: Int = 3
): PaginationState {

    // Ingat state-nya
    val state = remember {
        PaginationState(
            initialPage = initialPage,
            initialTotalPages = totalPages,
            visiblePages = visiblePages
        )
    }

    // Gunakan LaunchedEffect untuk meng-update totalPages di dalam state
    // jika nilainya berubah (misalnya setelah API call pertama selesai).
    LaunchedEffect(totalPages) {
        state.totalPages = totalPages
    }

    return state
}