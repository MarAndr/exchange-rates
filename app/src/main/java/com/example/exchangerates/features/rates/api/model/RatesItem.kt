package com.example.exchangerates.features.rates.api.model

data class RatesItem(
    val symbol: String,
    val rate: Double,
    val isFavorite: Boolean
)