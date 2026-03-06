package com.raihan.anicata.data.datasource.user

import com.raihan.anicata.data.source.firebase.FirebaseService

interface LibraryDataSource {

    suspend fun addToLibrary(collectionName: String, data: Any, docId: String): Boolean

    suspend fun removeFromLibrary(collectionName: String, docId: String): Boolean

    suspend fun isItemSaved(collectionName: String, docId: String): Boolean

    suspend fun <T> getLibraryItems(collectionName: String, clazz: Class<T>): List<T>
}

class LibraryDataSourceImpl(private val service: FirebaseService) : LibraryDataSource {

    override suspend fun addToLibrary(collectionName: String, data: Any, docId: String): Boolean {

        return service.saveToLibrary(collectionName, data, docId)
    }

    override suspend fun removeFromLibrary(collectionName: String, docId: String): Boolean {
        return service.removeFromLibrary(collectionName, docId)
    }

    override suspend fun isItemSaved(collectionName: String, docId: String): Boolean {
        return service.isItemSaved(collectionName, docId)
    }

    override suspend fun <T> getLibraryItems(collectionName: String, clazz: Class<T>): List<T> {
        return service.getUserLibrary(collectionName, clazz)
    }
}