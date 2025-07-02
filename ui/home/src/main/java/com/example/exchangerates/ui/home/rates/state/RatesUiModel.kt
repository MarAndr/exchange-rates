package com.example.exchangerates.ui.home.rates.state

import com.example.exchangerates.features.rates.entities.CurrencySymbol

data class RatesUiModel(
    val base: CurrencySymbol,
    val symbol: CurrencySymbol,
    val rate: Double,
    val isFavorite: Boolean,
)
