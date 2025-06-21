package com.example.exchangerates.ui.common.theme.color

import androidx.compose.ui.graphics.Color

private val BackgroundColorTokens = BackgroundColors(
    default = Color(0xFFFFFFFF),
    card = Color(0xFFF0F2F8),
)

private val MainColorTokens = MainColors(
    primary = Color(0xFF0A2FA7),
)

internal val AppColorTokens = AppColors(
    bg = BackgroundColorTokens,
    mainColors = MainColorTokens,
)