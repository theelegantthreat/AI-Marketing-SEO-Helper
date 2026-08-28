package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.MarketingDao
import com.example.data.local.entity.CompetitorEntity
import com.example.data.local.entity.SavedContentEntity
import com.example.data.local.entity.SavedKeywordEntity
import com.example.data.local.entity.SavedNicheEntity

@Database(
    entities = [
        SavedNicheEntity::class,
        SavedKeywordEntity::class,
        SavedContentEntity::class,
        CompetitorEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marketingDao(): MarketingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "marketing_seo_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
