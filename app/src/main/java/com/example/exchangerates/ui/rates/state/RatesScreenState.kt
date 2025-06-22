package com.example.exchangerates.ui.rates.state


sealed interface RatesScreenState {
    data class Loading(
        val baseCurrency: String
    ) : RatesScreenState
    
    data class Success(
        val baseCurrency: String,
        val currencies: List<CurrencyItem>
    ) : RatesScreenState
    
    data class Error(
        val baseCurrency: String,
        val message: String
    ) : RatesScreenState
}

data class CurrencyItem(
    val symbol: String,
    val rate: Double,
    val isFavorite: Boolean
)