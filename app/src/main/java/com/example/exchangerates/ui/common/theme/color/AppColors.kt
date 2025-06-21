package com.example.exchangerates.ui.common.theme.color

import androidx.compose.ui.graphics.Color

data class AppColors(
    val bg: BackgroundColors,
    val mainColors: MainColors,
)

data class BackgroundColors(
    val default: Color,
    val card: Color,
    val header: Color,
)

data class MainColors(
    val primary: Color,
    val secondary: Color,
    val textDefault: Color,
    val textSecondary: Color,
)