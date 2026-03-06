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
    /*var startPage by mutableStateOf(1)
        private set*/

    var startPage by mutableStateOf(initialStartPage)
        private set // Modifikasi dari

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

@Composable
fun rememberPaginationState(
    totalPages: Int = 1,
    visiblePages: Int = 3
): PaginationState {

    // 1. Simpan 'currentPage' menggunakan rememberSaveable.
    //    Ini adalah state yang akan selamat saat navigasi.
    var savedCurrentPage by rememberSaveable { mutableStateOf(1) }
    var savedStartPage by rememberSaveable { mutableStateOf(1) } // <-- TAMBAHKAN INI

    // 2. Buat objek state menggunakan 'remember'.
    //    Kita berikan 'savedCurrentPage' sebagai initialPage.
    val state = remember(totalPages, visiblePages) {
        PaginationState(
            initialPage = savedCurrentPage, // Gunakan halaman yang disimpan
            initialStartPage = savedStartPage,
            initialTotalPages = totalPages,
            visiblePages = visiblePages
        )
    }

    // 3. Sinkronkan kembali. Jika state internal berubah (misal dipanggil onPageChange),
    //    simpan nilai barunya ke 'savedCurrentPage'.
    LaunchedEffect(state.currentPage) {
        savedCurrentPage = state.currentPage
    }

    LaunchedEffect(state.startPage) { // <-- TAMBAHKAN BLOK INI
        savedStartPage = state.startPage
    }

    // 4. Update totalPages (seperti sebelumnya).
    LaunchedEffect(totalPages) {
        state.totalPages = totalPages
    }

    return state
}