package com.example.exchangerates.ui.common.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.exchangerates.ui.common.theme.color.AppColorTokens
import com.example.exchangerates.ui.common.theme.color.AppColors
import com.example.exchangerates.ui.common.theme.typography.Typography

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = emptyColorScheme(),
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalAppColors provides AppColorTokens,
        ) {
            content()
        }
    }
}

object AppTheme {
    val color: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}

internal val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("Colors were not set up")
}

private fun emptyColorScheme(
    emptyColor: Color = Color.Magenta,
) = ColorScheme(
    primary = emptyColor,
    onPrimary = emptyColor,
    primaryContainer = emptyColor,
    onPrimaryContainer = emptyColor,
    inversePrimary = emptyColor,
    secondary = emptyColor,
    onSecondary = emptyColor,
    secondaryContainer = emptyColor,
    onSecondaryContainer = emptyColor,
    tertiary = emptyColor,
    onTertiary = emptyColor,
    tertiaryContainer = emptyColor,
    onTertiaryContainer = emptyColor,
    background = emptyColor,
    onBackground = emptyColor,
    surface = emptyColor,
    onSurface = emptyColor,
    surfaceVariant = emptyColor,
    onSurfaceVariant = emptyColor,
    surfaceTint = emptyColor,
    inverseSurface = emptyColor,
    inverseOnSurface = emptyColor,
    error = emptyColor,
    onError = emptyColor,
    errorContainer = emptyColor,
    onErrorContainer = emptyColor,
    outline = emptyColor,
    outlineVariant = emptyColor,
    scrim = emptyColor,
    surfaceBright = emptyColor,
    surfaceDim = emptyColor,
    surfaceContainer = emptyColor,
    surfaceContainerHigh = emptyColor,
    surfaceContainerHighest = emptyColor,
    surfaceContainerLow = emptyColor,
    surfaceContainerLowest = emptyColor,
)