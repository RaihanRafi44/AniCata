package com.raihan.anicata.data.datasource.user

import com.raihan.anicata.data.source.firebase.FirebaseService

interface FavoriteAnimeDataSource {

    suspend fun addToFavoriteAnime(collectionName: String, data: Any, docId: String): Boolean

    suspend fun removeFromFavoriteAnime(collectionName: String, docId: String): Boolean

    suspend fun isFavoriteAnimeSaved(collectionName: String, docId: String): Boolean

    suspend fun <T> getFavoriteAnimeItems(collectionName: String, clazz: Class<T>): List<T>
}

class FavoriteAnimeDataSourceImpl(private val service: FirebaseService) : FavoriteAnimeDataSource {

    override suspend fun addToFavoriteAnime(collectionName: String, data: Any, docId: String): Boolean {

        return service.saveToLibrary(collectionName, data, docId)
    }

    override suspend fun removeFromFavoriteAnime(collectionName: String, docId: String): Boolean {
        return service.removeFromLibrary(collectionName, docId)
    }

    override suspend fun isFavoriteAnimeSaved(collectionName: String, docId: String): Boolean {
        return service.isItemSaved(collectionName, docId)
    }

    override suspend fun <T> getFavoriteAnimeItems(collectionName: String, clazz: Class<T>): List<T> {
        return service.getUserLibrary(collectionName, clazz)
    }
}