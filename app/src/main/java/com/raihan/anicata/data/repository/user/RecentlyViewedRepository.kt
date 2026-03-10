package com.raihan.anicata.data.repository.user

import com.raihan.anicata.data.datasource.local.RecentlyViewedDataSource
import com.raihan.anicata.data.mapper.ui.toListRecentlyViewed
import com.raihan.anicata.data.mapper.ui.toRecentlyViewedEntity
import com.raihan.anicata.data.model.storage.RecentlyViewed
import com.raihan.anicata.data.source.local.database.entity.RecentlyViewedEntity
import com.raihan.anicata.utils.ResultWrapper
import com.raihan.anicata.utils.proceed
import com.raihan.anicata.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface RecentlyViewedRepository {

    fun getRecentlyViewed(): Flow<ResultWrapper<List<RecentlyViewed>>>

    fun createRecentlyViewed(recentlyViewed: RecentlyViewed): Flow<ResultWrapper<Boolean>>

    fun deleteRecentlyViewed(recentlyViewed: RecentlyViewed): Flow<ResultWrapper<Boolean>>

    fun deleteAll() : Flow<ResultWrapper<Boolean>>

}

class RecentlyViewedRepositoryImpl(

    private val dataSource: RecentlyViewedDataSource

) : RecentlyViewedRepository {
    override fun getRecentlyViewed(): Flow<ResultWrapper<List<RecentlyViewed>>> {
        return dataSource.getRecentlyViewed()
            .map {
                proceed {
                    it.toListRecentlyViewed()
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.catch {
                emit(ResultWrapper.Error(Exception(it)))
            }.onStart {
                emit(ResultWrapper.Loading())
            }
    }

    override fun createRecentlyViewed(recentlyViewed: RecentlyViewed): Flow<ResultWrapper<Boolean>> {
        return recentlyViewed.id.let { id ->
            proceedFlow {
                val affectedRow =
                    dataSource.insertRecentlyViewed(
                        RecentlyViewedEntity(
                            id = id,
                            title = recentlyViewed.title,
                            imageUrl = recentlyViewed.imageUrl,
                            type = recentlyViewed.type,
                            timestamp = System.currentTimeMillis()
                        ),
                    )
                affectedRow > 0
            }
        }
    }

    override fun deleteRecentlyViewed(recentlyViewed: RecentlyViewed): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.deleteRecentlyViewed(recentlyViewed.toRecentlyViewedEntity()) > 0
        }
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.deleteAll()
            true
        }
    }
}