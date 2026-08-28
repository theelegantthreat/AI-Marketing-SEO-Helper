package com.example.data.service

import com.example.data.model.*
import java.util.UUID
import kotlin.random.Random

object MarketingIntelligenceEngine {

    // 1. Trending Niche Finder - Exactly 10 Trending Niches
    fun generateTrendingNiches(selectedCategory: String = "All"): List<NicheItem> {
        val allNiches = listOf(
            NicheItem(
                id = "niche_geo_seo",
                title = "Generative Engine Optimization (GEO) & AEO Consulting",
                category = "Tech & AI",
                summary = "Helping brands rank and get directly cited inside ChatGPT, Perplexity, Google Gemini, and Claude AI answer overviews.",
                growthRatePercent = 285,
                competitionLevel = CompetitionLevel.LOW,
                monetizationPotential = MonetizationLevel.VERY_HIGH,
                geoAeoReadinessScore = 98,
                estimatedMonthlySearchVolume = "540K/mo",
                avgCpcUsd = 6.40,
                subNiches = listOf("AEO Schema Structuring", "AI Citation Auditing", "Entity Authority Building", "Perplexity Brand Positioning"),
                monetizationMethods = listOf("High-Ticket Agency Retainers ($5k-$15k/mo)", "AI Readiness Audits", "B2B SaaS Tooling"),
                targetAudience = "E-Commerce Brands, SaaS Founders, B2B Marketing VPs",
                aiSearchTips = "Use structured question-answer markup, cite academic and primary research, and embed statistics directly in first 100 words."
            ),
            NicheItem(
                id = "niche_ai_automation",
                title = "No-Code AI Agent Workflows for Small Businesses",
                category = "Tech & AI",
                summary = "Custom autonomous multi-agent systems automating customer onboarding, CRM updates, and lead nurturing for local service providers.",
                growthRatePercent = 210,
                competitionLevel = CompetitionLevel.MEDIUM,
                monetizationPotential = MonetizationLevel.VERY_HIGH,
                geoAeoReadinessScore = 92,
                estimatedMonthlySearchVolume = "420K/mo",
                avgCpcUsd = 5.20,
                subNiches = listOf("Make.com / n8n Agent Blueprints", "Voice AI Receptionists", "Zapier Enterprise Systems"),
                monetizationMethods = listOf("Implementation Fees ($2,500+)", "Monthly Workflow Maintenance", "Template Marketplace"),
                targetAudience = "Dental Clinics, Real Estate Brokerages, Law Firms",
                aiSearchTips = "Create step-by-step implementation tutorials with explicit decision trees that LLMs cite as definitive guides."
            ),
            NicheItem(
                id = "niche_longevity_biohack",
                title = "At-Home Biomarker Tracking & Longevity Nutrition",
                category = "Health & Wellness",
                summary = "Personalized metabolic health, continuous glucose monitoring (CGM) analysis, and cellular rejuvenation protocols.",
                growthRatePercent = 175,
                competitionLevel = CompetitionLevel.MEDIUM,
                monetizationPotential = MonetizationLevel.HIGH,
                geoAeoReadinessScore = 88,
                estimatedMonthlySearchVolume = "680K/mo",
                avgCpcUsd = 4.10,
                subNiches = listOf("Continuous Glucose Dieting", "Biological Age Tests", "NAD+ Supplement Stacks"),
                monetizationMethods = listOf("High-Ticket Affiliate (Lab Tests)", "Subscription Community", "Personal Protocol Consulting"),
                targetAudience = "Health Enthusiasts, Tech Executives, Biohackers 30-55",
                aiSearchTips = "Include clinical study citations (PMID numbers) and dosage guidelines to trigger top medical AI answer inclusion."
            ),
            NicheItem(
                id = "niche_micro_saas",
                title = "Vertical Micro-SaaS for Niche Professional Crafts",
                category = "SaaS & B2B",
                summary = "Hyper-targeted, single-utility software tools built for underserved professions like boutique coffee roasters or boat mechanics.",
                growthRatePercent = 190,
                competitionLevel = CompetitionLevel.LOW,
                monetizationPotential = MonetizationLevel.VERY_HIGH,
                geoAeoReadinessScore = 85,
                estimatedMonthlySearchVolume = "310K/mo",
                avgCpcUsd = 5.80,
                subNiches = listOf("Specialty Inventory Trackers", "Quote Estimator Apps", "Boutique Compliance Loggers"),
                monetizationMethods = listOf("Monthly Recurring SaaS ($49-$199/mo)", "Lifetime Deal Launches", "Add-on API Integrations"),
                targetAudience = "Solo Operators, Craft Workshop Owners, Independent Contractors",
                aiSearchTips = "Target long-tail software comparison queries like 'best alternative to QuickBooks for mobile groomers'."
            ),
            NicheItem(
                id = "niche_creator_ip",
                title = "AI Avatar Licensing & Digital IP for Creators",
                category = "Creator Economy",
                summary = "Tools, legal frameworks, and platforms allowing influencers to license digital voice clones and multi-language AI video avatars.",
                growthRatePercent = 240,
                competitionLevel = CompetitionLevel.LOW,
                monetizationPotential = MonetizationLevel.HIGH,
                geoAeoReadinessScore = 90,
                estimatedMonthlySearchVolume = "290K/mo",
                avgCpcUsd = 3.90,
                subNiches = listOf("Multilingual Video Localization", "Synthetic Voice Rights Management", "AI Brand Endorsements"),
                monetizationMethods = listOf("Licensing Royalty Split", "Studio Production Retainer", "Digital Rights Legal Kits"),
                targetAudience = "YouTubers, Podcasters, Global Keynote Speakers",
                aiSearchTips = "Highlight copyright legislation and technical benchmark comparisons to dominate conversational search queries."
            ),
            NicheItem(
                id = "niche_eco_packaging",
                title = "Zero-Waste Compostable Packaging for DTC Brands",
                category = "E-Commerce",
                summary = "Mushroom mycelium, seaweed-based wraps, and biodegradable mailers replacing single-use plastics for direct-to-consumer retailers.",
                growthRatePercent = 160,
                competitionLevel = CompetitionLevel.MEDIUM,
                monetizationPotential = MonetizationLevel.HIGH,
                geoAeoReadinessScore = 82,
                estimatedMonthlySearchVolume = "390K/mo",
                avgCpcUsd = 3.50,
                subNiches = listOf("Mushroom Foam Mailers", "Water-Soluble Garment Bags", "Custom Branded Seed-Paper Inserts"),
                monetizationMethods = listOf("B2B Wholesale Supply Contracts", "Dropshipping Packaging Brokerage", "Green Certification Consulting"),
                targetAudience = "Eco-Conscious DTC Brands, Boutique Apparel, Organic Cosmetic Lines",
                aiSearchTips = "Publish comparative breakdown charts of ASTM D6400 certification specs that answer engines prioritize for sustainability queries."
            ),
            NicheItem(
                id = "niche_rwa_tokenization",
                title = "Real-World Asset (RWA) Fractional Investing",
                category = "Finance & Crypto",
                summary = "Democratized ownership of tokenized commercial real estate, luxury art, and treasury bills on institutional-grade blockchains.",
                growthRatePercent = 220,
                competitionLevel = CompetitionLevel.MEDIUM,
                monetizationPotential = MonetizationLevel.VERY_HIGH,
                geoAeoReadinessScore = 89,
                estimatedMonthlySearchVolume = "510K/mo",
                avgCpcUsd = 7.20,
                subNiches = listOf("Tokenized Commercial Debt", "Fractional Blue-Chip Art", "DeFi Yield Aggregation on Treasuries"),
                monetizationMethods = listOf("Broker-Dealer Referral Fees", "Educational Alpha Membership", "Asset Management Commission"),
                targetAudience = "Retail Investors, Family Offices, Accredited Tech Angels",
                aiSearchTips = "Provide transparent APY historical calculations and regulatory compliance overviews."
            ),
            NicheItem(
                id = "niche_fractional_cmo",
                title = "Fractional CMO & Growth Architect for Series-A Startups",
                category = "SaaS & B2B",
                summary = "Part-time executive marketing strategy, customer acquisition architecture, and full-funnel attribution for venture-backed founders.",
                growthRatePercent = 180,
                competitionLevel = CompetitionLevel.LOW,
                monetizationPotential = MonetizationLevel.VERY_HIGH,
                geoAeoReadinessScore = 94,
                estimatedMonthlySearchVolume = "260K/mo",
                avgCpcUsd = 8.50,
                subNiches = listOf("Go-To-Market Product Launches", "PLG (Product-Led Growth) Systems", "B2B Demand Gen Frameworks"),
                monetizationMethods = listOf("Executive Advisory Retainers ($6k-$12k/mo)", "Advisory Equity Grants", "Growth Workshop Intensives"),
                targetAudience = "Seed & Series-A Tech Founders, Venture Capital Operating Partners",
                aiSearchTips = "Publish verified teardowns of real SaaS growth metrics and cohort retention diagrams."
            ),
            NicheItem(
                id = "niche_solar_smart_grid",
                title = "Home Battery Storage & Dynamic Virtual Power Plants",
                category = "Sustainability",
                summary = "Smart home micro-grid optimization, time-of-use energy arbitrage, and peer-to-peer neighborhood renewable power sharing.",
                growthRatePercent = 195,
                competitionLevel = CompetitionLevel.MEDIUM,
                monetizationPotential = MonetizationLevel.HIGH,
                geoAeoReadinessScore = 86,
                estimatedMonthlySearchVolume = "480K/mo",
                avgCpcUsd = 4.80,
                subNiches = listOf("Grid Arbitrage Software", "Backup Battery Comparisons", "Solar Rebate Automated Filers"),
                monetizationMethods = listOf("Solar & Storage Installation Leads ($150-$400/lead)", "Affiliate Hardware Sales", "Energy Savings Calculator Tools"),
                targetAudience = "Suburban Homeowners, Electric Vehicle Owners, Preppers",
                aiSearchTips = "Utilize local kilowatt-hour cost calculation schemas to win rich snippet answer cards."
            ),
            NicheItem(
                id = "niche_neurodivergent_productivity",
                title = "Specialized ADHD & Neurodivergent Executive Tools",
                category = "Health & Wellness",
                summary = "Visual task schedulers, body doubling platforms, and sensory-friendly workspace ecosystems designed for adult ADHD brains.",
                growthRatePercent = 230,
                competitionLevel = CompetitionLevel.LOW,
                monetizationPotential = MonetizationLevel.HIGH,
                geoAeoReadinessScore = 91,
                estimatedMonthlySearchVolume = "620K/mo",
                avgCpcUsd = 2.90,
                subNiches = listOf("Gamified Dopamine Planners", "Virtual Body-Doubling Co-Working", "Sensory Ergonomic Gear"),
                monetizationMethods = listOf("Mobile App In-App Subscriptions", "Digital Notion/Obsidian Templates", "Physical Ergonomic Dropshipping"),
                targetAudience = "Adult Professionals with ADHD, Remote Tech Workers, University Students",
                aiSearchTips = "Format content in bulleted scannable blocks with zero fluff, exactly matching neurodivergent cognitive preference."
            )
        )

        return if (selectedCategory == "All") {
            allNiches
        } else {
            val filtered = allNiches.filter { it.category.equals(selectedCategory, ignoreCase = true) }
            if (filtered.size >= 10) filtered.take(10) else allNiches.take(10)
        }
    }

