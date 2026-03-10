package com.raihan.anicata.data.datasource.local

import com.raihan.anicata.data.source.local.database.dao.RecentlyViewedDao
import com.raihan.anicata.data.source.local.database.entity.RecentlyViewedEntity
import kotlinx.coroutines.flow.Flow

interface RecentlyViewedDataSource {

    fun getRecentlyViewed(): Flow<List<RecentlyViewedEntity>>

    suspend fun insertRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Long

    suspend fun updateRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int

    suspend fun deleteRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int

    suspend fun deleteAll()

}

class RecentlyViewedDataSourceImpl(

    private val dao: RecentlyViewedDao

) : RecentlyViewedDataSource {

    override fun getRecentlyViewed(): Flow<List<RecentlyViewedEntity>> = dao.getRecentlyViewed()

    override suspend fun insertRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Long = dao.insertRecentlyViewed(recentlyViewed)

    override suspend fun updateRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int = dao.updateRecentlyViewed(recentlyViewed)

    override suspend fun deleteRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int = dao.deleteRecentlyViewed(recentlyViewed)

    override suspend fun deleteAll() = dao.deleteAll()
}