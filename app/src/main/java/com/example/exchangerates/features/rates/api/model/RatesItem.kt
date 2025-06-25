package com.example.exchangerates.features.rates.api.model

data class RatesItem(
    val base: String,
    val symbol: String,
    val rate: Double,
)