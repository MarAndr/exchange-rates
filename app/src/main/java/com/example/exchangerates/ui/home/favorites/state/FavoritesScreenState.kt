package com.example.exchangerates.ui.home.favorites.state

import com.example.exchangerates.features.rates.api.model.RatesItem

sealed interface FavoritesScreenState {
    data object Loading : FavoritesScreenState
    data object Error : FavoritesScreenState
    data class Data(
        val favoriteRates: List<RatesItem>,
    ): FavoritesScreenState
}