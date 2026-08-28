package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryLight,
    onPrimary = DarkBackground,
    primaryContainer = BluePrimaryDark,
    onPrimaryContainer = BluePrimaryContainer,
    secondary = LilacAccentLight,
    onSecondary = DarkBackground,
    secondaryContainer = LilacSecondary,
    onSecondaryContainer = LilacSecondaryContainer,
    tertiary = TealTertiaryContainer,
    onTertiary = DarkBackground,
    tertiaryContainer = TealTertiary,
    onTertiaryContainer = OnTealTertiaryContainer,
    background = DarkBackground,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextWhite.copy(alpha = 0.7f),
    outline = DarkOutline,
    error = RoseAccent,
    errorContainer = RoseContainer,
    onErrorContainer = OnRoseContainer
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = TextWhite,
    primaryContainer = BluePrimaryContainer,
    onPrimaryContainer = OnBluePrimaryContainer,
    secondary = LilacSecondary,
    onSecondary = TextWhite,
    secondaryContainer = LilacSecondaryContainer,
    onSecondaryContainer = OnLilacSecondary,
    tertiary = TealTertiary,
    onTertiary = TextWhite,
    tertiaryContainer = TealTertiaryContainer,
    onTertiaryContainer = OnTealTertiaryContainer,
    background = LightBackground,
    onBackground = TextDark,
    surface = LightSurface,
    onSurface = TextDark,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = LightOutline,
    error = RoseAccent,
    errorContainer = RoseContainer,
    onErrorContainer = OnRoseContainer
)

@Composable
fun AiMarketingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    AiMarketingTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
