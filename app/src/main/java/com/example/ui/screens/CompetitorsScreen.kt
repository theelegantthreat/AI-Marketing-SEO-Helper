package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.ui.components.CopyShareButtons
import com.example.ui.components.SentimentBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.MarketingUiState

@Composable
fun CompetitorsScreen(
    uiState: MarketingUiState,
    onToggleLiveTracking: () -> Unit,
    onOpenCompetitorDetail: (CompetitorItem?) -> Unit,
    onSentimentInputChanged: (String) -> Unit,
    onAnalyzeSentiment: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Competitor Tracking Stream, 1: AI Sentiment NLP Analyzer

    Column(modifier = modifier.fillMaxSize()) {
        // Sub-tabs with Bold Typography
        TabRow(
            selectedTabIndex = selectedSubTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BluePrimary,
            divider = { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)) }
        ) {
            Tab(
                selected = selectedSubTab == 0,
                onClick = { selectedSubTab = 0 },
                modifier = Modifier.testTag("tab_competitor_tracker"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.RemoveRedEye, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Competitor Tracker",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubTab == 0) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
            Tab(
                selected = selectedSubTab == 1,
                onClick = { selectedSubTab = 1 },
                modifier = Modifier.testTag("tab_sentiment_nlp"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Sentiment NLP Analyzer",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubTab == 1) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
        }

        if (selectedSubTab == 0) {
            // === Feature 5: Competitor Engagement Tracker ===
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Live Tracker Control Bar
                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        tonalElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(if (uiState.isLiveTrackingActive) EmeraldTertiary else RoseAccent)
                                )
                                Column {
                                    Text(
                                        text = if (uiState.isLiveTrackingActive) "LIVE REAL-TIME STREAM" else "STREAM PAUSED",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.2.sp,
                                        color = if (uiState.isLiveTrackingActive) EmeraldTertiary else RoseAccent
                                    )
                                    Text(
                                        text = "Tracking Meta, X, LinkedIn, TikTok & Citations",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Button(
                                onClick = onToggleLiveTracking,
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (uiState.isLiveTrackingActive) RoseContainer else BluePrimaryContainer,
                                    contentColor = if (uiState.isLiveTrackingActive) OnRoseContainer else OnBluePrimaryContainer
                                ),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                                modifier = Modifier
                                    .testTag("toggle_live_tracking_btn")
                                    .height(34.dp)
                            ) {
                                Text(
                                    text = if (uiState.isLiveTrackingActive) "PAUSE" else "RESUME",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "TRACKED INDUSTRY COMPETITORS (${uiState.competitorList.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(uiState.competitorList, key = { it.id }) { comp ->
                    CompetitorCardItem(
                        competitor = comp,
                        onClick = { onOpenCompetitorDetail(comp) }
                    )
                }
            }
        } else {
            // === Feature 7 (Part 1): AI-Driven Sentiment Analysis NLP Module ===
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
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
                                text = "NLP SENTIMENT & BRAND MENTION ANALYZER",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Analyze user comments, ad feedback, customer reviews, or tweet mentions to extract emotional tone and evaluate impact on AI Answer Engine recommendations.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )

                            OutlinedTextField(
                                value = uiState.sentimentInputText,
                                onValueChange = onSentimentInputChanged,
                                label = { Text("Paste Comment, Ad Feedback, or Tweet") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("sentiment_input_field"),
                                shape = RoundedCornerShape(14.dp),
                                maxLines = 4
                            )

                            // Sample Test Presets
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AssistChip(
                                    onClick = {
                                        onSentimentInputChanged("The GEO schema implementation was a massive breakthrough. Doubled our traffic in 30 days!")
                                        onAnalyzeSentiment("The GEO schema implementation was a massive breakthrough. Doubled our traffic in 30 days!")
                                    },
                                    label = {
                                        Text("Sample Positive", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                )
                                AssistChip(
                                    onClick = {
                                        onSentimentInputChanged("The software is overly complex and the monthly subscription fee is way too expensive for what it offers.")
                                        onAnalyzeSentiment("The software is overly complex and the monthly subscription fee is way too expensive for what it offers.")
                                    },
                                    label = {
                                        Text("Sample Critical", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }

                            Button(
                                onClick = { onAnalyzeSentiment(null) },
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("analyze_sentiment_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                            ) {
                                Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "CLASSIFY SENTIMENT & GENERATE RESPONSE",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }

                if (uiState.isSentimentAnalyzing) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = BluePrimary)
                        }
                    }
                } else if (uiState.sentimentResult != null) {
                    val result = uiState.sentimentResult
                    item {
                        SentimentResultCard(result = result)
                    }
                }
            }
        }
    }
}

@Composable
private fun CompetitorCardItem(
    competitor: CompetitorItem,
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
            .testTag("competitor_card_${competitor.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = competitor.brandName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${competitor.websiteDomain} • ${competitor.nicheCategory}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = EmeraldContainer
                ) {
                    Text(
                        text = "${competitor.sentimentScorePercent}% Positive",
                        color = OnEmeraldContainer,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Key Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MetricColumn(label = "AVG LIKES", value = "${competitor.avgLikesPerPost}", color = BluePrimary)
                MetricColumn(label = "AVG SHARES", value = "${competitor.avgSharesPerPost}", color = TealTertiary)
                MetricColumn(label = "COMMENTS", value = "${competitor.avgCommentsPerPost}", color = AmberAccent)
                MetricColumn(label = "ENG RATE", value = "${competitor.engagementRatePercent}%", color = EmeraldTertiary)
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            // Platform Presence Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                competitor.platformPresence.forEach { plat ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${plat.platform}: ${plat.followerCount}",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SentimentResultCard(
    result: SentimentAnalysisResult
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Sentiment Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SentimentBadge(sentiment = result.sentiment)
                Text(
                    text = "CONFIDENCE: ${(result.confidenceScore * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "Detected Tone: ${result.emotionalTone}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Sentiment Score Breakdown Bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Positive ${result.positiveScore}%", fontSize = 11.sp, color = SentimentPositive, fontWeight = FontWeight.ExtraBold)
                    Text("Neutral ${result.neutralScore}%", fontSize = 11.sp, color = SentimentNeutral, fontWeight = FontWeight.ExtraBold)
                    Text("Negative ${result.negativeScore}%", fontSize = 11.sp, color = SentimentNegative, fontWeight = FontWeight.ExtraBold)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Box(modifier = Modifier.weight(result.positiveScore.toFloat().coerceAtLeast(1f)).fillMaxHeight().background(SentimentPositive))
                    Box(modifier = Modifier.weight(result.neutralScore.toFloat().coerceAtLeast(1f)).fillMaxHeight().background(SentimentNeutral))
                    Box(modifier = Modifier.weight(result.negativeScore.toFloat().coerceAtLeast(1f)).fillMaxHeight().background(SentimentNegative))
                }
            }

            // GEO Brand Reputation Impact
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = LilacSecondaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "AI ANSWER ENGINE REPUTATION IMPACT",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp,
                        color = OnLilacSecondary
                    )
                    Text(
                        text = result.geoBrandImpact,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnLilacSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            // Suggested AI Response
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "RECOMMENDED INSTANT RESPONSE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = BluePrimary
                        )
                        CopyShareButtons(textToCopy = result.suggestedAiResponse, shareSubject = "AI Sentiment Response")
                    }
                    Text(
                        text = result.suggestedAiResponse,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = color
        )
    }
}
