package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyMetricPoint
import com.example.ui.theme.*

@Composable
fun RoiTrendAreaChart(
    dailyPoints: List<DailyMetricPoint>,
    modifier: Modifier = Modifier
) {
    if (dailyPoints.isEmpty()) return

    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(dailyPoints) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(1f, animationSpec = tween(1000))
    }

    val maxRoi = dailyPoints.maxOfOrNull { it.roiPercent } ?: 400.0
    val minRoi = dailyPoints.minOfOrNull { it.roiPercent } ?: 200.0
    val roiRange = (maxRoi - minRoi).coerceAtLeast(1.0)

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val width = size.width
            val height = size.height
            val stepX = width / (dailyPoints.size - 1).coerceAtLeast(1)

            val strokePath = Path()
            val fillPath = Path()

            val points = dailyPoints.mapIndexed { index, point ->
                val normalizedY = ((point.roiPercent - minRoi) / roiRange).toFloat()
                val x = index * stepX
                // Invert Y and apply animation
                val targetY = height - (normalizedY * (height * 0.75f) + height * 0.15f)
                val animatedY = height - (height - targetY) * animatedProgress.value
                Offset(x, animatedY)
            }

            if (points.isNotEmpty()) {
                strokePath.moveTo(points.first().x, points.first().y)
                fillPath.moveTo(points.first().x, height)
                fillPath.lineTo(points.first().x, points.first().y)

                for (i in 0 until points.size - 1) {
                    val p0 = points[i]
                    val p1 = points[i + 1]
                    val controlX = (p0.x + p1.x) / 2f
                    strokePath.cubicTo(controlX, p0.y, controlX, p1.y, p1.x, p1.y)
                    fillPath.cubicTo(controlX, p0.y, controlX, p1.y, p1.x, p1.y)
                }

                fillPath.lineTo(points.last().x, height)
                fillPath.close()

                // Draw gradient fill
                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            IndigoPrimary.copy(alpha = 0.45f),
                            CyanSecondary.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    )
                )

                // Draw curve line
                drawPath(
                    path = strokePath,
                    brush = Brush.horizontalGradient(
                        colors = listOf(IndigoPrimary, CyanSecondary, EmeraldTertiary)
                    ),
                    style = Stroke(width = 3.5.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw point dots
                points.forEach { pt ->
                    drawCircle(
                        color = IndigoPrimary,
                        radius = 4.dp.toPx() * animatedProgress.value,
                        center = pt
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 2.dp.toPx() * animatedProgress.value,
                        center = pt
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // X-Axis day labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dailyPoints.forEach { point ->
                Text(
                    text = point.dayLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ImpressionsBarChart(
    dailyPoints: List<DailyMetricPoint>,
    modifier: Modifier = Modifier
) {
    if (dailyPoints.isEmpty()) return

    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(dailyPoints) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(1f, animationSpec = tween(900))
    }

    val maxImpressions = dailyPoints.maxOfOrNull { it.impressions } ?: 300000L

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            val width = size.width
            val height = size.height
            val barWidth = (width / dailyPoints.size) * 0.55f
            val spacing = width / dailyPoints.size

            dailyPoints.forEachIndexed { index, point ->
                val barHeightRatio = (point.impressions.toFloat() / maxImpressions.toFloat()).coerceIn(0.1f, 1.0f)
                val targetBarHeight = barHeightRatio * (height * 0.85f)
                val animatedBarHeight = targetBarHeight * animatedProgress.value
                val left = index * spacing + (spacing - barWidth) / 2f
                val top = height - animatedBarHeight

                // Draw Bar
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(CyanSecondary, IndigoPrimaryDark)
                    ),
                    topLeft = Offset(left, top),
                    size = Size(barWidth, animatedBarHeight),
                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            dailyPoints.forEach { point ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = point.dayLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "${point.impressions / 1000}k",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
