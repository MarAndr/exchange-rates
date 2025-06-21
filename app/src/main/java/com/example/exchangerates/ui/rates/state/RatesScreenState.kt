package com.example.exchangerates.ui.rates.state

import com.example.exchangerates.domain.model.Symbol

sealed interface RatesScreenState {
    data object Loading : RatesScreenState
    
    data class Success(
        val currencies: List<CurrencyItem>
    ) : RatesScreenState
    
    data class Error(
        val message: String
    ) : RatesScreenState
}

data class CurrencyItem(
    val symbol: String,
    val rate: Double,
    val isFavorite: Boolean
)