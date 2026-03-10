package com.raihan.anicata.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.raihan.anicata.data.source.local.database.entity.RecentlyViewedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentlyViewedDao {

    @Query("SELECT * FROM recently_viewed ORDER BY timestamp DESC LIMIT 10")
    fun getRecentlyViewed(): Flow<List<RecentlyViewedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Long

    @Update
    suspend fun updateRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int

    @Delete
    suspend fun deleteRecentlyViewed(recentlyViewed: RecentlyViewedEntity): Int

    @Query("DELETE FROM recently_viewed")
    suspend fun deleteAll()

}