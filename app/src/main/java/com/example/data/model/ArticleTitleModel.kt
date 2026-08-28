package com.example.data.model

data class ArticleTitleItem(
    val id: String,
    val title: String,
    val keyword: String,
    val style: TitleStyle,
    val estimatedCtrPercent: Double, // e.g. 14.8%
    val emotionalScore: Int, // 0 - 100
    val wordCount: Int,
    val characterCount: Int,
    val aeoAnswerTarget: String, // How AI engine features this headline
    val isSaved: Boolean = false
)

enum class TitleStyle(val label: String, val iconName: String) {
    HOW_TO("How-To Guide", "help_outline"),
    LISTICLE("Listicle", "format_list_numbered"),
    CONTROVERSIAL("Controversial / Opinion", "warning_amber"),
    DATA_DRIVEN("Data-Driven / Case Study", "insights"),
    ULTIMATE_GUIDE("Ultimate AEO Guide", "auto_awesome")
}
