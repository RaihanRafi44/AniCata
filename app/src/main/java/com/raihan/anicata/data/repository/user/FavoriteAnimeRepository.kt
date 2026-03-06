package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.user.FavoriteAnimeDataSource
import com.raihan.anicata.data.mapper.anime.toUserFavoriteAnime
import com.raihan.anicata.data.model.anime.full.AnimeData
import com.raihan.anicata.data.model.storage.UserFavoriteAnime
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface FavoriteAnimeRepository {

    fun saveAnimeToFavorite(anime: AnimeData): Flow<ResultWrapper<Boolean>>
    fun getAnimeFavorite(): Flow<ResultWrapper<List<UserFavoriteAnime>>>
    fun removeAnimeFromFavorite(id: String): Flow<ResultWrapper<Boolean>>
    fun isAnimeFavorites(id: String): Flow<ResultWrapper<Boolean>>
}

class FavoriteAnimeRepositoryImpl(
    private val dataSource: FavoriteAnimeDataSource
) : FavoriteAnimeRepository {

    override fun saveAnimeToFavorite(anime: AnimeData): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {

            val dataToSave = anime.toUserFavoriteAnime()

            dataSource.addToFavoriteAnime("anime_favorites", dataToSave, dataToSave.id)
        }
    }

    override fun getAnimeFavorite(): Flow<ResultWrapper<List<UserFavoriteAnime>>> {
        return proceedFlow {

            val list = dataSource.getFavoriteAnimeItems("anime_favorites", UserFavoriteAnime::class.java)

            list.sortedByDescending { it.savedAt }
        }
    }

    override fun removeAnimeFromFavorite(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.removeFromFavoriteAnime("anime_favorites", id)
        }
    }

    override fun isAnimeFavorites(id: String): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.isFavoriteAnimeSaved("anime_favorites", id)
        }
    }
}