package com.raihan.anicata.data.datasource.user

import com.raihan.anicata.data.model.auth.UserData
import com.raihan.anicata.data.source.firebase.FirebaseService

interface UserDataSource {
    suspend fun syncUserData(user: UserData): Boolean
    fun getCurrentUserId(): String?
}

class UserDataSourceImpl(private val service: FirebaseService) : UserDataSource {
    override suspend fun syncUserData(user: UserData): Boolean {
        return service.syncUserToFirestore(user)
    }

    override fun getCurrentUserId(): String? {
        return service.currentUserId
    }
}