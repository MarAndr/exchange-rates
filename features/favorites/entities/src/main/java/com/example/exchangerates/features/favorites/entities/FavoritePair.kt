package com.example.exchangerates.features.favorites.entities

import com.example.exchangerates.features.rates.entities.CurrencySymbol

data class FavoritePair(
    val id: Int = 0,
    val baseCurrency: CurrencySymbol,
    val targetCurrency: CurrencySymbol,
)
