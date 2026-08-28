package com.example.data.model

data class NicheItem(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val growthRatePercent: Int, // e.g. +142%
    val competitionLevel: CompetitionLevel, // Low, Medium, High
    val monetizationPotential: MonetizationLevel, // High, Very High, Medium
    val geoAeoReadinessScore: Int, // 1 - 100 AEO/GEO readiness
    val estimatedMonthlySearchVolume: String, // e.g. "450K/mo"
    val avgCpcUsd: Double, // e.g. $3.85
    val subNiches: List<String>,
    val monetizationMethods: List<String>,
    val targetAudience: String,
    val aiSearchTips: String, // Generative Engine Optimization recommendation
    val isSaved: Boolean = false
)

enum class CompetitionLevel(val label: String) {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High")
}

enum class MonetizationLevel(val label: String) {
    MEDIUM("Medium"),
    HIGH("High"),
    VERY_HIGH("Very High")
}