    // 2. Long-Tail Keyword Generator (with word count threshold & intent mapping)
    fun generateLongTailKeywords(seedTopic: String, minWordCount: Int = 3): List<KeywordItem> {
        val topic = if (seedTopic.isBlank()) "Generative Engine Optimization" else seedTopic.trim()

        val templates = listOf(
            Triple("how to optimize website for %s ai answers", SearchIntent.INFORMATIONAL, "Easy"),
            Triple("best %s tools for b2b saas companies", SearchIntent.COMMERCIAL, "Moderate"),
            Triple("step by step %s implementation guide for beginners", SearchIntent.INFORMATIONAL, "Easy"),
            Triple("buy affordable %s consulting services online", SearchIntent.TRANSACTIONAL, "Moderate"),
            Triple("what is the difference between seo and %s", SearchIntent.INFORMATIONAL, "Easy"),
            Triple("%s audit checklist and citation ranking factors", SearchIntent.COMMERCIAL, "Moderate"),
            Triple("hire certified %s specialist with proven case studies", SearchIntent.TRANSACTIONAL, "Hard"),
            Triple("top 10 %s strategies to get cited in perplexity", SearchIntent.INFORMATIONAL, "Easy"),
            Triple("how much does enterprise %s software cost per month", SearchIntent.COMMERCIAL, "Moderate"),
            Triple("%s official api documentation and webhook integration", SearchIntent.NAVIGATIONAL, "Moderate"),
            Triple("why %s is replacing traditional google search marketing", SearchIntent.INFORMATIONAL, "Easy"),
            Triple("download free %s template and executive reporting dashboard", SearchIntent.TRANSACTIONAL, "Easy")
        )

        return templates.mapIndexedNotNull { index, (template, intent, diff) ->
            val phrase = template.replace("%s", topic)
            val words = phrase.split("\\s+".toRegex()).size
            if (words >= minWordCount) {
                KeywordItem(
                    id = "kw_${UUID.randomUUID().toString().take(8)}",
                    keyword = phrase,
                    seedTopic = topic,
                    wordCount = words,
                    monthlySearchVolume = (1200..38000).random(),
                    competitionScore = when (diff) {
                        "Easy" -> (15..38).random()
                        "Moderate" -> (42..65).random()
                        else -> (70..88).random()
                    },
                    cpcUsd = String.format("%.2f", (1.50 + Random.nextDouble() * 7.50)).toDouble(),
                    intent = intent,
                    geoCitationLikelihood = (78..99).random(),
                    trendingDeltaPercent = (18..145).random(),
                    difficultyLabel = diff
                )
            } else null
        }.take(10)
    }

