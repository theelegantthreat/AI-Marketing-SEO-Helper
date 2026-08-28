package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.CopyShareButtons
import com.example.ui.theme.*
import com.example.ui.viewmodel.MarketingUiState

@Composable
fun ContentCreatorScreen(
    uiState: MarketingUiState,
    onTitleSeedKeywordChanged: (String) -> Unit,
    onSetTitleStyleFilter: (TitleStyle?) -> Unit,
    onGenerateTitles: () -> Unit,
    onSelectTitle: (ArticleTitleItem) -> Unit,
    onSelectSocialPlatform: (SocialPlatform) -> Unit,
    onSaveContentPackage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        // Section Selector Tabs
        TabRow(
            selectedTabIndex = selectedSubTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = BluePrimary,
            divider = { HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)) }
        ) {
            Tab(
                selected = selectedSubTab == 0,
                onClick = { selectedSubTab = 0 },
                modifier = Modifier.testTag("tab_article_titles"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.FormatQuote, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Catchy Titles",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubTab == 0) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
            Tab(
                selected = selectedSubTab == 1,
                onClick = { selectedSubTab = 1 },
                modifier = Modifier.testTag("tab_social_creator"),
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Social Posts",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedSubTab == 1) FontWeight.ExtraBold else FontWeight.Medium
                        )
                    }
                }
            )
        }

        when (selectedSubTab) {
            0 -> {
                // === Feature 1: Article Title Generator ===
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                    text = "SEO & GEO ARTICLE TITLE GENERATOR",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.5.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                OutlinedTextField(
                                    value = uiState.titleSeedKeyword,
                                    onValueChange = onTitleSeedKeywordChanged,
                                    label = { Text("Target Keyword or Topic") },
                                    leadingIcon = { Icon(Icons.Default.Key, contentDescription = null, tint = BluePrimary) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("title_keyword_input_field"),
                                    shape = RoundedCornerShape(14.dp),
                                    singleLine = true
                                )

                                // Style Filter Chips
                                Text(
                                    text = "CATEGORIZE BY HEADLINE STYLE:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    item {
                                        FilterChip(
                                            selected = uiState.selectedTitleStyleFilter == null,
                                            onClick = { onSetTitleStyleFilter(null) },
                                            label = {
                                                Text(
                                                    "All (10)",
                                                    fontSize = 11.sp,
                                                    fontWeight = if (uiState.selectedTitleStyleFilter == null) FontWeight.ExtraBold else FontWeight.Normal
                                                )
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = BluePrimary,
                                                selectedLabelColor = TextWhite
                                            )
                                        )
                                    }
                                    items(TitleStyle.values()) { style ->
                                        val isSelected = uiState.selectedTitleStyleFilter == style
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = { onSetTitleStyleFilter(style) },
                                            label = {
                                                Text(
                                                    style.label,
                                                    fontSize = 11.sp,
                                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
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

                                Button(
                                    onClick = onGenerateTitles,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .testTag("generate_titles_btn"),
                                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "GENERATE EXACTLY 10 CATCHY TITLES",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }
                    }

                    item {
                        val filteredTitles = if (uiState.selectedTitleStyleFilter == null) {
                            uiState.generatedArticleTitles
                        } else {
                            uiState.generatedArticleTitles.filter { it.style == uiState.selectedTitleStyleFilter }
                        }

                        Text(
                            text = "GENERATED TITLES (${filteredTitles.size})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    if (uiState.isTitlesLoading) {
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
                        val filteredTitles = if (uiState.selectedTitleStyleFilter == null) {
                            uiState.generatedArticleTitles
                        } else {
                            uiState.generatedArticleTitles.filter { it.style == uiState.selectedTitleStyleFilter }
                        }

                        items(filteredTitles, key = { it.id }) { item ->
                            val isSelected = uiState.selectedArticleTitle?.id == item.id
                            ArticleTitleCard(
                                item = item,
                                isSelected = isSelected,
                                onSelect = {
                                    onSelectTitle(item)
                                    selectedSubTab = 1 // Switch to Social Creator tab
                                }
                            )
                        }
                    }
                }
            }
            1 -> {
                // === Feature 2: Automated Social Media Post Creator ===
                val currentTitle = uiState.selectedArticleTitle ?: uiState.generatedArticleTitles.firstOrNull()
                val socialPkg = uiState.socialPostPackage

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Source Title Header
                    item {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = LilacSecondaryContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "SELECTED ARTICLE BLUEPRINT",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnLilacSecondary,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.5.sp
                                )
                                Text(
                                    text = currentTitle?.title ?: "Select or generate an article title above",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnLilacSecondary
                                )
                            }
                        }
                    }

                    // Platform Switcher Chips (Facebook, Instagram, Twitter/X, LinkedIn)
                    item {
                        Text(
                            text = "TAILORED PLATFORM FORMAT",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.2.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SocialPlatform.values().forEach { platform ->
                                val isSelected = uiState.selectedSocialPlatform == platform
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onSelectSocialPlatform(platform) },
                                    label = {
                                        Text(
                                            platform.displayName,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                                        )
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = when(platform) {
                                            SocialPlatform.FACEBOOK -> Color(0xFF1877F2)
                                            SocialPlatform.INSTAGRAM -> Color(0xFFE1306C)
                                            SocialPlatform.TWITTER -> Color(0xFF1DA1F2)
                                            SocialPlatform.LINKEDIN -> Color(0xFF0A66C2)
                                        },
                                        selectedLabelColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    if (uiState.isSocialPostsLoading || socialPkg == null) {
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
                        val activePost = when (uiState.selectedSocialPlatform) {
                            SocialPlatform.FACEBOOK -> socialPkg.facebookPost
                            SocialPlatform.INSTAGRAM -> socialPkg.instagramPost
                            SocialPlatform.TWITTER -> socialPkg.twitterPost
                            SocialPlatform.LINKEDIN -> socialPkg.linkedinPost
                        }

                        item {
                            SocialPostPreviewCard(
                                platformPost = activePost,
                                onSave = onSaveContentPackage
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleTitleCard(
    item: ArticleTitleItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.5.dp,
            if (isSelected) BluePrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
        ),
        tonalElevation = if (isSelected) 3.dp else 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .testTag("article_title_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (item.style) {
                        TitleStyle.HOW_TO -> BluePrimaryContainer
                        TitleStyle.LISTICLE -> EmeraldContainer
                        TitleStyle.CONTROVERSIAL -> RoseContainer
                        TitleStyle.DATA_DRIVEN -> LilacSecondaryContainer
                        TitleStyle.ULTIMATE_GUIDE -> AmberContainer
                    }
                ) {
                    Text(
                        text = item.style.label.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp,
                        color = when (item.style) {
                            TitleStyle.HOW_TO -> OnBluePrimaryContainer
                            TitleStyle.LISTICLE -> OnEmeraldContainer
                            TitleStyle.CONTROVERSIAL -> OnRoseContainer
                            TitleStyle.DATA_DRIVEN -> OnLilacSecondary
                            TitleStyle.ULTIMATE_GUIDE -> OnAmberContainer
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "Est. CTR: ${item.estimatedCtrPercent}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldTertiary,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Target AEO Signal: ${item.aeoAnswerTarget}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${item.wordCount} words • ${item.characterCount} chars • Impact ${item.emotionalScore}/100",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                )

                Button(
                    onClick = onSelect,
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "CREATE POSTS →",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialPostPreviewCard(
    platformPost: SocialPlatformPost,
    onSave: () -> Unit
) {
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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BluePrimaryContainer
                    ) {
                        Text(
                            text = platformPost.platform.displayName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = OnBluePrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "${platformPost.content.length} chars",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                CopyShareButtons(
                    textToCopy = platformPost.content,
                    shareSubject = "Social Post for ${platformPost.platform.displayName}"
                )
            }

            // Post Content Box
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = platformPost.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }

            // Hashtags Box
            if (platformPost.hashtags.isNotEmpty()) {
                Text(
                    text = "OPTIMIZED HASHTAGS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(platformPost.hashtags) { tag ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = TealTertiaryContainer
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = OnTealTertiaryContainer,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            // Visual Asset & Hook Suggestions
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = AmberContainer.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "VISUAL ASSET SUGGESTION",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = OnAmberContainer
                    )
                    Text(
                        text = platformPost.visualAssetSuggestion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }

            // Save to Content Library
            Button(
                onClick = onSave,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("save_content_library_btn")
            ) {
                Icon(Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "SAVE CONTENT PACKAGE TO LIBRARY",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
