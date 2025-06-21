package com.example.exchangerates.api.model

import com.example.exchangeratestestapppublic.domain.model.Symbol

data class NamesApiModel(
    val success: Boolean,
    val symbols: Map<Symbol, String>?
)