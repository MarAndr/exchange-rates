package com.example.exchangerates.ui.home.rates.state

data class RatesUiModel(
    val base: String,
    val symbol: String,
    val rate: Double,
    val isFavorite: Boolean,
)
