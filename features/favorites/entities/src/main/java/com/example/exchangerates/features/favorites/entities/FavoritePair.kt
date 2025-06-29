package com.example.exchangerates.features.favorites.entities

data class FavoritePair(
    val id: Int = 0,
    val baseCurrency: String,
    val targetCurrency: String,
)
