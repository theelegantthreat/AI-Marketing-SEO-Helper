package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SentimentType
import com.example.ui.theme.SentimentNegative
import com.example.ui.theme.SentimentNeutral
import com.example.ui.theme.SentimentPositive

@Composable
fun SentimentBadge(
    sentiment: SentimentType,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon) = when (sentiment) {
        SentimentType.POSITIVE -> Triple(
            SentimentPositive.copy(alpha = 0.15f),
            SentimentPositive,
            Icons.Default.Mood
        )
        SentimentType.NEUTRAL -> Triple(
            SentimentNeutral.copy(alpha = 0.15f),
            SentimentNeutral,
            Icons.Default.SentimentNeutral
        )
        SentimentType.NEGATIVE -> Triple(
            SentimentNegative.copy(alpha = 0.15f),
            SentimentNegative,
            Icons.Default.MoodBad
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = sentiment.label,
            tint = textColor,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = sentiment.label,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}
