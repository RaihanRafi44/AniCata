package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.user.LibraryDataSource
import com.raihan.anicata.data.mapper.anime.toUserSavedAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    // Fungsi khusus Anime Bookmark
    fun saveAnimeToBookmark(anime: AnimeData): Flow<ResultWrapper<Boolean>>
    fun getAnimeBookmarks(): Flow<ResultWrapper<List<UserBookmarkAnime>>>
    fun removeAnimeFromBookmark(id: String): Flow<ResultWrapper<Boolean>>
    fun isAnimeBookmarked(id: String): Flow<ResultWrapper<Boolean>>
}

class LibraryRepositoryImpl(
    private val dataSource: LibraryDataSource
) : LibraryRepository {

    override fun saveAnimeToBookmark(anime: AnimeData): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            // 1. LAKUKAN MAPPING DI SINI
            // Repository mengubah Domain Model (AnimeData) -> Storage Model (UserSavedAnime)
            val dataToSave = anime.toUserSavedAnime()

            // 2. Kirim ke DataSource
            dataSource.addToLibrary("anime_bookmarks", dataToSave, dataToSave.id)
        }
    }

    override fun getAnimeBookmarks(): Flow<ResultWrapper<List<UserBookmarkAnime>>> {
        return proceedFlow {

            // 1. Ambil data dari DataSource
            val list = dataSource.getLibraryItems("anime_bookmarks", UserBookmarkAnime::class.java)

            // 2. LAKUKAN SORTING DI SINI (Terbaru -> Terlama)
            // Menggunakan 'sortedByDescending' pada field 'savedAt'
            list.sortedByDescending { it.savedAt }
        }
    }

    override fun removeAnimeFromBookmark(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.removeFromLibrary("anime_bookmarks", id)
        }
    }

    override fun isAnimeBookmarked(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.isItemSaved("anime_bookmarks", id)
        }
    }
}