package com.example.exchangerates.ui.rates.state

sealed interface RatesScreenEvent {
    data class OnFavoriteClick(val symbol: String) : RatesScreenEvent
    data object OnRefresh : RatesScreenEvent
    data class OnBaseCurrencyChanged(val newBaseCurrency: String) : RatesScreenEvent
}