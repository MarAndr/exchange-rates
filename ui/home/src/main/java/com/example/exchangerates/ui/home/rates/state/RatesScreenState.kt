package com.example.exchangerates.ui.home.rates.state

import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.CurrencySymbol


sealed interface RatesScreenState {
    data object Loading : RatesScreenState

    data class Data(
        val baseCurrency: CurrencySymbol,
        val rates: List<RatesUiModel>,
        val availableCurrencies: List<Currency>,
        val isRefreshing: Boolean = false,
        val isError: Boolean = false,
    ) : RatesScreenState

    data object Error : RatesScreenState
}
