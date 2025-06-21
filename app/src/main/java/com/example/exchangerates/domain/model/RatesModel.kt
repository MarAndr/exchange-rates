package com.example.exchangerates.domain.model

data class RatesModel(
    val base: Symbol,
    val quote: Symbol,
    val rate: Double
)
