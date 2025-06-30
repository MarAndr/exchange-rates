package com.example.exchangerates.ui.home.favorites.state

internal sealed interface FavoritesScreenEvent {
    data object OnRefresh : FavoritesScreenEvent
    data class RemoveFromFavorites(
        val ratesItem: FavoriteRateUiModel,
    ) : FavoritesScreenEvent
}