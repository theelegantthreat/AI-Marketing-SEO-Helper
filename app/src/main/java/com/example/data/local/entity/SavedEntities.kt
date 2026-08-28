package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_niches")
data class SavedNicheEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val growthRatePercent: Int,
    val competitionLevel: String,
    val monetizationPotential: String,
    val geoAeoReadinessScore: Int,
    val estimatedMonthlySearchVolume: String,
    val avgCpcUsd: Double,
    val subNichesCsv: String,
    val targetAudience: String,
    val savedAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_keywords")
data class SavedKeywordEntity(
    @PrimaryKey val id: String,
    val keyword: String,
    val seedTopic: String,
    val wordCount: Int,
    val monthlySearchVolume: Int,
    val competitionScore: Int,
    val cpcUsd: Double,
    val intent: String,
    val geoCitationLikelihood: Int,
    val difficultyLabel: String,
    val savedAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_content")
data class SavedContentEntity(
    @PrimaryKey val id: String,
    val title: String,
    val keyword: String,
    val style: String,
    val estimatedCtrPercent: Double,
    val facebookCopy: String,
    val instagramCopy: String,
    val twitterCopy: String,
    val hashtagsCsv: String,
    val createdAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "competitors")
data class CompetitorEntity(
    @PrimaryKey val id: String,
    val brandName: String,
    val websiteDomain: String,
    val nicheCategory: String,
    val avgLikes: Int,
    val avgShares: Int,
    val avgComments: Int,
    val postingFrequency: Double,
    val engagementRate: Double,
    val sentimentScore: Int,
    val isLiveTracking: Boolean,
    val lastUpdatedMillis: Long = System.currentTimeMillis()
)

