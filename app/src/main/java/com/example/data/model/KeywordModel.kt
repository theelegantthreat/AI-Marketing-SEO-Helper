package com.example.data.model

data class KeywordItem(
    val id: String,
    val keyword: String,
    val seedTopic: String,
    val wordCount: Int,
    val monthlySearchVolume: Int,
    val competitionScore: Int, // 0 - 100
    val cpcUsd: Double,
    val intent: SearchIntent,
    val geoCitationLikelihood: Int, // 0 - 100 score for being cited by ChatGPT/Perplexity/Gemini
    val trendingDeltaPercent: Int,
    val difficultyLabel: String, // Easy, Moderate, Hard
    val isSaved: Boolean = false
)

enum class SearchIntent(val label: String, val badgeColorHex: Long) {
    INFORMATIONAL("Informational", 0xFF3B82F6),
    TRANSACTIONAL("Transactional", 0xFF10B981),
    COMMERCIAL("Commercial", 0xFF8B5CF6),
    NAVIGATIONAL("Navigational", 0xFFF59E0B)
}
