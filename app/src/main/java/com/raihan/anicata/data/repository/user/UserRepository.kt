package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.user.UserDataSource
import com.raihan.anicata.data.model.auth.UserData
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun syncUser(user: UserData): Flow<ResultWrapper<Boolean>>
    fun getCurrentUserId(): String?
}

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {

    override fun syncUser(user: UserData): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.syncUserData(user)
        }
    }

    override fun getCurrentUserId(): String? {
        return dataSource.getCurrentUserId()
    }
}