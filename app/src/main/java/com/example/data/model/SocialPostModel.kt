package com.example.data.model

data class SocialPostPackage(
    val id: String,
    val sourceTitle: String,
    val targetKeyword: String,
    val facebookPost: SocialPlatformPost,
    val instagramPost: SocialPlatformPost,
    val twitterPost: SocialPlatformPost,
    val linkedinPost: SocialPlatformPost,
    val createdAtMillis: Long = System.currentTimeMillis()
)

data class SocialPlatformPost(
    val platform: SocialPlatform,
    val content: String,
    val characterCount: Int,
    val hashtags: List<String>,
    val callToAction: String,
    val hookLine: String,
    val visualAssetSuggestion: String
)

enum class SocialPlatform(val displayName: String, val maxChars: Int, val iconName: String) {
    FACEBOOK("Facebook", 63206, "facebook"),
    INSTAGRAM("Instagram", 2200, "camera_alt"),
    TWITTER("Twitter / X", 280, "tag"),
    LINKEDIN("LinkedIn", 3000, "work")
}
