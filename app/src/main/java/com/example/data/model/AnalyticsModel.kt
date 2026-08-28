package com.example.data.model

data class MarketingAnalyticsSummary(
    val currentRoiPercent: Double, // e.g. 342.5%
    val totalRevenueUsd: Double, // e.g. $48,250.00
    val totalSpendUsd: Double, // e.g. $10,900.00
    val totalImpressions: Long, // e.g. 1,420,000
    val avgClickThroughRatePercent: Double, // e.g. 4.82%
    val totalConversions: Int, // e.g. 1,840
    val avgCostPerAcquisitionUsd: Double, // e.g. $5.92
    val aeoAnswerEngineSharePercent: Double, // e.g. 28.4% of traffic from AI citation
    val dailyTrendData: List<DailyMetricPoint>,
    val channelBreakdown: List<ChannelPerformance>,
    val latestExecutiveReport: ExecutiveSummaryReport
)

data class DailyMetricPoint(
    val dayLabel: String, // e.g. "Mon", "Tue"
    val impressions: Long,
    val clicks: Long,
    val spendUsd: Double,
    val revenueUsd: Double,
    val ctrPercent: Double,
    val roiPercent: Double
)

data class ChannelPerformance(
    val channelName: String,
    val spendUsd: Double,
    val revenueUsd: Double,
    val roas: Double,
    val sharePercent: Int,
    val status: String
)

data class ExecutiveSummaryReport(
    val reportId: String,
    val dateString: String,
    val headline: String,
    val executiveScore: Int, // 0-100 marketing health score
    val keyWins: List<String>,
    val growthOpportunities: List<String>,
    val geoAeoActionPlan: String,
    val criticalAlerts: List<String>,
    val budgetAdjustmentRecommendation: String
)
