package com.example.exchangerates.features.rates.entities

@JvmInline
value class CurrencySymbol(val value: String) {
    override fun toString(): String = value
} 