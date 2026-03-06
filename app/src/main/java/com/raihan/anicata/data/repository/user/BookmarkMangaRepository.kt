package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.user.BookmarkMangaDataSource
import com.raihan.anicata.data.datasource.user.LibraryDataSource
import com.raihan.anicata.data.mapper.anime.toUserSavedAnime
import com.raihan.anicata.data.mapper.manga.toUserBookmarkManga
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.storage.UserBookmarkAnime
import com.raihan.anicata.data.model.storage.UserBookmarkManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface BookmarkMangaRepository {
    // Fungsi khusus Anime Bookmark
    fun saveMangaToBookmark(manga: MangaDetailFull): Flow<ResultWrapper<Boolean>>
    fun getMangaBookmarks(): Flow<ResultWrapper<List<UserBookmarkManga>>>
    fun removeMangaFromBookmark(id: String): Flow<ResultWrapper<Boolean>>
    fun isMangaBookmarked(id: String): Flow<ResultWrapper<Boolean>>
}

class BookmarkMangaRepositoryImpl(
    private val dataSource: BookmarkMangaDataSource
) : BookmarkMangaRepository {

    override fun saveMangaToBookmark(manga: MangaDetailFull): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {

            val dataToSave = manga.toUserBookmarkManga()

            dataSource.addToBookmarkManga("manga_bookmarks", dataToSave, dataToSave.id)
        }
    }

    override fun getMangaBookmarks(): Flow<ResultWrapper<List<UserBookmarkManga>>> {
        return proceedFlow {

            val list = dataSource.getBookmarkMangaItems("manga_bookmarks", UserBookmarkManga::class.java)

            list.sortedByDescending { it.savedAt }
        }
    }

    override fun removeMangaFromBookmark(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.removeFromBookmarkManga("manga_bookmarks", id)
        }
    }

    override fun isMangaBookmarked(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.isBookmarkMangaSaved("manga_bookmarks", id)
        }
    }
}