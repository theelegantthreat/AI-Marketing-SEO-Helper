package com.example.data.repository

import com.example.data.local.dao.MarketingDao
import com.example.data.local.entity.CompetitorEntity
import com.example.data.local.entity.SavedContentEntity
import com.example.data.local.entity.SavedKeywordEntity
import com.example.data.local.entity.SavedNicheEntity
import com.example.data.model.*
import com.example.data.service.GeminiMarketingService
import com.example.data.service.MarketingIntelligenceEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MarketingRepository(
    private val dao: MarketingDao,
    private val geminiService: GeminiMarketingService = GeminiMarketingService()
) {

    val savedNichesFlow: Flow<List<SavedNicheEntity>> = dao.getAllSavedNiches()
    val savedKeywordsFlow: Flow<List<SavedKeywordEntity>> = dao.getAllSavedKeywords()
    val savedContentFlow: Flow<List<SavedContentEntity>> = dao.getAllSavedContent()
    val trackedCompetitorsFlow: Flow<List<CompetitorEntity>> = dao.getAllCompetitors()

    fun isGeminiConfigured(): Boolean = geminiService.isConfigured

    suspend fun getTrendingNiches(category: String = "All"): List<NicheItem> {
        return MarketingIntelligenceEngine.generateTrendingNiches(category)
    }

    suspend fun generateLongTailKeywords(seedTopic: String, minWordCount: Int = 3): List<KeywordItem> {
        return MarketingIntelligenceEngine.generateLongTailKeywords(seedTopic, minWordCount)
    }

    suspend fun generateArticleTitles(keyword: String): List<ArticleTitleItem> {
        return MarketingIntelligenceEngine.generateArticleTitles(keyword)
    }

    suspend fun generateSocialPosts(title: String, keyword: String): SocialPostPackage {
        return MarketingIntelligenceEngine.generateSocialMediaPostPackage(title, keyword)
    }

    suspend fun getCompetitorData(): List<CompetitorItem> {
        return MarketingIntelligenceEngine.getCompetitorEngagementData()
    }

    suspend fun analyzeSentiment(commentText: String): SentimentAnalysisResult {
        return MarketingIntelligenceEngine.analyzeSentiment(commentText)
    }

    suspend fun getAnalyticsSummary(): MarketingAnalyticsSummary {
        return MarketingIntelligenceEngine.getAnalyticsSummary()
    }

    fun getAdPlatforms(): List<AdPlatformAccount> {
        return MarketingIntelligenceEngine.getAdPlatformAccounts()
    }

    suspend fun askGeminiMarketingExpert(prompt: String): Result<String> {
        val sysInstruction = """
            You are a world-class Principal Digital Marketing Strategist, SEO Authority, and GEO (Generative Engine Optimization) Specialist.
            Provide structured, high-ROI, data-driven actionable marketing insights, keyword recommendations, and content strategies.
        """.trimIndent()
        return geminiService.generateMarketingInsight(prompt, sysInstruction)
    }

    // Room Persistence Methods
    suspend fun toggleSaveNiche(niche: NicheItem, currentlySaved: Boolean) {
        if (currentlySaved) {
            dao.deleteNicheById(niche.id)
        } else {
            val entity = SavedNicheEntity(
                id = niche.id,
                title = niche.title,
                category = niche.category,
                summary = niche.summary,
                growthRatePercent = niche.growthRatePercent,
                competitionLevel = niche.competitionLevel.name,
                monetizationPotential = niche.monetizationPotential.name,
                geoAeoReadinessScore = niche.geoAeoReadinessScore,
                estimatedMonthlySearchVolume = niche.estimatedMonthlySearchVolume,
                avgCpcUsd = niche.avgCpcUsd,
                subNichesCsv = niche.subNiches.joinToString(","),
                targetAudience = niche.targetAudience
            )
            dao.insertNiche(entity)
        }
    }

    suspend fun toggleSaveKeyword(keyword: KeywordItem, currentlySaved: Boolean) {
        if (currentlySaved) {
            dao.deleteKeywordById(keyword.id)
        } else {
            val entity = SavedKeywordEntity(
                id = keyword.id,
                keyword = keyword.keyword,
                seedTopic = keyword.seedTopic,
                wordCount = keyword.wordCount,
                monthlySearchVolume = keyword.monthlySearchVolume,
                competitionScore = keyword.competitionScore,
                cpcUsd = keyword.cpcUsd,
                intent = keyword.intent.name,
                geoCitationLikelihood = keyword.geoCitationLikelihood,
                difficultyLabel = keyword.difficultyLabel
            )
            dao.insertKeyword(entity)
        }
    }

    suspend fun saveSocialContent(title: String, keyword: String, style: String, ctr: Double, pkg: SocialPostPackage) {
        val entity = SavedContentEntity(
            id = "content_${System.currentTimeMillis()}",
            title = title,
            keyword = keyword,
            style = style,
            estimatedCtrPercent = ctr,
            facebookCopy = pkg.facebookPost.content,
            instagramCopy = pkg.instagramPost.content,
            twitterCopy = pkg.twitterPost.content,
            hashtagsCsv = pkg.instagramPost.hashtags.joinToString(",")
        )
        dao.insertContent(entity)
    }

    suspend fun saveCompetitor(competitor: CompetitorItem) {
        val entity = CompetitorEntity(
            id = competitor.id,
            brandName = competitor.brandName,
            websiteDomain = competitor.websiteDomain,
            nicheCategory = competitor.nicheCategory,
            avgLikes = competitor.avgLikesPerPost,
            avgShares = competitor.avgSharesPerPost,
            avgComments = competitor.avgCommentsPerPost,
            postingFrequency = competitor.postingFrequencyPerWeek,
            engagementRate = competitor.engagementRatePercent,
            sentimentScore = competitor.sentimentScorePercent,
            isLiveTracking = competitor.isLiveTracking
        )
        dao.insertCompetitor(entity)
    }
}
