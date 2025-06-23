package com.example.exchangerates.ui.rates.state


sealed interface RatesScreenState {
    data object Loading : RatesScreenState

    data class Success(
        val baseCurrency: String,
        val rates: List<RatesItem>
    ) : RatesScreenState

    data object Error : RatesScreenState
}

data class RatesItem(
    val symbol: String,
    val rate: Double,
    val isFavorite: Boolean
)