package com.example.f1fantasyleague.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class F1Colors(
    val backgroundPrimary: androidx.compose.ui.graphics.Color,
    val surfacePrimary: androidx.compose.ui.graphics.Color,
    val surfaceSecondary: androidx.compose.ui.graphics.Color,
    val borderSubtle: androidx.compose.ui.graphics.Color,
    val brandPrimary: androidx.compose.ui.graphics.Color,
    val textPrimary: androidx.compose.ui.graphics.Color,
    val textSecondary: androidx.compose.ui.graphics.Color
)

private val DarkF1Colors = F1Colors(
    backgroundPrimary = BackgroundPrimary,
    surfacePrimary = SurfacePrimary,
    surfaceSecondary = SurfaceSecondary,
    borderSubtle = BorderSubtle,
    brandPrimary = BrandPrimary,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary
)

private val LightF1Colors = F1Colors(
    backgroundPrimary = BackgroundPrimary,
    surfacePrimary = SurfacePrimary,
    surfaceSecondary = SurfaceSecondary,
    borderSubtle = BorderSubtle,
    brandPrimary = BrandPrimary,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary
)

private val LocalF1Colors = staticCompositionLocalOf { DarkF1Colors }

object F1Theme {
    val colors: F1Colors
        @Composable get() = LocalF1Colors.current
}

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = TextPrimary,
    background = BackgroundPrimary,
    onBackground = TextPrimary,
    surface = SurfacePrimary,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceSecondary,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    tertiary = TextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = TextPrimary,
    background = BackgroundPrimary,
    onBackground = TextPrimary,
    surface = SurfacePrimary,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceSecondary,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    tertiary = TextSecondary
)

@Composable
fun F1FantasyLeageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    _dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkF1Colors else LightF1Colors
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalF1Colors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}