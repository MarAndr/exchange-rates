package com.example.exchangerates.features.rates.data.remote.model

internal data class CurrencyListDto(
    val success: Boolean,
    val symbols: Map<String, String>?
)