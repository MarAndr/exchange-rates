package com.example.exchangerates.ui.home.rates.state

import com.example.exchangerates.features.filters.entities.SortOption
import com.example.exchangerates.features.rates.entities.CurrencySymbol

sealed interface RatesScreenEvent {
    data class OnFavoriteClick(
        val rate: RatesUiModel,
        val wasFavorite: Boolean,
    ) : RatesScreenEvent

    data object OnRefresh : RatesScreenEvent

    data class OnBaseCurrencyChanged(val newBaseCurrency: CurrencySymbol) : RatesScreenEvent

    data object OpenFilters : RatesScreenEvent

    data class OnSortOptionChanged(val sortOption: SortOption) : RatesScreenEvent
}