    // 3. Article Title Generator - Exactly 10 Catchy SEO & AEO Titles
    fun generateArticleTitles(targetKeyword: String): List<ArticleTitleItem> {
        val kw = if (targetKeyword.isBlank()) "Generative Engine Optimization (GEO)" else targetKeyword.trim()

        val rawTitles = listOf(
            Pair("How to Master $kw in 2026: The Definitive Step-by-Step Blueprint", TitleStyle.HOW_TO),
            Pair("10 Proven $kw Strategies That Will Double Your AI Search Traffic", TitleStyle.LISTICLE),
            Pair("Why Traditional SEO Is Dead and $kw Is the Only Survival Strategy", TitleStyle.CONTROVERSIAL),
            Pair("We Analyzed 1,000 AI Overviews: What the Data Reveals About $kw", TitleStyle.DATA_DRIVEN),
            Pair("The Ultimate $kw Playbook: Get Your Brand Cited by Gemini and ChatGPT", TitleStyle.ULTIMATE_GUIDE),
            Pair("How We Scaled Organic Inbound by 340% Using Modern $kw Tactics", TitleStyle.HOW_TO),
            Pair("7 Costly $kw Mistakes Most Marketing Agencies Are Making Right Now", TitleStyle.LISTICLE),
            Pair("Stop Burning Ad Budget: The $kw Case Study That Surprised Everyone", TitleStyle.CONTROVERSIAL),
            Pair("2026 Industry Benchmark Report: State of $kw and Answer Engines", TitleStyle.DATA_DRIVEN),
            Pair("Complete Zero-Click Search Guide: Winning the #1 AI Answer Spot With $kw", TitleStyle.ULTIMATE_GUIDE)
        )

        return rawTitles.mapIndexed { index, (titleText, style) ->
            val words = titleText.split("\\s+".toRegex()).size
            ArticleTitleItem(
                id = "title_$index",
                title = titleText,
                keyword = kw,
                style = style,
                estimatedCtrPercent = String.format("%.1f", 12.0 + Random.nextDouble() * 9.5).toDouble(),
                emotionalScore = (72..96).random(),
                wordCount = words,
                characterCount = titleText.length,
                aeoAnswerTarget = when (style) {
                    TitleStyle.HOW_TO -> "Featured as primary ordered list in AI overview cards"
                    TitleStyle.LISTICLE -> "Scraped for bullet-point comparison summaries by Perplexity"
                    TitleStyle.CONTROVERSIAL -> "Cited in synthesis queries comparing conflicting marketing viewpoints"
                    TitleStyle.DATA_DRIVEN -> "Directly referenced for statistical claims and market benchmarks"
                    TitleStyle.ULTIMATE_GUIDE -> "Ranked as the foundational pillar link across all conversational prompts"
                }
            )
        }
    }

