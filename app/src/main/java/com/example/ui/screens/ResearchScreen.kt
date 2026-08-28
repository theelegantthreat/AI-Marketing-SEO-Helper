package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.dialogs.NicheDetailDialog
import com.example.ui.theme.*
import com.example.ui.viewmodel.MarketingUiState

@Composable
fun ResearchScreen(
    uiState: MarketingUiState,
    savedNicheIds: Set<String>,
    savedKeywordIds: Set<String>,
    onSelectCategory: (String) -> Unit,
    onRefreshNiches: () -> Unit,
    onOpenNicheDetail: (NicheItem?) -> Unit,
    onToggleSaveNiche: (NicheItem) -> Unit,
    onSeedKeywordChanged: (String) -> Unit,
    onSetWordCountThreshold: (Int) -> Unit,
    onSetIntentFilter: (SearchIntent?) -> Unit,
    onRefreshKeywords: () -> Unit,
    onToggleSaveKeyword: (KeywordItem) -> Unit,
    onTransferKeywordToTitles: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubSection by remember { mutableIntStateOf(0) } // 0: Trending Niches (10), 1: Long-Tail Keywords

    val categories = listOf("All", "Tech & AI", "Health & Wellness", "SaaS & B2B", "Creator Economy", "E-Commerce", "Finance & Crypto", "Sustainability")

    Column(modifier = modifier.fillMaxSize()) {
        // Section Sub-Tab Navigation with Bold Typography
        TabRow(
            selectedTabIndex = selectedSubSection,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BluePrimary,
            divider = { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)) }
        ) {
            Tab(
                selected = selectedSubSection == 0,
                onClick = { selectedSubSection = 0 },
                modifier = Modifier.testTag("tab_trending_niches"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Trending Niches (10)",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubSection == 0) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
            Tab(
                selected = selectedSubSection == 1,
                onClick = { selectedSubSection = 1 },
                modifier = Modifier.testTag("tab_long_tail_keywords"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Key, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Long-Tail Keywords",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubSection == 1) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
        }

        if (selectedSubSection == 0) {
            // === Feature 1: Trending Niche Finder ===
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Design Spec: Active Campaign ROI Lilac Hero Card
                item {
                    Surface(
                        shape = RoundedCornerShape(28.dp),
                        color = LilacSecondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column {
                                    Text(
                                        text = "Active Campaign ROI",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Medium,
                                        color = OnLilacSecondary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "+24.8%",
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = OnLilacSecondary
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.White,
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.TrendingUp,
                                            contentDescription = null,
                                            tint = LilacSecondary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Progress Track
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.4f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.72f)
                                        .fillMaxHeight()
                                        .clip(CircleShape)
                                        .background(LilacSecondary)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Daily Executive Summary: Optimization recommended for X/Twitter & TikTok posts.",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = OnLilacSecondary.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                item {
                    // Category Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        items(categories) { cat ->
                            val isSelected = uiState.selectedCategory == cat
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectCategory(cat) },
                                label = {
                                    Text(
                                        text = cat,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = BluePrimary,
                                    selectedLabelColor = TextWhite
                                )
                            )
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "TRENDING NICHES",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.5.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(
                            onClick = onRefreshNiches,
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Text(
                                text = "View Top 10",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = BluePrimary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.Refresh, contentDescription = "Refresh", modifier = Modifier.size(14.dp), tint = BluePrimary)
                        }
                    }
                }

                if (uiState.isNichesLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = BluePrimary)
                        }
                    }
                } else {
                    itemsIndexed(uiState.trendingNiches, key = { _, it -> it.id }) { index, niche ->
                        val isSaved = savedNicheIds.contains(niche.id)
                        NicheCardItem(
                            index = index + 1,
                            niche = niche,
                            isSaved = isSaved,
                            onCardClick = { onOpenNicheDetail(niche) },
                            onToggleSave = { onToggleSaveNiche(niche) },
                            onExploreKeywords = { onTransferKeywordToTitles(niche.title) }
                        )
                    }
                }

                // Keyword Insights Preview Card (from Design HTML)
                item {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, LightCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "KEYWORD INSIGHTS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "\"best generative ai productivity tools 2025\"",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "INTENT: TRANSACTIONAL",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BluePrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    onClick = { onTransferKeywordToTitles("best generative ai productivity tools 2025") },
                                    shape = CircleShape,
                                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text(
                                        text = "GENERATE TITLES",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // === Feature 2: Long-Tail Keyword Generator ===
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    // Seed Topic Input Box
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        tonalElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "SEED TOPIC & THRESHOLD FILTER",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            OutlinedTextField(
                                value = uiState.seedKeywordInput,
                                onValueChange = onSeedKeywordChanged,
                                label = { Text("Seed Topic (e.g. Generative Engine Optimization)") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BluePrimary) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("seed_keyword_input_field"),
                                shape = RoundedCornerShape(14.dp),
                                singleLine = true
                            )

                            // Word Count Length Threshold Filter
                            Text(
                                text = "KEYWORD WORD COUNT THRESHOLD:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(3 to "3+ Words", 4 to "4+ Words", 5 to "5+ Words").forEach { (count, label) ->
                                    val isSelected = uiState.selectedWordCountThreshold == count
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = { onSetWordCountThreshold(count) },
                                        label = {
                                            Text(
                                                label,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                                            )
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = BluePrimaryContainer,
                                            selectedLabelColor = OnBluePrimaryContainer
                                        )
                                    )
                                }
                            }

                            Button(
                                onClick = onRefreshKeywords,
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .testTag("generate_keywords_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "GENERATE LONG-TAIL KEYWORDS",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "GENERATED LONG-TAIL KEYWORDS (${uiState.generatedKeywords.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.2.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (uiState.isKeywordsLoading) {
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
                } else {
                    items(uiState.generatedKeywords, key = { it.id }) { kw ->
                        val isSaved = savedKeywordIds.contains(kw.id)
                        KeywordCardItem(
                            keywordItem = kw,
                            isSaved = isSaved,
                            onToggleSave = { onToggleSaveKeyword(kw) },
                            onGenerateTitles = { onTransferKeywordToTitles(kw.keyword) }
                        )
                    }
                }
            }
        }
    }

    // Niche Detail Dialog Sheet
    if (uiState.selectedNicheDetail != null) {
        val niche = uiState.selectedNicheDetail
        val isSaved = savedNicheIds.contains(niche.id)
        NicheDetailDialog(
            niche = niche,
            isSaved = isSaved,
            onToggleSave = { onToggleSaveNiche(niche) },
            onExploreKeywords = { title -> onTransferKeywordToTitles(title) },
            onDismiss = { onOpenNicheDetail(null) }
        )
    }
}

@Composable
private fun NicheCardItem(
    index: Int,
    niche: NicheItem,
    isSaved: Boolean,
    onCardClick: () -> Unit,
    onToggleSave: () -> Unit,
    onExploreKeywords: () -> Unit
) {
    val (badgeBg, badgeText) = when (index % 3) {
        1 -> BadgePink to OnBadgePink
        2 -> LilacSecondaryContainer to OnLilacSecondary
        else -> BadgeNavy to OnBadgeNavy
    }

    val indexFormatted = if (index < 10) "0$index" else "$index"

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("niche_card_${niche.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Numbered Index Badge (01, 02, 03)
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(badgeBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = indexFormatted,
                        color = badgeText,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = niche.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BadgePurple
                        ) {
                            Text(
                                text = "Growth: ${niche.growthRatePercent}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnBadgePurple,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BadgeSkyBlue
                        ) {
                            Text(
                                text = "Comp: ${niche.competitionLevel.label}",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnBadgeSkyBlue,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = niche.estimatedMonthlySearchVolume,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = BluePrimary
                    )
                    IconButton(
                        onClick = onToggleSave,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save Niche",
                            tint = if (isSaved) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = niche.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Monetization: ${niche.monetizationPotential.label} • GEO: ${niche.geoAeoReadinessScore}/100",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                )

                TextButton(
                    onClick = onExploreKeywords,
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "GET KEYWORDS →",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp,
                        color = BluePrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun KeywordCardItem(
    keywordItem: KeywordItem,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onGenerateTitles: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = keywordItem.keyword,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Keyword",
                        tint = if (isSaved) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Keyword Badges: Intent, Words, Volume, CPC
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Intent Indicator
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(keywordItem.intent.badgeColorHex).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "INTENT: ${keywordItem.intent.label.uppercase()}",
                        color = Color(keywordItem.intent.badgeColorHex),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "${keywordItem.wordCount} words",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "• ${keywordItem.monthlySearchVolume} /mo",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "• $${keywordItem.cpcUsd} CPC",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmberAccent,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AI Citation: ${keywordItem.geoCitationLikelihood}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = TealTertiary,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onGenerateTitles,
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.Article, contentDescription = null, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "GENERATE TITLES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}
