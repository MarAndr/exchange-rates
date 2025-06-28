package com.example.exchangerates.features.rates.impl.remote.model

internal data class CurrencyListDto(
    val success: Boolean,
    val symbols: Map<String, String>?
)