package com.cleanpulse.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PrimaryColor = Color(0xFF00B4FF)
private val PrimaryVariant = Color(0xFF0099CC)
private val SecondaryColor = Color(0xFF1A1A1A)
private val SecondaryVariant = Color(0xFF333333)
private val BackgroundColor = Color(0xFFFFFFFF)
private val SurfaceColor = Color(0xFFF5F5F5)
private val ErrorColor = Color(0xFFF44336)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F7FF),
    onPrimaryContainer = PrimaryVariant,
    secondary = SecondaryColor,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8E8E8),
    onSecondaryContainer = SecondaryVariant,
    tertiary = Color(0xFF4CAF50),
    onTertiary = Color.White,
    error = ErrorColor,
    onError = Color.White,
    background = BackgroundColor,
    onBackground = SecondaryColor,
    surface = SurfaceColor,
    onSurface = SecondaryColor,
    surfaceVariant = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFF666666),
    outline = Color(0xFFCCCCCC),
    outlineVariant = Color(0xFFE0E0E0)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = SecondaryColor,
    primaryContainer = Color(0xFF005A7F),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFFFFFF),
    onSecondary = SecondaryColor,
    secondaryContainer = Color(0xFF4A4A4A),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFF66BB6A),
    onTertiary = SecondaryColor,
    error = ErrorColor,
    onError = SecondaryColor,
    background = SecondaryColor,
    onBackground = Color.White,
    surface = Color(0xFF2A2A2A),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF3A3A3A),
    onSurfaceVariant = Color(0xFFCCCCCC),
    outline = Color(0xFF666666),
    outlineVariant = Color(0xFF4A4A4A)
)

@Composable
fun CleanPulseTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = CleanPulseTypography,
        shapes = CleanPulseShapes,
        content = content
    )
}
