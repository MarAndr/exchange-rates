package com.example.exchangerates.features.favorites.api.model

data class FavoritePair(
    val id: Int = 0,
    val baseCurrency: String,
    val targetCurrency: String,
)
