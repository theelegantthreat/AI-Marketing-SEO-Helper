package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.data.local.entity.CompetitorEntity
import com.example.data.local.entity.SavedContentEntity
import com.example.data.local.entity.SavedKeywordEntity
import com.example.data.local.entity.SavedNicheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketingDao {
    // Saved Niches
    @Query("SELECT * FROM saved_niches ORDER BY savedAtMillis DESC")
    fun getAllSavedNiches(): Flow<List<SavedNicheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNiche(niche: SavedNicheEntity)

    @Query("DELETE FROM saved_niches WHERE id = :id")
    suspend fun deleteNicheById(id: String)

    // Saved Keywords
    @Query("SELECT * FROM saved_keywords ORDER BY savedAtMillis DESC")
    fun getAllSavedKeywords(): Flow<List<SavedKeywordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyword(keyword: SavedKeywordEntity)

    @Query("DELETE FROM saved_keywords WHERE id = :id")
    suspend fun deleteKeywordById(id: String)

    // Saved Content / Titles & Posts
    @Query("SELECT * FROM saved_content ORDER BY createdAtMillis DESC")
    fun getAllSavedContent(): Flow<List<SavedContentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: SavedContentEntity)

    @Query("DELETE FROM saved_content WHERE id = :id")
    suspend fun deleteContentById(id: String)

    // Competitors
    @Query("SELECT * FROM competitors ORDER BY lastUpdatedMillis DESC")
    fun getAllCompetitors(): Flow<List<CompetitorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitor(competitor: CompetitorEntity)

    @Query("DELETE FROM competitors WHERE id = :id")
    suspend fun deleteCompetitorById(id: String)
}

