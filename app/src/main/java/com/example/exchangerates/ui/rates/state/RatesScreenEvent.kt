package com.example.exchangerates.ui.rates.state

sealed interface RatesScreenEvent {
    data class OnFavoriteClick(
        val baseCurrency: String,
        val rate: RatesUiModel,
        val wasFavorite: Boolean,
    ) : RatesScreenEvent

    data object OnRefresh : RatesScreenEvent
    data class OnBaseCurrencyChanged(val newBaseCurrency: String) : RatesScreenEvent
}