package com.example.f1fantasyleague.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

@Immutable
data class F1Colors(
    val backgroundPrimary: Color,
    val surfacePrimary: Color,
    val surfaceSecondary: Color,
    val borderSubtle: Color,
    val brandPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color
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
fun F1FantasyLeagueTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkF1Colors else LightF1Colors
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalF1Colors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content)
    }
}