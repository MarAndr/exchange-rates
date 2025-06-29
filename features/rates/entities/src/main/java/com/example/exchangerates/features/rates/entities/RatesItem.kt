package com.example.exchangerates.features.rates.entities

data class RatesItem(
    val base: String,
    val symbol: String,
    val rate: Double,
)