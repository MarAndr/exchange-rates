package com.example.exchangerates.api

data class CurrenciesNameResponse(
    val success: Boolean? = null,
    val symbols: Map<String, String>? = null
)
