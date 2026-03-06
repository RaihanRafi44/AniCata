package com.raihan.anicata.data.datasource.user

import com.raihan.anicata.data.source.firebase.FirebaseService

interface BookmarkMangaDataSource {

    suspend fun addToBookmarkManga(collectionName: String, data: Any, docId: String): Boolean

    suspend fun removeFromBookmarkManga(collectionName: String, docId: String): Boolean

    suspend fun isBookmarkMangaSaved(collectionName: String, docId: String): Boolean

    suspend fun <T> getBookmarkMangaItems(collectionName: String, clazz: Class<T>): List<T>
}

class BookmarkMangaDataSourceImpl(private val service: FirebaseService) : BookmarkMangaDataSource {

    override suspend fun addToBookmarkManga(collectionName: String, data: Any, docId: String): Boolean {

        return service.saveToLibrary(collectionName, data, docId)
    }

    override suspend fun removeFromBookmarkManga(collectionName: String, docId: String): Boolean {
        return service.removeFromLibrary(collectionName, docId)
    }

    override suspend fun isBookmarkMangaSaved(collectionName: String, docId: String): Boolean {
        return service.isItemSaved(collectionName, docId)
    }

    override suspend fun <T> getBookmarkMangaItems(collectionName: String, clazz: Class<T>): List<T> {
        return service.getUserLibrary(collectionName, clazz)
    }
}