    // 4. Automated Social Media Post Creator
    fun generateSocialMediaPostPackage(sourceTitle: String, keyword: String): SocialPostPackage {
        val title = if (sourceTitle.isBlank()) "Mastering Generative Engine Optimization (GEO) in 2026" else sourceTitle
        val kw = if (keyword.isBlank()) "Generative Engine Optimization" else keyword

        val facebook = SocialPlatformPost(
            platform = SocialPlatform.FACEBOOK,
            content = """
🚀 If you are still relying exclusively on 2019 SEO tactics, your traffic is about to hit a brick wall.

Here is the unfiltered reality: Users aren't just scrolling 10 blue links anymore. They are asking ChatGPT, Google Gemini, and Perplexity for instant answers.

If your brand isn't structured for $kw, you are invisible to over 35% of buying intent searches.

In our latest deep dive, "$title", we break down:
✅ The 3 schema tags AI answer bots crawl first
✅ How to format your data tables for 1-click citation
✅ The exact primary research framework that drives authoritative backlinks

👇 Click the link in the first comment to read the entire blueprint and claim our free checklist!

What is your biggest challenge with AI search right now? Drop your thoughts below!
            """.trimIndent(),
            characterCount = 840,
            hashtags = listOf("#InternetMarketing", "#DigitalStrategy", "#SEO2026", "#GrowthHacking", "#AIOverviews"),
            callToAction = "Read the complete guide in the comments & download the free SOP!",
            hookLine = "Is your organic traffic silently evaporating into AI answer summaries?",
            visualAssetSuggestion = "High-contrast infographic showing 'Traditional Search vs AI Overview Conversion funnel'"
        )

        val instagram = SocialPlatformPost(
            platform = SocialPlatform.INSTAGRAM,
            content = """
Swipe to see how AI is rewriting search marketing forever ➡️

The new game isn’t just ranking #1. It’s becoming the CITATION SOURCE that AI engines trust and quote directly. 🎯

Here are 4 quick takeaways from "$title":

1️⃣ Structure your content with concise Q&A blocks.
2️⃣ Always lead with verified data and concrete numbers.
3️⃣ Publish original case studies (LLMs love primary sources).
4️⃣ Build entity authority across authoritative industry nodes.

💬 Comment "GEO2026" and we’ll DM you the complete PDF framework instantly!
            """.trimIndent(),
            characterCount = 590,
            hashtags = listOf("#MarketingTips", "#SEOStrategy", "#GEO", "#AEO", "#ContentCreator", "#TechMarketing", "#EntrepreneurLife", "#BusinessGrowth", "#SocialMediaStrategy", "#AItools"),
            callToAction = "Comment 'GEO2026' to get the full PDF blueprint delivered to your DMs!",
            hookLine = "Why #1 on Google is no longer enough in 2026...",
            visualAssetSuggestion = "Carousel with 5 dark-mode slides with neon purple badges and clean code-style typography"
        )

        val twitter = SocialPlatformPost(
            platform = SocialPlatform.TWITTER,
            content = """
Google search traffic is changing. If you're not optimizing for $kw, you're missing 40%+ of high-intent buyers.

Here's how to get cited in Gemini & Perplexity 🧵👇

1/ Lead with primary data
2/ Use FAQ schemas
3/ Cut the fluff

Read full guide:
$title

#SEO #Marketing #AI
            """.trimIndent(),
            characterCount = 276,
            hashtags = listOf("#SEO", "#Marketing", "#AI", "#BuildInPublic"),
            callToAction = "Retweet if helpful & check the thread for the full breakdown!",
            hookLine = "If you're not optimizing for $kw, you're missing 40%+ of buyers.",
            visualAssetSuggestion = "Clean chart graphic showing AI Citation Rate vs Content Structure"
        )

        val linkedin = SocialPlatformPost(
            platform = SocialPlatform.LINKEDIN,
            content = """
The transition from SEO to GEO (Generative Engine Optimization) is the largest paradigm shift in digital distribution since mobile-first indexing.

In our research breakdown, "$title", we identified the 3 primary signals that drive LLM knowledge retrieval:

1. Entity Co-occurrence: How frequently your brand is mapped alongside industry authority nodes.
2. Structured Precision: Formatted data tables and single-sentence answers within the first 150 words.
3. Unreplicated Primary Metrics: Proprietary data points that AI models cannot infer without citing you.

For B2B leaders and growth executives, adapting to this transition now creates an unassailable moat.

How is your marketing team adapting your 2026 distribution strategy?
            """.trimIndent(),
            characterCount = 780,
            hashtags = listOf("#B2BMarketing", "#ExecutiveLeadership", "#GrowthStrategy", "#AEO", "#SEO"),
            callToAction = "Share with your growth team and follow for weekly executive marketing analyses.",
            hookLine = "The transition from SEO to GEO is the largest paradigm shift since mobile indexing.",
            visualAssetSuggestion = "Professional corporate slide with executive summary benchmarks"
        )

        return SocialPostPackage(
            id = "social_pkg_${System.currentTimeMillis()}",
            sourceTitle = title,
            targetKeyword = kw,
            facebookPost = facebook,
            instagramPost = instagram,
            twitterPost = twitter,
            linkedinPost = linkedin
        )
    }

