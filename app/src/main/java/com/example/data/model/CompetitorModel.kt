package com.example.data.model

data class CompetitorItem(
    val id: String,
    val brandName: String,
    val websiteDomain: String,
    val nicheCategory: String,
    val avgLikesPerPost: Int,
    val avgSharesPerPost: Int,
    val avgCommentsPerPost: Int,
    val postingFrequencyPerWeek: Double,
    val engagementRatePercent: Double,
    val sentimentScorePercent: Int, // e.g. 78% positive
    val sentimentBreakdown: SentimentSummary,
    val topHashtags: List<String>,
    val geoPresenceScore: Int, // 0 - 100 presence in AI answer citations
    val platformPresence: List<PlatformMetric>,
    val isLiveTracking: Boolean = true,
    val lastUpdatedMillis: Long = System.currentTimeMillis()
)

data class PlatformMetric(
    val platform: String,
    val followerCount: String,
    val weeklyGrowthPercent: Double,
    val activityStatus: String
)

data class SentimentSummary(
    val positivePercent: Int,
    val neutralPercent: Int,
    val negativePercent: Int
)
