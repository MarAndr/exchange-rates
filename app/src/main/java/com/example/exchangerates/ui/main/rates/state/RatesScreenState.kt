package com.example.exchangerates.ui.main.rates.state

import com.example.exchangerates.features.rates.api.model.RatesItem
import com.example.exchangerates.features.rates.api.model.Currency


sealed interface RatesScreenState {
    data object Loading : RatesScreenState

    data class Success(
        val baseCurrency: String,
        val rates: List<RatesUiModel>,
        val availableCurrencies: List<Currency>
    ) : RatesScreenState

    data object Error : RatesScreenState
}
