package com.example.exchangerates.core.internal

@JvmInline
value class CurrencySymbol(val value: String) {
    override fun toString(): String = value
} 