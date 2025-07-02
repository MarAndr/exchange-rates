package com.example.exchangerates.ui.home.favorites.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.exchangerates.features.rates.entities.CurrencySymbol
import com.example.exchangerates.ui.home.favorites.state.FavoritesScreenState
import com.example.exchangerates.ui.home.favorites.state.FavoriteRateUiModel

internal class FavoritesScreenPreviewParamsProvider : PreviewParameterProvider<FavoritesScreenState> {
    private val mockFavoriteRates = listOf(
        FavoriteRateUiModel(
            base = CurrencySymbol("USD"),
            symbol = CurrencySymbol("EUR"),
            rate = 0.85,
        ),
        FavoriteRateUiModel(
            base = CurrencySymbol("USD"),
            symbol = CurrencySymbol("GBP"),
            rate = 0.73,
        ),
        FavoriteRateUiModel(
            base = CurrencySymbol("USD"),
            symbol = CurrencySymbol("JPY"),
            rate = 110.5,
        )
    )

    override val values = sequenceOf(
        FavoritesScreenState(
            favoriteRates = mockFavoriteRates,
            isLoading = false,
        ),
        FavoritesScreenState(
            favoriteRates = mockFavoriteRates.take(1),
            isLoading = false,
        ),
        FavoritesScreenState(
            favoriteRates = emptyList(),
            isLoading = false,
        ),
        FavoritesScreenState(
            favoriteRates = mockFavoriteRates,
            isLoading = true,
        ),
    )
} 