    // 5. Competitor Engagement Tracker
    fun getCompetitorEngagementData(): List<CompetitorItem> {
        return listOf(
            CompetitorItem(
                id = "comp_semrush_ai",
                brandName = "ApexMarketing Labs",
                websiteDomain = "apexmarketinglabs.io",
                nicheCategory = "B2B SaaS Growth",
                avgLikesPerPost = 3420,
                avgSharesPerPost = 890,
                avgCommentsPerPost = 215,
                postingFrequencyPerWeek = 8.5,
                engagementRatePercent = 5.64,
                sentimentScorePercent = 84,
                sentimentBreakdown = SentimentSummary(positivePercent = 84, neutralPercent = 12, negativePercent = 4),
                topHashtags = listOf("#SaaSGrowth", "#B2BMarketing", "#AIWorkflows", "#GEO2026"),
                geoPresenceScore = 92,
                platformPresence = listOf(
                    PlatformMetric("LinkedIn", "148K", 4.2, "Highly Active"),
                    PlatformMetric("Twitter/X", "82K", 2.8, "Daily Threads"),
                    PlatformMetric("YouTube", "45K", 6.1, "Weekly Tutorials")
                )
            ),
            CompetitorItem(
                id = "comp_nexus_media",
                brandName = "Nexus Growth Media",
                websiteDomain = "nexusgrowthmedia.com",
                nicheCategory = "E-Commerce & DTC",
                avgLikesPerPost = 5890,
                avgSharesPerPost = 1450,
                avgCommentsPerPost = 430,
                postingFrequencyPerWeek = 14.0,
                engagementRatePercent = 6.82,
                sentimentScorePercent = 78,
                sentimentBreakdown = SentimentSummary(positivePercent = 78, neutralPercent = 16, negativePercent = 6),
                topHashtags = listOf("#DTCBrand", "#TikTokAds", "#ShopifyScaling", "#CreativeStrategy"),
                geoPresenceScore = 86,
                platformPresence = listOf(
                    PlatformMetric("Instagram", "320K", 5.4, "Daily Carousels"),
                    PlatformMetric("TikTok", "510K", 8.9, "Multiple Daily Reels"),
                    PlatformMetric("Meta Ads", "Active", 0.0, "32 Active Ad Creatives")
                )
            ),
            CompetitorItem(
                id = "comp_hyper_organic",
                brandName = "HyperOrganic SEO",
                websiteDomain = "hyperorganicseo.co",
                nicheCategory = "AEO & Search Tech",
                avgLikesPerPost = 1850,
                avgSharesPerPost = 620,
                avgCommentsPerPost = 142,
                postingFrequencyPerWeek = 5.2,
                engagementRatePercent = 4.31,
                sentimentScorePercent = 91,
                sentimentBreakdown = SentimentSummary(positivePercent = 91, neutralPercent = 7, negativePercent = 2),
                topHashtags = listOf("#SearchEngineOpt", "#PerplexityAI", "#KnowledgeGraph", "#SchemaMarkup"),
                geoPresenceScore = 96,
                platformPresence = listOf(
                    PlatformMetric("Twitter/X", "64K", 3.1, "Technical Breakdowns"),
                    PlatformMetric("LinkedIn", "92K", 4.8, "Case Studies"),
                    PlatformMetric("Newsletter", "38K", 7.5, "Weekly Deep Dives")
                )
            )
        )
    }

