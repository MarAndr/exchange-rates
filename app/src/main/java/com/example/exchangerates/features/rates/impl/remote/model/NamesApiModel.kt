package com.example.exchangerates.features.rates.impl.remote.model

data class NamesApiModel(
    val success: Boolean,
    val symbols: Map<String, String>?
)