package com.example.exchangerates.ui.common.theme.color

import androidx.compose.ui.graphics.Color

private val BackgroundColorTokens = BackgroundColors(
    default = Color(0xFFFFFFFF),
    card = Color(0xFFF0F2F8),
)

private val MainColorTokens = MainColors(
    primary = Color(0xFF0A2FA7),
    secondary = Color(0xFF9DACDC),
    textDefault = Color(0xFF343138),
    textSecondary = Color(0xFF767676),
)

internal val AppColorTokens = AppColors(
    bg = BackgroundColorTokens,
    mainColors = MainColorTokens,
)