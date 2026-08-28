package com.example.data.model

data class AdPlatformAccount(
    val platformId: String,
    val name: String,
    val platformType: AdPlatformType,
    val isConnected: Boolean,
    val statusLabel: String,
    val activeCampaignsCount: Int,
    val dailyBudgetUsd: Double,
    val targetRoas: Double,
    val totalImpressions7d: Long,
    val currentSpend7dUsd: Double,
    val currentRoas7d: Double,
    val apiEndpoint: String,
    val syncLatencyMs: Long,
    val apiKeyMasked: String = "••••••••••••••••"
)

enum class AdPlatformType(val displayName: String, val iconColorHex: Long) {
    META_ADS("Meta Ads (Facebook & Instagram)", 0xFF1877F2),
    GOOGLE_ADS("Google Ads & Performance Max", 0xFFEA4335),
    TIKTOK_ADS("TikTok For Business", 0xFF000000),
    LINKEDIN_ADS("LinkedIn Campaign Manager", 0xFF0A66C2)
}
