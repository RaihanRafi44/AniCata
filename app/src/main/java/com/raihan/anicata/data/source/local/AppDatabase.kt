package com.raihan.anicata.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raihan.anicata.data.source.local.database.dao.RecentlyViewedDao
import com.raihan.anicata.data.source.local.database.entity.RecentlyViewedEntity

@Database(
    entities = [RecentlyViewedEntity::class],
    version = 2,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun recentlyViewedDao(): RecentlyViewedDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun createInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Anicata.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
}
}