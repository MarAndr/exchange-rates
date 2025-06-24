package com.example.exchangerates.ui.home.rates.state

data class RatesUiModel(
    val symbol: String,
    val rate: Double,
    val favoriteId: Int?, // todo can we store id differently?
) {
    val isFavorite: Boolean = favoriteId != null
}
