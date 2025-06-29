package com.example.exchangerates.features.rates.abstractions

import com.example.exchangerates.core.loading.LoadingState
import com.example.exchangerates.features.rates.entities.Currency
import com.example.exchangerates.features.rates.entities.RatesItem
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun getLatestRates(baseCurrency: String, targetCurrencies: List<String>): Flow<LoadingState<List<RatesItem>>>
    fun getCurrencyList(): Flow<LoadingState<List<Currency>>>
}