    // 6. AI-Driven Sentiment Analysis & NLP Engine
    fun analyzeSentiment(text: String): SentimentAnalysisResult {
        val trimmed = text.trim()
        val lower = trimmed.lowercase()

        val positiveWords = listOf("love", "great", "amazing", "excellent", "best", "helpful", "recommend", "brilliant", "fantastic", "effective", "valuable", "roi", "gamechanger", "huge win")
        val negativeWords = listOf("bad", "terrible", "worst", "waste", "buggy", "expensive", "slow", "broken", "scam", "frustrated", "disappointed", "annoying", "poor", "hate")

        var posCount = 0
        var negCount = 0

        positiveWords.forEach { if (lower.contains(it)) posCount += 2 }
        negativeWords.forEach { if (lower.contains(it)) negCount += 2 }

        val sentimentType: SentimentType
        val tone: String
        val posScore: Int
        val neuScore: Int
        val negScore: Int
        val confidence: Double
        val brandImpact: String
        val suggestedResponse: String

        if (posCount > negCount) {
            sentimentType = SentimentType.POSITIVE
            tone = "Enthusiastic & Highly Satisfied"
            posScore = minOf(95, 70 + posCount * 5)
            negScore = maxOf(2, 10 - posCount * 2)
            neuScore = 100 - posScore - negScore
            confidence = 0.94
            brandImpact = "Positive citation booster. AI engines (Gemini & ChatGPT) index this sentiment to recommend your brand for high-intent queries."
            suggestedResponse = "Thank you so much for the incredible feedback! We are thrilled to see these results and appreciate your partnership. Let's keep scaling together! 🚀"
        } else if (negCount > posCount) {
            sentimentType = SentimentType.NEGATIVE
            tone = "Frustrated / Critical Feedback"
            negScore = minOf(92, 65 + negCount * 6)
            posScore = maxOf(4, 12 - negCount * 2)
            neuScore = 100 - posScore - negScore
            confidence = 0.91
            brandImpact = "Risk of negative sentiment grounding in AI overviews. Recommended immediate proactive resolution to preserve entity reputation."
            suggestedResponse = "We sincerely apologize for this frustration. We want to make this right immediately—please reach out to us at priority@ourbrand.com or DM us so our senior team can resolve this today."
        } else {
            sentimentType = SentimentType.NEUTRAL
            tone = "Inquisitive / Informational Inquiry"
            neuScore = 70
            posScore = 18
            negScore = 12
            confidence = 0.86
            brandImpact = "Neutral brand context. Represents prime conversion opportunity to provide authoritative clarity."
            suggestedResponse = "Great point! We appreciate your question. Here is a quick breakdown to give you full clarity on how this functions in practice. Let us know if you need additional details!"
        }

        val extractedPhrases = trimmed.split(".", ",", "\n")
            .map { it.trim() }
            .filter { it.length > 10 }
            .take(3)
            .ifEmpty { listOf("Key inquiry regarding core performance and integration.") }

        return SentimentAnalysisResult(
            inputText = trimmed,
            sentiment = sentimentType,
            confidenceScore = confidence,
            emotionalTone = tone,
            positiveScore = posScore,
            neutralScore = neuScore,
            negativeScore = negScore,
            keyExtractedPhrases = extractedPhrases,
            geoBrandImpact = brandImpact,
            suggestedAiResponse = suggestedResponse
        )
    }

