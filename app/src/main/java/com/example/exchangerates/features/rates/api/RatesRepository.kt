package com.example.exchangerates.features.rates.api

import com.example.exchangerates.features.common.loading.LoadingState
import com.example.exchangerates.features.rates.api.model.Currency
import com.example.exchangerates.features.rates.api.model.RatesItem
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    fun getLatestRates(baseCurrency: String): Flow<LoadingState<List<RatesItem>>>
    fun getCurrencyList(): Flow<LoadingState<List<Currency>>>
}
