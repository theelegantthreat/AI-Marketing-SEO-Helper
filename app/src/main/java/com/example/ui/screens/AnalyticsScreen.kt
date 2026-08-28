package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.dialogs.AdPlatformDialog
import com.example.ui.theme.*
import com.example.ui.viewmodel.MarketingUiState

@Composable
fun AnalyticsScreen(
    uiState: MarketingUiState,
    onOpenAdPlatformDetail: (AdPlatformAccount?) -> Unit,
    onToggleAdConnection: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val summary = uiState.analyticsSummary

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (summary != null) {
            // === Top KPI Stat Grid ===
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        title = "BLENDED ROI",
                        value = "${summary.currentRoiPercent}%",
                        subValue = "+14.2% vs last mo",
                        icon = Icons.Default.TrendingUp,
                        deltaPercent = 14.2,
                        accentColor = EmeraldTertiary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricStatCard(
                        title = "GROSS REVENUE",
                        value = "$${(summary.totalRevenueUsd / 1000).toInt()}k",
                        subValue = "$${(summary.totalSpendUsd / 1000).toInt()}k spend",
                        icon = Icons.Default.MonetizationOn,
                        deltaPercent = 22.8,
                        accentColor = BluePrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricStatCard(
                        title = "AVG CTR RATE",
                        value = "${summary.avgClickThroughRatePercent}%",
                        subValue = "Top quartile",
                        icon = Icons.Default.AdsClick,
                        deltaPercent = 8.5,
                        accentColor = LilacSecondary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricStatCard(
                        title = "AVG CAC",
                        value = "$${summary.avgCostPerAcquisitionUsd}",
                        subValue = "${summary.totalConversions} conversions",
                        icon = Icons.Default.PersonAdd,
                        deltaPercent = -18.4, // lower CAC is good
                        accentColor = AmberAccent,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // === Visual ROI Trend Curve Chart ===
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "7-DAY BLENDED ROI TREND",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.2.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Generative Engine Citations & Multi-Channel Return",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = EmeraldContainer
                            ) {
                                Text(
                                    text = "Peak: 373.1%",
                                    color = OnEmeraldContainer,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        RoiTrendAreaChart(dailyPoints = summary.dailyTrendData)
                    }
                }
            }

            // === Weekly Impressions Volume Chart ===
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "WEEKLY IMPRESSION VOLUME (${(summary.totalImpressions / 1000000.0 * 10).toInt() / 10.0}M TOTAL)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        ImpressionsBarChart(dailyPoints = summary.dailyTrendData)
                    }
                }
            }

            // === Channel Attribution & ROAS Breakdown ===
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "CHANNEL ATTRIBUTION & ROAS BREAKDOWN",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        summary.channelBreakdown.forEach { channel ->
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = channel.channelName,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${channel.roas}x ROAS (${channel.sharePercent}%)",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = if (channel.roas > 5.0) EmeraldTertiary else BluePrimary
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Spend: $${channel.spendUsd.toInt()}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("Revenue: $${channel.revenueUsd.toInt()}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                LinearProgressIndicator(
                                    progress = { channel.sharePercent / 100f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = if (channel.roas > 5.0) EmeraldTertiary else BluePrimary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // === Feature 6: Executive Summary Report Card ===
            val report = summary.latestExecutiveReport
            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.Assessment, contentDescription = null, tint = BluePrimary)
                                Column {
                                    Text(
                                        text = "EXECUTIVE SUMMARY REPORT",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.2.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = report.dateString,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            CopyShareButtons(
                                textToCopy = "${report.headline}\n\nKey Wins:\n${report.keyWins.joinToString("\n- ")}\n\nAction Plan:\n${report.geoAeoActionPlan}\n\nBudget Recommendation:\n${report.budgetAdjustmentRecommendation}",
                                shareSubject = "Executive Marketing Report"
                            )
                        }

                        Text(
                            text = report.headline,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = BluePrimary
                        )

                        // Key Wins
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "KEY WINS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            report.keyWins.forEach { win ->
                                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(16.dp))
                                    Text(win, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
                                }
                            }
                        }

                        // Growth Opportunities
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "GROWTH OPPORTUNITIES",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            report.growthOpportunities.forEach { opp ->
                                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.ArrowForward, contentDescription = null, tint = TealTertiary, modifier = Modifier.size(16.dp))
                                    Text(opp, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
                                }
                            }
                        }

                        // GEO & AEO Action Plan
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = LilacSecondaryContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "AEO & GEO ACTION PLAN",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.2.sp,
                                    color = OnLilacSecondary
                                )
                                Text(report.geoAeoActionPlan, style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = OnLilacSecondary)
                            }
                        }

                        // Budget Recommendation
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AmberContainer.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "BUDGET REALLOCATION STRATEGY",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp,
                                    color = OnAmberContainer
                                )
                                Text(report.budgetAdjustmentRecommendation, style = MaterialTheme.typography.bodySmall, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            // === Feature 7 (Part 2): Ad Platform Controller Hub ===
            item {
                Text(
                    text = "CONNECTED AD PLATFORM APIS",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(uiState.adPlatforms, key = { it.platformId }) { platform ->
                AdPlatformCardItem(
                    platform = platform,
                    onClick = { onOpenAdPlatformDetail(platform) }
                )
            }
        }
    }

    // Ad Platform Detail / Config Dialog
    if (uiState.selectedAdPlatformDetail != null) {
        val plat = uiState.selectedAdPlatformDetail
        AdPlatformDialog(
            platform = plat,
            onToggleConnection = { onToggleAdConnection(plat.platformId) },
            onDismiss = { onOpenAdPlatformDetail(null) }
        )
    }
}

@Composable
private fun AdPlatformCardItem(
    platform: AdPlatformAccount,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("ad_platform_card_${platform.platformId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(platform.platformType.iconColorHex).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        tint = Color(platform.platformType.iconColorHex),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Text(
                        text = platform.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${platform.activeCampaignsCount} Active Campaigns • Target ROAS: ${platform.targetRoas}x",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (platform.isConnected) EmeraldContainer else RoseContainer
            ) {
                Text(
                    text = if (platform.isConnected) "Synced" else "Sandbox",
                    color = if (platform.isConnected) OnEmeraldContainer else OnRoseContainer,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