    // 7. Analytics Summary & Automated Reporting
    fun getAnalyticsSummary(): MarketingAnalyticsSummary {
        val dailyPoints = listOf(
            DailyMetricPoint("Mon", impressions = 184000, clicks = 8900, spendUsd = 1420.0, revenueUsd = 6200.0, ctrPercent = 4.84, roiPercent = 336.6),
            DailyMetricPoint("Tue", impressions = 210000, clicks = 10400, spendUsd = 1650.0, revenueUsd = 7400.0, ctrPercent = 4.95, roiPercent = 348.5),
            DailyMetricPoint("Wed", impressions = 198000, clicks = 9800, spendUsd = 1580.0, revenueUsd = 6950.0, ctrPercent = 4.95, roiPercent = 339.8),
            DailyMetricPoint("Thu", impressions = 245000, clicks = 12600, spendUsd = 1890.0, revenueUsd = 8800.0, ctrPercent = 5.14, roiPercent = 365.6),
            DailyMetricPoint("Fri", impressions = 260000, clicks = 13800, spendUsd = 2050.0, revenueUsd = 9700.0, ctrPercent = 5.31, roiPercent = 373.1),
            DailyMetricPoint("Sat", impressions = 175000, clicks = 7900, spendUsd = 1210.0, revenueUsd = 4900.0, ctrPercent = 4.51, roiPercent = 304.9),
            DailyMetricPoint("Sun", impressions = 148000, clicks = 7100, spendUsd = 1100.0, revenueUsd = 4300.0, ctrPercent = 4.79, roiPercent = 290.9)
        )

        val channels = listOf(
            ChannelPerformance("GEO & AEO Inbound (AI Citations)", spendUsd = 1200.0, revenueUsd = 14800.0, roas = 12.33, sharePercent = 31, status = "Top Performer"),
            ChannelPerformance("Meta Ads (IG/FB Retargeting)", spendUsd = 4100.0, revenueUsd = 16200.0, roas = 3.95, sharePercent = 34, status = "Scaling"),
            ChannelPerformance("Google Performance Max", spendUsd = 3400.0, revenueUsd = 11900.0, roas = 3.50, sharePercent = 25, status = "Stable"),
            ChannelPerformance("Organic Search & Blog Pillars", spendUsd = 800.0, revenueUsd = 5350.0, roas = 6.68, sharePercent = 10, status = "Growing")
        )

        val report = ExecutiveSummaryReport(
            reportId = "REP_EXECUTIVE_${System.currentTimeMillis().toString().takeLast(6)}",
            dateString = "August 2026 - Real-Time Snapshot",
            headline = "Record 342.5% Blended ROI Driven by Exponential Generative Engine Citations",
            executiveScore = 94,
            keyWins = listOf(
                "GEO/AEO AI citations now drive 31% of total inbound revenue at an extraordinary 12.33x ROAS.",
                "Click-Through Rate (CTR) hit a 30-day high of 5.31% on optimized carousel ad creatives.",
                "Customer Acquisition Cost (CAC) decreased from $7.40 to $5.92 after deploying FAQ schema overhauls."
            ),
            growthOpportunities = listOf(
                "Scale Meta Ads daily budget by +20% on the top-performing 'How-To' video creatives.",
                "Publish 4 new structured case study articles targeting high-intent commercial keywords.",
                "Implement automated review outreach to lift positive sentiment score above 90%."
            ),
            geoAeoActionPlan = "Deploy schema JSON-LD with Entity relations and add primary research benchmark metrics to all tier-1 blog headers.",
            criticalAlerts = listOf(
                "Competitor 'ApexMarketing Labs' increased LinkedIn posting frequency by +35% this week."
            ),
            budgetAdjustmentRecommendation = "Shift $1,500/mo from low-converting broad Google search keywords into high-ROAS Meta retargeting and GEO pillar creation."
        )

        return MarketingAnalyticsSummary(
            currentRoiPercent = 342.5,
            totalRevenueUsd = 48250.0,
            totalSpendUsd = 10900.0,
            totalImpressions = 1420000,
            avgClickThroughRatePercent = 4.82,
            totalConversions = 1840,
            avgCostPerAcquisitionUsd = 5.92,
            aeoAnswerEngineSharePercent = 28.4,
            dailyTrendData = dailyPoints,
            channelBreakdown = channels,
            latestExecutiveReport = report
        )
    }

