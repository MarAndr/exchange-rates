package com.example.exchangerates.features.rates.entities

data class RatesItem(
    val base: CurrencySymbol,
    val symbol: CurrencySymbol,
    val rate: Double,
)