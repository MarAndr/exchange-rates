package com.example.exchangerates.ui.common.theme.color

import androidx.compose.ui.graphics.Color

private val BackgroundColorTokens = BackgroundColors(
    default = Color(0xFFFFFFFF),
    card = Color(0xFFF0F2F8),
    header = Color(0xFFF6F6F6)
)

private val MainColorTokens = MainColors(
    primary = Color(0xFF0A2FA7),
    lightPrimary = Color(0xFFDEE4F8),
    secondary = Color(0xFF9DACDC),
    textDefault = Color(0xFF343138),
    textSecondary = Color(0xFF767676),
    outline = Color(0xFFEBEBEB),
)

internal val AppColorTokens = AppColors(
    bg = BackgroundColorTokens,
    mainColors = MainColorTokens,
)