    // 8. External Ad Platform API Integration Status
    fun getAdPlatformAccounts(): List<AdPlatformAccount> {
        return listOf(
            AdPlatformAccount(
                platformId = "meta_ads_live",
                name = "Meta Marketing API (v20.0)",
                platformType = AdPlatformType.META_ADS,
                isConnected = true,
                statusLabel = "Active & Synced",
                activeCampaignsCount = 6,
                dailyBudgetUsd = 250.0,
                targetRoas = 3.8,
                totalImpressions7d = 620000,
                currentSpend7dUsd = 4100.0,
                currentRoas7d = 3.95,
                apiEndpoint = "https://graph.facebook.com/v20.0/act_84719204/insights",
                syncLatencyMs = 142
            ),
            AdPlatformAccount(
                platformId = "google_ads_live",
                name = "Google Ads REST API (v17)",
                platformType = AdPlatformType.GOOGLE_ADS,
                isConnected = true,
                statusLabel = "Active & Synced",
                activeCampaignsCount = 4,
                dailyBudgetUsd = 200.0,
                targetRoas = 3.2,
                totalImpressions7d = 480000,
                currentSpend7dUsd = 3400.0,
                currentRoas7d = 3.50,
                apiEndpoint = "https://googleads.googleapis.com/v17/customers/938-204-1829/googleAds:searchStream",
                syncLatencyMs = 186
            ),
            AdPlatformAccount(
                platformId = "tiktok_ads_sandbox",
                name = "TikTok Marketing Partner API",
                platformType = AdPlatformType.TIKTOK_ADS,
                isConnected = false,
                statusLabel = "Sandbox / Ready to Connect",
                activeCampaignsCount = 0,
                dailyBudgetUsd = 100.0,
                targetRoas = 4.0,
                totalImpressions7d = 0,
                currentSpend7dUsd = 0.0,
                currentRoas7d = 0.0,
                apiEndpoint = "https://business-api.tiktok.com/open_api/v1.3/campaign/get/",
                syncLatencyMs = 0
            ),
            AdPlatformAccount(
                platformId = "linkedin_ads_live",
                name = "LinkedIn Campaign Manager API",
                platformType = AdPlatformType.LINKEDIN_ADS,
                isConnected = true,
                statusLabel = "Active & Synced",
                activeCampaignsCount = 2,
                dailyBudgetUsd = 120.0,
                targetRoas = 4.5,
                totalImpressions7d = 190000,
                currentSpend7dUsd = 1600.0,
                currentRoas7d = 4.80,
                apiEndpoint = "https://api.linkedin.com/rest/adAnalyticsV2",
                syncLatencyMs = 210
            )
        )
    }
}
