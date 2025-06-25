package com.example.exchangerates.ui.home.rates.state

import com.example.exchangerates.features.rates.api.model.Currency


sealed interface RatesScreenState {
    data object Loading : RatesScreenState

    data class Success(
        val baseCurrency: String,
        val rates: List<RatesUiModel>,
        val availableCurrencies: List<Currency>,
        val isRefreshing: Boolean = false,
    ) : RatesScreenState

    data object Error : RatesScreenState
}
