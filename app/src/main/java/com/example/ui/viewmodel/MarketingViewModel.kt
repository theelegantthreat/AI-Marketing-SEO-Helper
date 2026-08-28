package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entity.SavedContentEntity
import com.example.data.local.entity.SavedKeywordEntity
import com.example.data.local.entity.SavedNicheEntity
import com.example.data.model.*
import com.example.data.repository.MarketingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class MarketingUiState(
    val currentTab: Int = 0, // 0: Research, 1: Content Creator, 2: Competitors, 3: Analytics
    
    // Research Tab
    val selectedCategory: String = "All",
    val isNichesLoading: Boolean = false,
    val trendingNiches: List<NicheItem> = emptyList(),
    val selectedNicheDetail: NicheItem? = null,
    val seedKeywordInput: String = "Generative Engine Optimization",
    val selectedWordCountThreshold: Int = 3,
    val selectedIntentFilter: SearchIntent? = null,
    val isKeywordsLoading: Boolean = false,
    val generatedKeywords: List<KeywordItem> = emptyList(),
    
    // Content Creator Tab
    val titleSeedKeyword: String = "Generative Engine Optimization (GEO)",
    val selectedTitleStyleFilter: TitleStyle? = null,
    val isTitlesLoading: Boolean = false,
    val generatedArticleTitles: List<ArticleTitleItem> = emptyList(),
    val selectedArticleTitle: ArticleTitleItem? = null,
    val isSocialPostsLoading: Boolean = false,
    val socialPostPackage: SocialPostPackage? = null,
    val selectedSocialPlatform: SocialPlatform = SocialPlatform.FACEBOOK,
    
    // Competitors Tab
    val competitorList: List<CompetitorItem> = emptyList(),
    val isLiveTrackingActive: Boolean = true,
    val sentimentInputText: String = "We migrated our schema to GEO structure and saw a 3x surge in ChatGPT citations! Outstanding ROI.",
    val isSentimentAnalyzing: Boolean = false,
    val sentimentResult: SentimentAnalysisResult? = null,
    val selectedCompetitorDetail: CompetitorItem? = null,
    
    // Analytics Tab
    val analyticsSummary: MarketingAnalyticsSummary? = null,
    val adPlatforms: List<AdPlatformAccount> = emptyList(),
    val selectedAdPlatformDetail: AdPlatformAccount? = null,
    
    // AI Chat Assistant
    val isAiAssistantOpen: Boolean = false,
    val aiChatMessages: List<AiChatMessage> = listOf(
        AiChatMessage(
            sender = "AI Marketing Strategist",
            text = "Hello! I am your AI Marketing, SEO & GEO Consultant. Ask me anything about ranking in ChatGPT/Gemini, structuring content, finding untapped niches, or optimizing ad spend.",
            isAi = true
        )
    ),
    val aiInputText: String = "",
    val isAiThinking: Boolean = false,
    
    // Global User Messages
    val userSnackbarMessage: String? = null
)

data class AiChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String,
    val text: String,
    val isAi: Boolean,
    val timestampMillis: Long = System.currentTimeMillis()
)

class MarketingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MarketingRepository

    private val _uiState = MutableStateFlow(MarketingUiState())
    val uiState: StateFlow<MarketingUiState> = _uiState.asStateFlow()

    private var liveStreamJob: Job? = null

    val savedNiches: StateFlow<List<SavedNicheEntity>>
    val savedKeywords: StateFlow<List<SavedKeywordEntity>>
    val savedContent: StateFlow<List<SavedContentEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = MarketingRepository(db.marketingDao())

        savedNiches = repository.savedNichesFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        savedKeywords = repository.savedKeywordsFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        savedContent = repository.savedContentFlow.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )

        loadInitialData()
        startLiveStreamSimulation()
    }

    private fun loadInitialData() {
        refreshNiches()
        refreshKeywords()
        generateTitlesForKeyword("Generative Engine Optimization (GEO)")
        loadCompetitors()
        loadAnalytics()
        loadAdPlatforms()
    }


    fun setTab(tabIndex: Int) {
        _uiState.update { it.copy(currentTab = tabIndex) }
    }

    // --- Research Tab Actions ---
    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        refreshNiches()
    }

    fun refreshNiches() {
        viewModelScope.launch {
            _uiState.update { it.copy(isNichesLoading = true) }
            delay(350)
            val niches = repository.getTrendingNiches(_uiState.value.selectedCategory)
            _uiState.update { it.copy(trendingNiches = niches, isNichesLoading = false) }
        }
    }

    fun openNicheDetail(niche: NicheItem?) {
        _uiState.update { it.copy(selectedNicheDetail = niche) }
    }

    fun onSeedKeywordChanged(text: String) {
        _uiState.update { it.copy(seedKeywordInput = text) }
    }

    fun setWordCountThreshold(threshold: Int) {
        _uiState.update { it.copy(selectedWordCountThreshold = threshold) }
        refreshKeywords()
    }

    fun setIntentFilter(intent: SearchIntent?) {
        _uiState.update { it.copy(selectedIntentFilter = intent) }
    }

    fun refreshKeywords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isKeywordsLoading = true) }
            delay(400)
            val topic = _uiState.value.seedKeywordInput.ifBlank { "Generative Engine Optimization" }
            val threshold = _uiState.value.selectedWordCountThreshold
            val keywords = repository.generateLongTailKeywords(topic, threshold)
            _uiState.update { it.copy(generatedKeywords = keywords, isKeywordsLoading = false) }
        }
    }

    fun toggleSaveNiche(niche: NicheItem) {
        viewModelScope.launch {
            val isSaved = savedNiches.value.any { it.id == niche.id }
            repository.toggleSaveNiche(niche, isSaved)
            showSnackbar(if (isSaved) "Removed niche from bookmarks" else "Saved niche to bookmarks")
        }
    }

    fun toggleSaveKeyword(keyword: KeywordItem) {
        viewModelScope.launch {
            val isSaved = savedKeywords.value.any { it.id == keyword.id }
            repository.toggleSaveKeyword(keyword, isSaved)
            showSnackbar(if (isSaved) "Removed keyword from bookmarks" else "Saved keyword to bookmarks")
        }
    }

    fun transferKeywordToContentCreator(keyword: String) {
        _uiState.update {
            it.copy(
                titleSeedKeyword = keyword,
                currentTab = 1
            )
        }
        generateTitlesForKeyword(keyword)
        showSnackbar("Generating catchy article titles for: $keyword")
    }

    // --- Content Creator Actions ---
    fun onTitleSeedKeywordChanged(text: String) {
        _uiState.update { it.copy(titleSeedKeyword = text) }
    }

    fun setTitleStyleFilter(style: TitleStyle?) {
        _uiState.update { it.copy(selectedTitleStyleFilter = style) }
    }

    fun generateTitlesForKeyword(keyword: String? = null) {
        val target = keyword ?: _uiState.value.titleSeedKeyword
        viewModelScope.launch {
            _uiState.update { it.copy(isTitlesLoading = true) }
            delay(400)
            val titles = repository.generateArticleTitles(target)
            val first = titles.firstOrNull()
            _uiState.update {
                it.copy(
                    generatedArticleTitles = titles,
                    isTitlesLoading = false,
                    selectedArticleTitle = first
                )
            }
            if (first != null) {
                generateSocialPostsForTitle(first)
            }
        }
    }

    fun selectArticleTitle(title: ArticleTitleItem) {
        _uiState.update { it.copy(selectedArticleTitle = title) }
        generateSocialPostsForTitle(title)
    }

    fun generateSocialPostsForTitle(title: ArticleTitleItem) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSocialPostsLoading = true) }
            delay(300)
            val posts = repository.generateSocialPosts(title.title, title.keyword)
            _uiState.update { it.copy(socialPostPackage = posts, isSocialPostsLoading = false) }
        }
    }

    fun setSelectedSocialPlatform(platform: SocialPlatform) {
        _uiState.update { it.copy(selectedSocialPlatform = platform) }
    }

    fun saveCurrentContentPackage() {
        val title = _uiState.value.selectedArticleTitle ?: return
        val pkg = _uiState.value.socialPostPackage ?: return
        viewModelScope.launch {
            repository.saveSocialContent(
                title = title.title,
                keyword = title.keyword,
                style = title.style.label,
                ctr = title.estimatedCtrPercent,
                pkg = pkg
            )
            showSnackbar("Saved content package to your offline library!")
        }
    }

    // --- Competitor & Sentiment Actions ---
    private fun loadCompetitors() {
        viewModelScope.launch {
            val list = repository.getCompetitorData()
            _uiState.update { it.copy(competitorList = list) }
        }
    }

    fun openCompetitorDetail(competitor: CompetitorItem?) {
        _uiState.update { it.copy(selectedCompetitorDetail = competitor) }
    }

    fun toggleLiveTracking() {
        _uiState.update { it.copy(isLiveTrackingActive = !it.isLiveTrackingActive) }
        showSnackbar(if (_uiState.value.isLiveTrackingActive) "Live competitor stream resumed" else "Live tracking paused")
    }

    private fun startLiveStreamSimulation() {
        liveStreamJob?.cancel()
        liveStreamJob = viewModelScope.launch {
            while (isActive) {
                delay(6000)
                if (_uiState.value.isLiveTrackingActive && _uiState.value.competitorList.isNotEmpty()) {
                    val updated = _uiState.value.competitorList.map { comp ->
                        val deltaLikes = (-5..15).random()
                        val deltaShares = (-2..8).random()
                        comp.copy(
                            avgLikesPerPost = maxOf(100, comp.avgLikesPerPost + deltaLikes),
                            avgSharesPerPost = maxOf(50, comp.avgSharesPerPost + deltaShares),
                            lastUpdatedMillis = System.currentTimeMillis()
                        )
                    }
                    _uiState.update { it.copy(competitorList = updated) }
                }
            }
        }
    }

    fun onSentimentInputChanged(text: String) {
        _uiState.update { it.copy(sentimentInputText = text) }
    }

    fun analyzeSentimentText(customText: String? = null) {
        val text = customText ?: _uiState.value.sentimentInputText
        if (text.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSentimentAnalyzing = true) }
            delay(400)
            val result = repository.analyzeSentiment(text)
            _uiState.update { it.copy(sentimentResult = result, isSentimentAnalyzing = false) }
        }
    }

    // --- Analytics Tab Actions ---
    private fun loadAnalytics() {
        viewModelScope.launch {
            val summary = repository.getAnalyticsSummary()
            _uiState.update { it.copy(analyticsSummary = summary) }
        }
    }

    private fun loadAdPlatforms() {
        val platforms = repository.getAdPlatforms()
        _uiState.update { it.copy(adPlatforms = platforms) }
    }

    fun openAdPlatformDetail(platform: AdPlatformAccount?) {
        _uiState.update { it.copy(selectedAdPlatformDetail = platform) }
    }

    fun toggleAdPlatformConnection(platformId: String) {
        val updated = _uiState.value.adPlatforms.map { p ->
            if (p.platformId == platformId) {
                val newStatus = !p.isConnected
                p.copy(
                    isConnected = newStatus,
                    statusLabel = if (newStatus) "Active & Synced" else "Disconnected"
                )
            } else p
        }
        _uiState.update { it.copy(adPlatforms = updated) }
        showSnackbar("Updated connection state for $platformId")
    }

    // --- AI Assistant / Chatbot ---
    fun toggleAiAssistant(open: Boolean) {
        _uiState.update { it.copy(isAiAssistantOpen = open) }
    }

    fun onAiInputChanged(text: String) {
        _uiState.update { it.copy(aiInputText = text) }
    }

    fun sendAiMessage() {
        val input = _uiState.value.aiInputText.trim()
        if (input.isBlank()) return

        val userMsg = AiChatMessage(sender = "You", text = input, isAi = false)
        _uiState.update {
            it.copy(
                aiChatMessages = it.aiChatMessages + userMsg,
                aiInputText = "",
                isAiThinking = true
            )
        }

        viewModelScope.launch {
            val responseResult = repository.askGeminiMarketingExpert(input)
            val replyText = responseResult.getOrElse {
                // High-precision built-in response based on query
                when {
                    input.contains("geo", ignoreCase = true) || input.contains("perplexity", ignoreCase = true) || input.contains("chatgpt", ignoreCase = true) ->
                        "To maximize Generative Engine Optimization (GEO) citations:\n1. Structure your answers with clear 'What / Why / How' headers.\n2. Embed exact statistical benchmarks and primary research in the first 100 words.\n3. Implement Schema.org TechArticle/FAQ JSON-LD with Entity IDs."
                    input.contains("niche", ignoreCase = true) ->
                        "The top 3 fastest-growing marketing niches in 2026 are: (1) Generative Engine Optimization consulting, (2) No-Code Autonomous AI Agent Workflows, and (3) Continuous Biomarker Longevity Nutrition."
                    input.contains("title", ignoreCase = true) || input.contains("headline", ignoreCase = true) ->
                        "For maximum CTR, use high-curiosity contrast or specific data: 'We Tested 100 Campaigns: The Surprising GEO Strategy That Generated 340% ROI'."
                    else ->
                        "Strategy Recommendation: Focus on building high-authority topical clusters with structured data. When users search on AI Answer Engines, models aggregate consensus from interconnected primary sources."
                }
            }

            val aiReply = AiChatMessage(sender = "AI Marketing Strategist", text = replyText, isAi = true)
            _uiState.update {
                it.copy(
                    aiChatMessages = it.aiChatMessages + aiReply,
                    isAiThinking = false
                )
            }
        }
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(userSnackbarMessage = message) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(userSnackbarMessage = null) }
    }
}
