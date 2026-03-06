package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.user.FavoriteMangaDataSource
import com.raihan.anicata.data.mapper.manga.toUserFavoriteManga
import com.raihan.anicata.data.model.manga.full.MangaDetailFull
import com.raihan.anicata.data.model.storage.UserFavoriteManga
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FavoriteMangaRepository {

    fun saveMangaToFavorite(manga: MangaDetailFull): Flow<ResultWrapper<Boolean>>
    fun getMangaFavorite(): Flow<ResultWrapper<List<UserFavoriteManga>>>
    fun removeMangaFromFavorite(id: String): Flow<ResultWrapper<Boolean>>
    fun isMangaFavorites(id: String): Flow<ResultWrapper<Boolean>>
}

class FavoriteMangaRepositoryImpl(
    private val dataSource: FavoriteMangaDataSource
) : FavoriteMangaRepository {
    override fun saveMangaToFavorite(manga: MangaDetailFull): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val dataToSave = manga.toUserFavoriteManga()

            dataSource.addToFavoriteManga("manga_favorites", dataToSave, dataToSave.id)
        }
    }

    override fun getMangaFavorite(): Flow<ResultWrapper<List<UserFavoriteManga>>> {
        return proceedFlow {
            val list = dataSource.getFavoriteMangaItems("manga_favorites", UserFavoriteManga::class.java)

            list.sortedByDescending { it.savedAt }
        }
    }

    override fun removeMangaFromFavorite(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.removeFromFavoriteManga("manga_favorites", id)
        }
    }

    override fun isMangaFavorites(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.isFavoriteMangaSaved("manga_favorites", id)
        }
    }
}