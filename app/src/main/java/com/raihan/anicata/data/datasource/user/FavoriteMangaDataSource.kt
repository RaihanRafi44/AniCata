package com.raihan.anicata.data.datasource.user

import com.raihan.anicata.data.source.firebase.FirebaseService

interface FavoriteMangaDataSource {

    suspend fun addToFavoriteManga(collectionName: String, data: Any, docId: String): Boolean

    suspend fun removeFromFavoriteManga(collectionName: String, docId: String): Boolean

    suspend fun isFavoriteMangaSaved(collectionName: String, docId: String): Boolean

    suspend fun <T> getFavoriteMangaItems(collectionName: String, clazz: Class<T>): List<T>
}

class FavoriteMangaDataSourceImpl(private val service: FirebaseService) : FavoriteMangaDataSource {

    override suspend fun addToFavoriteManga(collectionName: String, data: Any, docId: String): Boolean {

        return service.saveToLibrary(collectionName, data, docId)
    }

    override suspend fun removeFromFavoriteManga(collectionName: String, docId: String): Boolean {
        return service.removeFromLibrary(collectionName, docId)
    }

    override suspend fun isFavoriteMangaSaved(collectionName: String, docId: String): Boolean {
        return service.isItemSaved(collectionName, docId)
    }

    override suspend fun <T> getFavoriteMangaItems(collectionName: String, clazz: Class<T>): List<T> {
        return service.getUserLibrary(collectionName, clazz)
    }
}