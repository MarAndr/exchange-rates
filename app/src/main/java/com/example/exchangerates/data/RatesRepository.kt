package com.example.exchangerates.data

import com.example.exchangerates.ui.rates.state.CurrencyItem

interface RatesRepository {
    suspend fun getLatestCurrencies(baseCurrency: String): List<CurrencyItem>
}
