package com.example.data.model

data class SentimentAnalysisResult(
    val inputText: String,
    val sentiment: SentimentType,
    val confidenceScore: Double, // 0.0 - 1.0
    val emotionalTone: String, // e.g. "Enthusiastic & Delighted", "Frustrated with Pricing", "Inquisitive"
    val positiveScore: Int, // 0-100
    val neutralScore: Int,
    val negativeScore: Int,
    val keyExtractedPhrases: List<String>,
    val geoBrandImpact: String, // How this sentiment affects brand reputation in AI summary indexes
    val suggestedAiResponse: String
)

enum class SentimentType(val label: String, val colorHex: Long) {
    POSITIVE("Positive", 0xFF10B981),
    NEUTRAL("Neutral", 0xFFF59E0B),
    NEGATIVE("Negative", 0xFFEF4444)
}
