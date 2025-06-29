package com.example.exchangerates.ui.home.favorites.state

import com.example.exchangerates.features.rates.entities.RatesItem

sealed interface FavoritesScreenEvent {
    data object OnRefresh : FavoritesScreenEvent
    data class RemoveFromFavorites(
        val ratesItem: RatesItem
    ) : FavoritesScreenEvent
}