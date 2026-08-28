package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.NicheItem
import com.example.ui.components.CopyShareButtons
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NicheDetailDialog(
    niche: NicheItem,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onExploreKeywords: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    AssistChip(
                        onClick = {},
                        label = { Text(niche.category, fontWeight = FontWeight.SemiBold) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    Text(
                        text = niche.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.testTag("bookmark_niche_btn")
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Niche",
                        tint = if (isSaved) AmberAccent else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = niche.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )

            // Key Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Growth Rate", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("+${niche.growthRatePercent}%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = EmeraldTertiary)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Search Index", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(niche.estimatedMonthlySearchVolume, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = CyanSecondary)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Avg CPC", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("$${niche.avgCpcUsd}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = AmberAccent)
                    }
                }
            }

            // GEO & AEO AI Search Strategy Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = IndigoContainer.copy(alpha = 0.6f),
                border = androidx.compose.foundation.BorderStroke(1.dp, IndigoPrimary.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = CyanSecondary, modifier = Modifier.size(18.dp))
                        Text("GEO & AEO Citation Strategy", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = OnIndigoContainer)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = niche.aiSearchTips,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnIndigoContainer.copy(alpha = 0.9f),
                        lineHeight = 18.sp
                    )
                }
            }

            // Sub-Niches
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Target Sub-Niches", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                niche.subNiches.forEach { sub ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(16.dp))
                        Text(sub, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Monetization Methods
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Monetization Potential: ${niche.monetizationPotential.label}", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                niche.monetizationMethods.forEach { method ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = AmberAccent, modifier = Modifier.size(16.dp))
                        Text(method, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // Action Button
            Button(
                onClick = {
                    onExploreKeywords(niche.title)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("explore_niche_keywords_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
            ) {
                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Long-Tail Keywords for this Niche", fontWeight = FontWeight.Bold)
            }
        }
    }
}
