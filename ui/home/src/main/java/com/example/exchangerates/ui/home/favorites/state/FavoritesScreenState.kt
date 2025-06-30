package com.example.exchangerates.ui.home.favorites.state

sealed interface FavoritesScreenState {
    data class Data(
        val favoriteRates: List<FavoriteRateUiModel>,
        val isLoading: Boolean = false,
    ) : FavoritesScreenState
}

data class FavoriteRateUiModel(
    val base: String,
    val symbol: String,
    val rate: Double?,
)