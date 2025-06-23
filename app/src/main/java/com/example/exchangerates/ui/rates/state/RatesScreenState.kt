package com.example.exchangerates.ui.rates.state

import com.example.exchangerates.features.rates.api.model.RatesItem


sealed interface RatesScreenState {
    data object Loading : RatesScreenState

    data class Success(
        val baseCurrency: String,
        val rates: List<RatesItem>
    ) : RatesScreenState

    data object Error : RatesScreenState
}
