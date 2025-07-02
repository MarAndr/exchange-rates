package com.example.exchangerates.ui.home.favorites.state

import com.example.exchangerates.features.rates.entities.CurrencySymbol

data class FavoritesScreenState(
    val favoriteRates: List<FavoriteRateUiModel>,
    val isLoading: Boolean = false,
)

data class FavoriteRateUiModel(
    val base: CurrencySymbol,
    val symbol: CurrencySymbol,
    val rate: Double?,
)