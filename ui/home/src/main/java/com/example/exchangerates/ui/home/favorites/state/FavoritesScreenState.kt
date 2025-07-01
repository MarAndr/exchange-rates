package com.example.exchangerates.ui.home.favorites.state

data class FavoritesScreenState(
    val favoriteRates: List<FavoriteRateUiModel>,
    val isLoading: Boolean = false,
)

data class FavoriteRateUiModel(
    val base: String,
    val symbol: String,
    val rate: Double?,
)