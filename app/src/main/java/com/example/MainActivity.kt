package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.MarketingHeader
import com.example.ui.dialogs.AiMarketingAdvisorSheet
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.CompetitorsScreen
import com.example.ui.screens.ContentCreatorScreen
import com.example.ui.screens.ResearchScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.MarketingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AiMarketingTheme {
                MarketingApp()
            }
        }
    }
}

@Composable
fun MarketingApp(viewModel: MarketingViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val savedNiches by viewModel.savedNiches.collectAsState()
    val savedKeywords by viewModel.savedKeywords.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userSnackbarMessage) {
        uiState.userSnackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val title = when (uiState.currentTab) {
                0 -> "Market Research"
                1 -> "Content Creator"
                2 -> "Competitor Tracker"
                else -> "Marketing Analytics"
            }
            val subtitle = when (uiState.currentTab) {
                0 -> "10 Trending Niches & Long-Tail Keywords"
                1 -> "10 SEO/GEO Titles & Multi-Platform Copy"
                2 -> "Live Social Metrics & NLP Sentiment"
                else -> "342.5% Blended ROI & Ad Controllers"
            }
            MarketingHeader(
                title = title,
                subtitle = subtitle,
                onOpenAiChat = { viewModel.toggleAiAssistant(true) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 0.dp
            ) {
                val items = listOf(
                    Triple(0, "Research", Icons.Default.Search),
                    Triple(1, "Content", Icons.Default.Article),
                    Triple(2, "Competitors", Icons.Default.People),
                    Triple(3, "Analytics", Icons.Default.BarChart)
                )

                items.forEach { (index, label, icon) ->
                    NavigationBarItem(
                        selected = uiState.currentTab == index,
                        onClick = { viewModel.setTab(index) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (uiState.currentTab == index) FontWeight.ExtraBold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OnBluePrimaryContainer,
                            selectedTextColor = OnBluePrimaryContainer,
                            indicatorColor = BluePrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.testTag("nav_tab_$index")
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                0 -> ResearchScreen(
                    uiState = uiState,
                    savedNicheIds = savedNiches.map { it.id }.toSet(),
                    savedKeywordIds = savedKeywords.map { it.id }.toSet(),
                    onSelectCategory = { viewModel.selectCategory(it) },
                    onRefreshNiches = { viewModel.refreshNiches() },
                    onOpenNicheDetail = { viewModel.openNicheDetail(it) },
                    onToggleSaveNiche = { viewModel.toggleSaveNiche(it) },
                    onSeedKeywordChanged = { viewModel.onSeedKeywordChanged(it) },
                    onSetWordCountThreshold = { viewModel.setWordCountThreshold(it) },
                    onSetIntentFilter = { viewModel.setIntentFilter(it) },
                    onRefreshKeywords = { viewModel.refreshKeywords() },
                    onToggleSaveKeyword = { viewModel.toggleSaveKeyword(it) },
                    onTransferKeywordToTitles = { viewModel.transferKeywordToContentCreator(it) }
                )
                1 -> ContentCreatorScreen(
                    uiState = uiState,
                    onTitleSeedKeywordChanged = { viewModel.onTitleSeedKeywordChanged(it) },
                    onSetTitleStyleFilter = { viewModel.setTitleStyleFilter(it) },
                    onGenerateTitles = { viewModel.generateTitlesForKeyword() },
                    onSelectTitle = { viewModel.selectArticleTitle(it) },
                    onSelectSocialPlatform = { viewModel.setSelectedSocialPlatform(it) },
                    onSaveContentPackage = { viewModel.saveCurrentContentPackage() }
                )
                2 -> CompetitorsScreen(
                    uiState = uiState,
                    onToggleLiveTracking = { viewModel.toggleLiveTracking() },
                    onOpenCompetitorDetail = { viewModel.openCompetitorDetail(it) },
                    onSentimentInputChanged = { viewModel.onSentimentInputChanged(it) },
                    onAnalyzeSentiment = { viewModel.analyzeSentimentText(it) }
                )
                3 -> AnalyticsScreen(
                    uiState = uiState,
                    onOpenAdPlatformDetail = { viewModel.openAdPlatformDetail(it) },
                    onToggleAdConnection = { viewModel.toggleAdPlatformConnection(it) }
                )
            }
        }

        // Multi-Turn AI Marketing Strategist Assistant BottomSheet
        if (uiState.isAiAssistantOpen) {
            AiMarketingAdvisorSheet(
                messages = uiState.aiChatMessages,
                inputText = uiState.aiInputText,
                isThinking = uiState.isAiThinking,
                onInputChanged = { viewModel.onAiInputChanged(it) },
                onSendMessage = { viewModel.sendAiMessage() },
                onDismiss = { viewModel.toggleAiAssistant(false) }
            )
        }
    }
}
